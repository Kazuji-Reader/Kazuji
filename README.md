<div align="center">

# Kazuji

**Kazuji is a free and open-source manga reader for Android with built-in online content sources.**

![Android 6.0](https://img.shields.io/badge/android-6.0+-brightgreen)
[![License](https://img.shields.io/github/license/joaovpimenta/Kazuji)](https://github.com/joaovpimenta/Kazuji/blob/devel/LICENSE)
[![Sources](https://img.shields.io/badge/sources-Kotatsu--Redo-informational)](https://github.com/Kotatsu-Redo/kotatsu-parsers-redo)

</div>

## Features

- Online manga catalogues powered by the Kotatsu-Redo parser ecosystem
- Support for Tachiyomi/Keiyoushi extensions
- Search by title, genres, and filters
- Favorites with custom categories
- Reading history, bookmarks, and incognito mode
- Offline downloads and CBZ support
- Material You interface for phones, tablets, and larger screens
- Standard and Webtoon reader modes
- New-chapter notifications, recommendations, and tracking
- AniList, MyAnimeList, Kitsu, and Shikimori integrations
- Password/biometric protection
- Android 6.0+ support

## Development

### Requirements

- JDK 17
- Android SDK 36
- Android build tools 35.0.0
- Android Studio or Android SDK command-line tools

### Clone and build

```bash
git clone https://github.com/joaovpimenta/Kazuji.git
cd Kazuji
./gradlew assembleDebug
```

Debug APK:

```text
app/build/outputs/apk/debug/app-debug.apk
```

### Tests and checks

```bash
./gradlew test
./gradlew lint
./gradlew check
```

See [CONTRIBUTING.md](./CONTRIBUTING.md), [AGENTS.md](./AGENTS.md), and [DESIGN.md](./DESIGN.md) for project conventions.

## Screenshots

<div align="center">
    <img src="./metadata/en-US/images/phoneScreenshots/1.png" alt="Kazuji mobile view" width="250"/>
    <img src="./metadata/en-US/images/phoneScreenshots/2.png" alt="Kazuji mobile view" width="250"/>
    <img src="./metadata/en-US/images/phoneScreenshots/3.png" alt="Kazuji mobile view" width="250"/>
</div>

## License

Kazuji is distributed under the GNU General Public License v3.0. See [LICENSE](./LICENSE).

## Upstream attribution

Kazuji builds on the open-source work of the [Kotatsu](https://github.com/KotatsuApp/Kotatsu) project and uses the actively maintained [Kotatsu-Redo parsers](https://github.com/Kotatsu-Redo/kotatsu-parsers-redo).

The Kotatsu project is the original codebase in this lineage. References to Kotatsu that remain in dependency coordinates, parser packages, compatibility code, or attribution are intentional.
