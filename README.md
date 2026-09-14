# Velra — Liquid Glass Engine

Pure Android glass rendering engine. `AGSL + C++ NDK`. Powers [KLYNT](https://github.com/U-Nyxx/Klynt).

| | |
|---|---|
| <img src="hero.png" width="100%" alt="Velra hero"> | **AGSL v2** — SDF-gradient normal refraction, 3-tap chromatic aberration, inner stroke, bevel per-SOC |
| `SHADER` | 4 taps, blur 18 — Snapdragon 8 Elite, Tensor G4 |
| `LITE` | 2 taps, blur 12 — SD 6/7, Dimensity 8k/9k, Exynos |
| `SCRIM` | Canvas `0xD61E2A3C`, blur 8 — SD 700, low-RAM, thermal |
| **minSdk 33** | `RuntimeShader` needs Tiramisu+ |
| **arm64 only** | `libvelra.so` via CMake + NDK 27 |
| **No device needed** | Tests run on JVM |

## Install

```kotlin
implementation("io.github.u-nyxx:velra:0.1.2")
```

Maven Central · JitPack · `io.github.u-nyxx:velra`

## Use

```kotlin
val view = VelraGlassView(context).apply {
    configure(SocDetector.resolve(context), intensity = 1f, blurEnabled = true)
    setBarRect(0, 0, width, height)
    post { animateIntensityTo(1f) }
}
```

`configure` picks `blur 18/12/8` + `dispersion` + `bevel` per SOC. `animateIntensityTo` springs `0→1` with `Choreographer` at 170/0.72.

## Configure · Language

| Layer | Language | File |
|-------|----------|------|
| Orchestration | Kotlin 2.0.21 | `VelraGlassView.kt`, `SocDetector.kt` |
| GPU | AGSL | `VelraGlassShader.kt` `VELRA_GLASS_SHADER` |
| Hardware | C++ 17 | `velra.cpp` `__system_property_get` + `sysconf` |

JNI: `VelraBridge.externalFun stringFromJNI()`, `SocDetector.nativeHardware()`, `VelraGlassView.nativeIsLowRam()`.

## Tiers

- `SHADER` 4 taps (3 CA + center) blur 18 — 8 Elite / Tensor
- `LITE` 2 taps blur 12 — SD 6/7 Dimensity 8k/9k Exynos
- `SCRIM` Canvas `0xD61E2A3C` blur 8 — 700 / low-RAM / thermal

`selectGlassTier(frosted, lowRam, thermal, highQuality)` is pure JVM.

## Build

```bash
./gradlew :velra:assembleRelease   # 22K aar
./gradlew :velra:testDebugUnitTest  # JVM tests
./gradlew :velra:publishToMavenLocal
```

## Screenshots

| | |
|---|---|
| `screenshots/velra-hero.png` | Hero banner 1280×640 |
| `screenshots/velra-usage.png` | Usage preview 720×1280 |

## License

MIT — U-Nyxx
