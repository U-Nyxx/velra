# Velra

Glass engine for Android. `AGSL + RenderEffect + C++`. Extracted from [Klynt](https://github.com/U-Nyxx/Klynt).

<p>
  <a href="https://github.com/U-Nyxx/velra/releases"><img src="https://img.shields.io/github/v/release/U-Nyxx/velra?style=flat&label=version&color=111111" alt="version"></a>
  <a href="https://jitpack.io/#U-Nyxx/velra"><img src="https://jitpack.io/v/U-Nyxx/velra.svg" alt="jitpack"></a>
  <a href="LICENSE"><img src="https://img.shields.io/github/license/U-Nyxx/velra?style=flat&label=license&color=111111" alt="license"></a>
</p>

<img src="hero.png" alt="velra hero" width="100%">

No bitmap capture. `libvelra.so` via NDK. Loop-free AGSL for Mali.

## Install

Maven Central:

```kotlin
implementation("io.github.u-nyxx:velra:0.1.1")
```

JitPack:

```kotlin
maven("https://jitpack.io")
implementation("com.github.U-Nyxx:velra:0.1.1")
```

## Use

```kotlin
val view = VelraGlassView(context).apply {
    configure(SocDetector.resolve(context), intensity = 1f, blurEnabled = true)
    setBarRect(0, 0, width, height)
    post { animateIntensityTo(1f) }
}
```

`configure` picks `blur 18/12/8` + `dispersion` + `bevel` per SOC. `animateIntensityTo` is spring `170/0.72`, not alpha.

C++ bridge: `SocDetector.nativeHardware()` via `__system_property_get`, `VelraGlassView.nativeIsLowRam()` via `sysconf`.

## Tiers

- `SHADER` — 4 taps (3 CA + center), blur 18 — 8 Elite / Tensor
- `LITE` — 2 taps, blur 12 — SD 6/7, Dimensity 8k/9k, Exynos
- `SCRIM` — Canvas `0xD61E2A3A`, blur 8 — 700 / low-RAM / thermal

`selectGlassTier(frosted, lowRam, thermal, highQuality)` is pure.

## Build

`minSdk 33` (RuntimeShader), `compileSdk 34`, `Kotlin 2.0.21`, `NDK 27`, `CMake 3.22`

```bash
./gradlew :velra:assembleRelease
./gradlew :velra:publishToMavenLocal
```

## License

MIT — U-Nyxx
