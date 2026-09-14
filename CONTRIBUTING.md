# Contributing to Velra

## Setup

```bash
git clone https://github.com/U-Nyxx/velra.git
cd velra
./gradlew :velra:assembleDebug
./gradlew :velra:testDebugUnitTest
```

## PR Checklist

- [ ] `./gradlew :velra:testDebugUnitTest` passes
- [ ] `./gradlew :velra:assembleRelease` passes
- [ ] `libvelra.so` present in AAR
- [ ] No `apple` trademark references
- [ ] CHANGELOG.md updated
- [ ] No TODO/FIXME without explanation

## Style

- Kotlin 2.0.21, `kotlin.code.style=official`
- KDoc for all public API
- `AGSL` shader: no loops, balanced braces
- C++17, `__system_property_get` for hardware detection

## Branches

- `main` — stable
- PRs → `main` after approval
- `hotfix/*` for urgent fixes

## Issue Templates

- Bug report: `.github/ISSUE_TEMPLATE/bug_report.yml`
- Feature request: `.github/ISSUE_TEMPLATE/feature_request.yml`

## License

MIT — contributions accepted under same license.
