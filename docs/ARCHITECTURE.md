# Velra — Architecture

## Overview

```
┌─────────────────────────────────────────────────────────┐
│  App / Telegram / Twitter                               │
│  ┌───────────────────────────────────────────────────┐  │
│  │  VelraGlassView                                    │  │
│  │    ├── configure() → SocDetector.resolve()         │  │
│  │    ├── setBarRect()                                │  │
│  │    ├── animateIntensityTo() → GlassMotion.spring   │  │
│  │    └── RenderEffect → AGSL shader                  │  │
│  └───────────────────────────────────────────────────┘  │
│       │                                                  │
│       ├── SocDetector → profileFor(ro.hardware)          │
│       │     ├── SHADER (8 Elite/Tensor)                  │
│       │     ├── LITE (SD 6/7, Dimensity 8k/9k)           │
│       │     └── SCRIM (700, low-RAM, thermal)            │
│       │                                                  │
│       ├── VelraBridge → C++ libvelra.so                  │
│       │     ├── __system_property_get (ro.hardware)      │
│       │     └── sysconf (_SC_PHYS_PAGES)                │
│       │                                                  │
│       └── VelraGlassShader → VELRA_GLASS_SHADER          │
│             ├── SDF-gradient normal refraction           │
│             ├── 3-tap chromatic aberration               │
│             ├── inner stroke hairline                    │
│             └── bevel per-tier                           │
│                                                      │
│  No bitmap capture. No GPU texture upload.              │
└─────────────────────────────────────────────────────────┘
```

## Data Flow

1. `VelraGlassView.configure(profile, intensity, blurEnabled)` called
2. `SocDetector.resolve()` → `Profile.SHADER/LITE/SCRIM`
3. `selectGlassTier(frosted, lowRam, thermal, highQuality)` → `KlyntTier`
4. `GlassParams` assembled with `blur 18/12/8`, `dispersion`, `bevel`
5. `RenderEffect` chain: `blur(RenderEffect.blur())` → `AGSL` via `RuntimeShader`
6. `animateIntensityTo()` → `GlassMotion.springStep` at 120Hz
7. Thermal downgrade: `MODERATE+ → SCRIM` (live, restores on cool)

## Key Decisions

- **AGSL over Kotlin Canvas** — `RuntimeShader` compiles to GPU, no `Canvas.draw` overhead
- **C++ NDK over JNI Kotlin** — `__system_property_get` is `const char*` C API, Kotlin `System.getProperty` doesn't expose it
- **SDF over bitmap** — `sdRoundBox` gives exact pill geometry without texture sampling
- **3-tap CA over 5-tap** — 3-tap is visually identical, half cost
- **No loops in shader** — Mali strict AGSL compiler rejects `for`/`while`

## File Map

| File | Purpose |
|------|---------|
| `VelraGlassView.kt` | Main view, `configure`/`setBarRect`/`animateIntensityTo` |
| `SocDetector.kt` | SOC profile detection + C++ bridge |
| `VelraGlassShader.kt` | AGSL source string |
| `GlassMotion.kt` | Spring physics |
| `VelraBridge.kt` | JNI declarations |
| `velra.cpp` | C++ `__system_property_get` + `sysconf` |
| `CMakeLists.txt` | NDK build config |
