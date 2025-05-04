# 📘 Changelog

All notable changes to this project will be documented in this file.

The format follows [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

---

## [0.0.1] - 2025-05-03

### Added
- Initial release of **Simple KeyStore**.
- Local password storage using **AES-128 encryption**.
- Fields: Name, Username, URL, Password, and optional Note.
- UI for managing credentials and persisting them to encrypted files.
- **Google Password Manager CSV import**:
  - Automatically maps `name`, `url`, `username`, and `password`.
  - Encrypted immediately upon import.
  - Handles duplicate avoidance.

### Fixed
- Early fixes in file import handling and field mapping logic.

---

## [Upcoming]

### Planned Features
- 🔍 `LIKE`-style **search** by Name and URL.
- 📤 **Export to CSV** (Google Passwords compatible).
- 🌐 **Clickable URLs** to open websites in the default browser.
- 🐞 **Fix for `note` field** not being imported correctly from Google CSV.

---
