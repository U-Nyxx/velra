<p align="center">
  <img src="https://img.shields.io/github/v/release/U-Nyxx/velra?style=flat&color=0A0A0A" alt="Release">
  <img src="https://img.shields.io/maven-central/v/io.github.u-nyxx/velra?color=0A0A0A&label=Maven" alt="Maven">
  <img src="https://jitpack.io/v/U-Nyxx/velra.svg" alt="JitPack">
  <img src="https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?style=flat&logo=kotlin" alt="Kotlin">
  <img src="https://img.shields.io/badge/C++-NDK%2027-00599C?style=flat&logo=cplusplus" alt="C++">
  <img src="https://img.shields.io/badge/AGSL-RuntimeShader-111111?style=flat" alt="AGSL">
  <img src="https://img.shields.io/badge/API-33%2B-111111?style=flat" alt="API">
</p>

<h1 align="center">Velra</h1>

<p align="center">
  <b>Liquid Glass Engine for Android</b><br>
  <sub>AGSL RuntimeShader + RenderEffect + C++ NDK • SDF lens • 3-tap CA • Spring physics • SOC-adaptive</sub>
</p>

---

> Extracted from [Klynt](https://github.com/U-Nyxx/Klynt) — the LSPosed Liquid Glass pill. Velra is the pure engine: zero third-party, `arm64` + `Mali` safe, `ThermalListener` aware. No bitmap capture, `libvelra.so` via NDK (`velra.cpp` hardware↔software bridge), no HWUI kill-switch. Not full Kotlin: Kotlin + AGSL + C++.

## Install

**Maven Central (recommended, `io` — no `com` domain needed):**

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories { google(); mavenCentral() }
}
// app/build.gradle.kts
dependencies {
    implementation("io.github.u-nyxx:velra:0.1.0")
}
```

**JitPack (instant, no Sonatype):**

```kotlin
// settings.gradle.kts
maven("https://jitpack.io")
// app/build.gradle.kts
implementation("com.github.U-Nyxx:velra:0.1.0")
```

## Usage

```kotlin
import io.github.u_nyxx.velra.VelraGlassView
import io.github.u_nyxx.velra.SocDetector
import io.github.u_nyxx.velra.configure

val profile = SocDetector.resolve(context)
val view = VelraGlassView(context).apply {
    configure(profile, intensity = 1f, blurEnabled = true)
    setBarRect(0, 0, width, height) // pill bounds
    clearMode = false
    post { animateIntensityTo(1f) } // spring 170/0.72, not alpha fade
}
// Thermal: auto downgrade MODERATE+ → SCRIM, restore on cool (window-bound)
// Press: setPress(x, y, 1f) / clearPress() — exponential  exp(-r²/16200)*10
```

## Tiers

| Tier | Blur | Taps | When |
|------|------|------|------|
| `SHADER` | 18 | 4 (3 CA + center) | Snapdragon 8 / Tensor |
| `LITE` | 12 | 2 (achromatic) | SD 6/7, Dimensity 8k/9k, Exynos |
| `SCRIM` | 8* | 0 (Canvas) | Dimensity 700, low RAM, thermal |

*`*` blur 8 only for cool-down restore — SCRIM paints `0xD61E2A3A`.

## Cost

Loop-free AGSL (`for/while` banned — Mali strict). Blur from `RenderEffect` chain, not shader. `selectGlassTier(frosted, lowRam, thermal, highQuality)` pure.

## Low-Level Bridge (hardware↔software)

Velra is not full Kotlin — research: reference platform glass is `Render Server (C++14)` + `Metal Shading Language`; we map to `AGSL (GPU) + RenderEffect + C++ NDK (CPU)`. Native `velra.cpp` reads `ro.hardware` via `__system_property_get` (bypasses Java cache) and `sysconf` for low-RAM, exposing `nativeHardware()` / `nativeIsLowRam()` via JNI. Build: `CMake 3.22.1` + `NDK 27.0.12077973` → `libvelra.so` (`arm64-v8a` only, diet).

## Requirements

- `minSdk 33` (`RuntimeShader` Tiramisu+), `compileSdk 34`, `Kotlin 2.0.21`, `JDK 17`, `NDK 27` + `CMake 3.22.1`
- `android.graphics.RuntimeShader` + `RenderEffect` (`API 33/31`) + `libvelra.so`

## License

MIT — `U-Nyxx` — see [LICENSE](LICENSE)

<p align="center">
  <sub>Crafted for SOC reality — Adreno, Mali, Xclipse, TPU.</sub>
</p>
