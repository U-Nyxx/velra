# Velra Engine API

## `VelraGlassView`

```kotlin
class VelraGlassView(context: Context) : View(context) {
    fun configure(profile: SocDetector.Profile, intensity: Float, blurEnabled: Boolean)
    fun setBarRect(left: Int, top: Int, right: Int, bottom: Int)
    fun animateIntensityTo(target: Float)
    fun getGlassParams(): GlassParams
}
```

`configure` selects `SHADER/LITE/SCRIM` tier based on `SocDetector.profileFor(ro.hardware)`. `setBarRect` defines the glass region in pixels. `animateIntensityTo` springs from current → target using `GlassMotion.springStep`.

## `SocDetector`

```kotlin
object SocDetector {
    enum class Profile { SHADER, LITE, SCRIM }
    fun resolve(context: Context): Profile
    fun profileFor(hardware: String, brand: String): Profile
    fun nativeHardware(): String  // C++ bridge
    fun nativeIsLowRam(): Boolean // C++ bridge
}
```

Mapping: `sm8/sun/gs` → `SHADER`, `mt8/mt6/s5e` → `LITE`, `sd6/sd7`/thermal → `SCRIM`.

## `VelraGlassShader`

```kotlin
object VelraGlassShader {
    const val VELRA_GLASS_SHADER: String = "...AGSL source..."
    fun validate(shader: String): Boolean
}
```

SDF-gradient normal refraction, 3-tap chromatic aberration, inner stroke hairline, bevel. No `for`/`while` loops (Mali-safe).

## `GlassMotion`

```kotlin
object GlassMotion {
    data class SpringConfig(val stiffness: Float, val damping: Float)
    fun springStep(pos: Float, vel: Float, target: Float, stiffness: Float, damping: Float, dt: Float): Pair<Float, Float>
    fun isSettled(pos: Float, vel: Float, target: Float): Boolean
    val StiffnessLow = SpringConfig(170f, 0.72f)
}
```

Choreographer-based, 120Hz half-steps, `isSettled` for frame budgeting.

## `GlassParams`

```kotlin
data class GlassParams(
    val blurRadius: Float,
    val dispersion: Float,
    val bevel: Float,
    val intensity: Float,
    val dark: Float,
    val barRect: FloatArray,
    val cornerRadius: Float,
    val clearMode: Float,
    val press: FloatArray,
    val pressAmount: Float
)
```

## `VelraBridge`

```kotlin
object VelraBridge {
    external fun stringFromJNI(): String
}
```

C++ `libvelra.so` entry point. `__system_property_get` + `sysconf` via JNI.

## `selectGlassTier`

```kotlin
fun selectGlassTier(frosted: Boolean, lowRam: Boolean, thermal: Boolean, highQuality: Boolean): KlyntTier
```

Pure JVM. Returns `SHADER`, `LITE`, or `SCRIM`.

## `KlyntTier`

```kotlin
enum class KlyntTier { SHADER, LITE, SCRIM }
```

## `VelraConfig`

```kotlin
data class VelraConfig(
    val intensity: Float = 1f,
    val blurEnabled: Boolean = true,
    val highQuality: Boolean = false
)
```

Builder pattern for `VelraGlassView.configure`.
