# Daniel Kwan — Project Portfolio Page

## Overview

ZettelCLI is a command-line Zettelkasten app for managing a personal knowledge base with speed and precision. It focuses on keyboard-first workflows such as creating, linking, tagging, filtering, and searching notes, with opinionated command formats and clear, consistent outputs.

My primary contributions centered on user-facing features for linking and tagging, robust parsing and confirmation flows, and documentation that clearly explains formats and expected outputs.

## Summary of Contributions

### Code Contributed
- Dashboard (authorship) link: https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=danielkwan2004&tabRepo=AY2526S1-CS2113-W13-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false&until=2025-10-29T23%3A59%3A59

### Enhancements Implemented
- List UX and filters
	- Added and documented list filters with flags: `-p` (pinned-only) and `-a` (archived-only), including the combined matrix and updated UI help output.
	- Implemented consistent list labels and empty-state messages (e.g., pinned archived vs. pinned unarchived).
- Tagging features
	- List tags on a single note: `list-tags <NOTE_ID>`.
	- List all tags globally: `list-tags-all`.
	- Add a tag to a note (and create globally if new): `add-tag` / `new-tag`.
	- Delete a tag from a note (with confirmation and `-f` to force): `delete-tag [-f] <NOTE_ID> <TAG>`.
	- Delete a tag globally (with confirmation and cascading removal): `delete-tag-globally [-f] <TAG>`.
	- Rename a tag globally and update all affected notes: `rename-tag <OLD> <NEW>`.
- Linking features
	- Link notes (uni-directional) and unlink notes: `link`, `unlink`.
	- Link in both directions and unlink both sides: `link-both`, `unlink-both`.
	- List incoming/outgoing links directly: `list-incoming-links <NOTE_ID>`, `list-outgoing-links <NOTE_ID>`.
- Other user commands
	- Search notes: `find <TEXT>`.
	- Pin a note: `pin <NOTE_ID>`.
	- Print a note’s body: `print-body <NOTE_ID>`.

### Testing
- Added comprehensive JUnit tests for parser and commands, including:
	- Parser: direct commands for `list-incoming-links` and `list-outgoing-links`, list flags, tag operations, and `print-body`.
	- Command tests: delete tag (single note and globally) with confirmation/force flows, rename tag globally, unlink/link-both variants, and print note body edge cases (empty notes, missing ID, empty body).

### Documentation
- User Guide (UG)
	- Authored end-user sections and examples for: tagging (`new-tag`, `add-tag`, `list-tags`, `list-tags-all`, `delete-tag`, `delete-tag-globally`, `rename-tag`), linking (`link`, `unlink`, `link-both`, `unlink-both`, `list-incoming-links`, `list-outgoing-links`), searching (`find`), and printing note bodies (`print-body`).
	- Wrote the `list` command filters section with the pinned/archived matrix and updated examples; ensured help text matches runtime output.
- Team-based contributions
	- Wrote Javadoc where necessary and refactored code to a consistent style (e.g., command message formats, parser validation messages, and UI output harmonization).

### Notable Design/Implementation Highlights
- Parser consistency: standardized flag handling (`-p`, `-a`, `-f`) and error messages; added specific format strings to improve user feedback and testability.
- Safe destructive actions: added confirmation prompts for delete operations with `-f` bypass to support both safety and speed.
- Clear UI outputs: ensured commands report precise, actionable messages, including empty states and success summaries, to reduce user confusion.



