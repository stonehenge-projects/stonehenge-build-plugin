#!/usr/bin/env python3
"""
Javadoc English Preprocessor

Transforms Java source files with bilingual Javadoc (Chinese primary, English
via custom tags) into English-only Javadoc output.

Custom tags:
  @en <desc>              English main description (replaces Chinese)
  @enparam <name> <desc>  English @param description
  @enreturn <desc>        English @return description

Single-line shorthand:
  /** 中文说明。@en English description. */  ->  /** English description. */

Usage:
  python3 javadoc-en-preprocessor.py <output-dir>
  (reads source file paths from stdin, one per line)
"""

import re
import os
import sys

JAVADOC_RE = re.compile(r'/\*\*.*?\*/', re.DOTALL)


def _content(line: str) -> str:
    """Strip the leading ' * ' / ' *' prefix from a Javadoc line."""
    return re.sub(r'^\s*\*\s?', '', line).rstrip()


def translate_block(block: str) -> str:
    """Translate a multi-line Javadoc block to English using @en tags."""
    if '@en' not in block:
        return block

    lines = block.split('\n')
    indent = re.match(r'^(\s*)', lines[0]).group(1)

    en_main   = []      # English main description lines
    en_params = {}      # {param_name: english_desc}
    en_return = None    # English @return

    std_params = {}     # {param_name: original_desc}  (Chinese, may be replaced)
    std_return = None   # Original @return desc
    other_tags = []     # [(tag_name, content)] for @throws/@since etc.

    state     = 'main'
    cur_param = None

    for line in lines:
        c = _content(line)

        # Skip block delimiters: opening /** and closing */ (stripped to "/" by _content)
        if c == '/**' or c == '/' or c.startswith('/**'):
            continue

        # ── @en ────────────────────────────────────────────────────────────────
        m = re.match(r'^@en\s+(.*)', c)
        if m:
            state = 'en_main'
            en_main = [m.group(1)] if m.group(1) else []
            continue

        # ── @enparam name desc ─────────────────────────────────────────────────
        m = re.match(r'^@enparam\s+(\S+)\s*(.*)', c)
        if m:
            state, cur_param = 'en_param', m.group(1)
            en_params[cur_param] = m.group(2)
            continue

        # ── @enreturn desc ─────────────────────────────────────────────────────
        m = re.match(r'^@enreturn\s*(.*)', c)
        if m:
            state    = 'en_return'
            en_return = m.group(1)
            continue

        # ── @param name desc ───────────────────────────────────────────────────
        m = re.match(r'^@param\s+(\S+)\s*(.*)', c)
        if m:
            state, cur_param = 'std_param', m.group(1)
            std_params[cur_param] = m.group(2)
            continue

        # ── @return desc ───────────────────────────────────────────────────────
        m = re.match(r'^@return\s*(.*)', c)
        if m:
            state      = 'std_return'
            std_return = m.group(1)
            continue

        # ── other known block tags ─────────────────────────────────────────────
        m = re.match(r'^@(throws?|see|since|author|version|deprecated'
                     r'|apiNote|implNote|implSpec)\s*(.*)', c)
        if m:
            state = 'other_tag'
            other_tags.append((m.group(1), m.group(2)))
            continue

        # ── continuation lines ─────────────────────────────────────────────────
        if state == 'en_main':
            en_main.append(c)           # may be '' (blank separator line)
        elif state == 'en_param' and cur_param and c:
            en_params[cur_param] = (en_params.get(cur_param, '') + ' ' + c).strip()
        elif state == 'en_return' and en_return is not None and c:
            en_return = (en_return + ' ' + c).strip()

    # ── Assemble English block ─────────────────────────────────────────────────
    result = [f'{indent}/**']

    for line in en_main:
        result.append(f'{indent} *' if line == '' else f'{indent} * {line}')

    has_tags = bool(std_params or std_return is not None or other_tags)
    if has_tags and en_main:
        result.append(f'{indent} *')

    for pname, pdesc in std_params.items():
        result.append(f'{indent} * @param {pname} {en_params.get(pname, pdesc)}')

    if std_return is not None:
        result.append(f'{indent} * @return {en_return if en_return is not None else std_return}')

    for tag, content in other_tags:
        result.append(f'{indent} * @{tag} {content}' if content else f'{indent} * @{tag}')

    result.append(f'{indent} */')
    return '\n'.join(result)


def translate_single(block: str) -> str:
    """Translate /** 中文 @en English */ single-line block."""
    m = re.search(r'@en\s+(.+?)(?=\s*\*/)', block)
    if m:
        indent = re.match(r'^(\s*)', block).group(1)
        return f'{indent}/** {m.group(1).strip()} */'
    return block


def translate_javadoc(block: str) -> str:
    if '@en' not in block:
        return block
    if '\n' not in block.strip():
        return translate_single(block)
    return translate_block(block)


def process_content(content: str) -> str:
    return JAVADOC_RE.sub(lambda m: translate_javadoc(m.group(0)), content)


def dest_path(output_dir: str, src_file: str, content: str) -> str:
    """Derive output path from the file's package declaration."""
    m = re.search(r'^package\s+([\w.]+);', content, re.MULTILINE)
    pkg = m.group(1).replace('.', os.sep) if m else ''
    return os.path.join(output_dir, pkg, os.path.basename(src_file))


def main():
    if len(sys.argv) != 2:
        print(f'Usage: echo "<file1>\\n<file2>\\n..." | {sys.argv[0]} <output-dir>')
        sys.exit(1)

    output_dir = sys.argv[1]
    files = [line.strip() for line in sys.stdin if line.strip()]

    ok, fail = 0, 0
    for java_file in files:
        try:
            with open(java_file, encoding='utf-8') as f:
                content = f.read()
            processed = process_content(content)
            dst = dest_path(output_dir, java_file, content)
            os.makedirs(os.path.dirname(dst), exist_ok=True)
            with open(dst, 'w', encoding='utf-8') as f:
                f.write(processed)
            ok += 1
        except Exception as e:
            print(f'  ERROR {java_file}: {e}', file=sys.stderr)
            fail += 1

    print(f'Preprocessed {ok} files ({fail} errors).')
    if fail:
        sys.exit(1)


if __name__ == '__main__':
    main()
