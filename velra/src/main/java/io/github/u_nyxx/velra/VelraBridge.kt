package io.github.u_nyxx.velra

/**
 * C++ NDK bridge — hardware↔software direct page.
 * Proves Velra is not full Kotlin: AGSL (GPU) + C++ (CPU/thermal/SOC) + Kotlin (orchestration).
 * Research: reference platform uses Render Server + Metal (C++14) for glass;
 * we map to AGSL + RenderEffect + NDK.
 */
object VelraBridge {
    init {
        try { System.loadLibrary("velra") } catch (_: Throwable) {}
    }

    @JvmStatic external fun stringFromJNI(): String
}
