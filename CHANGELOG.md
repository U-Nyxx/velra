# Changelog — Velra

## v0.1.2 (2026-09-14) — Polished repo + version catalog

- **Version catalog**: added `gradle/libs.versions.toml`, migrated `build.gradle.kts` + `settings.gradle.kts` to `libs.*` aliases
- **CI**: added `.github/workflows/build.yml` — unit tests + AAR build + `libvelra.so` verification + GitHub Release
- **Tests**: added `unitTest` (SocDetector, GlassMotion, VelraGlassShader) + `androidTest` (VelraGlassView lifecycle)
- **Docs**: added `docs/ARCHITECTURE.md`, `docs/API.md`
- **Screenshots**: added `velra-hero.png` + `velra-usage.png`
- **Project metadata**: `CHANGELOG.md`, `CONTRIBUTING.md`, `SECURITY.md`, `FUNDING.yml`, `CODEOWNERS`, `renovate.json`
- **Sample app**: `sample/` directory for integration demo
- **`gradle.properties`**: `kotlin.code.style=official`, caching, parallel

## v0.1.1 (2026-09-13) — C++ NDK bridge + no apple

- `velra.cpp`: `__system_property_get` + `sysconf` via JNI
- `VelraBridge`, `SocDetector.nativeHardware()`, `VelraGlassView.nativeIsLowRam()`
- `AGSL v2`: SDF-gradient normal refraction, 3-tap CA, inner stroke, bevel per-SOC
- `GlassMotion`: Choreographer spring `170/0.72`, `isSettled`
- "apple" trademark removed
- Branch protection + MIT license

## v0.1.0 — Initial

- Standalone extraction from Klynt
- `VelraGlassView`, `VelraGlassShader`, `GlassMotion`, `SocDetector`
- Maven Central `io.github.u-nyxx:velra`
