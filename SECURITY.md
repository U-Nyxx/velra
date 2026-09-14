# Security Policy — Velra

## Reporting a Vulnerability

Email **u_nyxx@protonmail.com** or open a private draft on GitHub.

We take security seriously. Do not disclose via public issues until patched.

## Known Issues

- **AGSL shader compilation failure** on some Mali mid-range drivers — fallback to SCRIM
- **C++ bridge `__system_property_get`** may return empty string on Android 13 emulator — fallback to `Build.HARDWARE`
- **`RuntimeShader` crash** on API 33 with certain vendor overlays — graceful degradation to LITE

## Dependencies

Velra has **no third-party dependencies**. Zero attack surface from libraries.

## Signing

No signing required for library publication. `consumer-rules.pro` strips internal names.
