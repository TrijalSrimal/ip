# IP Work Context and History (March 6-7, 2026)

## Summary of Chat Context and Work Done

This document captures the entire context of the codebase enhancements, debugging, and file iterations executed during the chatbot session.

### Initial Review & Status
1. Evaluated `git status`, tags, branches, and files in the parent repo (`/Users/trijal/courses/CS2113/IP`). 
2. Realized the project was up to **Level 7** functionality (Basic commands, File I/O via strings, Array-based/Collection-based task list via Level 6). 
3. Identified some structural Code Quality shortfalls: `Encik.java` contained ~456 lines (a God class handling everything from storage to UI), fields lacked encapsulation, and the `docs` only had a rudimentary README.

### Implementing CS2113 Week 7 Project Instructions
- Consulted the `CS2113 - Week 7 - Project.pdf` guidelines and `.agent-context.md`.
- Excluded unneeded compilation and binary artifacts (`Encik.jar`, `.class`) and documented files (`CS2113 - Week 7 - Project.pdf`) via `.gitignore`.
- Decoupled `Encik.java` into multiple, single-responsibility OOP classes according to `A-MoreOOP`, drastically reducing file sizes and improving cohesion. 

### A-MoreOOP Classes Built:
1. **`Ui`**: Handles scanning user inputs and displaying text formatting (e.g. `showWelcome()`, `showTaskAdded()`).
2. **`Storage`**: Facilitates tasks loading and saving out of/into `data/encik.txt`. Skip mechanisms were upgraded during later parts of the session to elegantly bypass badly formatted/non-date compliant old data lines instead of crashing the app.
3. **`Parser`**: Isolates standard inputs from parameters. Dispatches commands to standard `handleX` static functions depending on user statements (`isExit()`, `handleDeadline()`).
4. **`TaskList`**: Encapsulates array operations providing straightforward `add()`, `remove()`, and `find()` behavior.

### Deliverables Completed
- **`Level 9 (Find)`**: Created functionality inside `TaskList.find()` enabling case-insensitive searches returned accurately to the CLI.
- **`A-JavaDoc`**: Audited files and verified that all public methods and Java files already possessed extensive JavaDocs format header comments (passed via 71 counts).
- **`A-UserGuide`**: Revamped `docs/README.md` to comprehensively document formatting tables and full usage examples for `todo`, `deadline`, `event`, and `find`.
- **`A-Release` and Deployments**: Triggered GitHub API (gh CLI) to activate GitHub Pages tracking `/docs` via `master`. Created JAR build tests, proved functionality in an isolated folder, and subsequently deployed `v0.2` payload and Release properties on the GitHub pipeline.
- **`Level-6 (Collections)` Configuration**: Verified tasks inherently utilize Java ArrayList collections.

### Late Change: Level 8 Date Support
- Migrated legacy `String` date storage schemas onto strict validations running via `java.time.LocalDate`.
- Updated `Deadline.java` (`byDate`) and `Event.java` (`fromDate`, `toDate`) to force constructor assignments mapping `yyyy-MM-dd` standards via `LocalDate.parse()`. Fallbacks emit `EncikException` indicating formatting requests. Formatting handles serialization across output `MMM d yyyy`.
- Patched older README snippets to strictly define expected dates `yyyy-MM-dd`. 

## Final Architecture Status 
The `Encik` bot effectively leverages fully independent architecture, properly tracks strict timestamps, accurately catches and reports inputs, generates binary distributions, and maintains its public User Guide on GitHub pages synced to its internal branches. All deliverables (up to Level 9 boundaries + Optional Level 8 integrations) are merged securely onto `master`.
