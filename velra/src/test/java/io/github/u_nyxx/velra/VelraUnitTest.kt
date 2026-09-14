package io.github.u_nyxx.velra

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class VelraUnitTest {

    @Test
    fun `shader declares every uniform`() {
        val required = listOf(
            "uniform shader backdrop",
            "uniform float2 resolution",
            "uniform float4 barRect",
            "uniform float cornerRadius",
            "uniform float intensity",
            "uniform float dark",
            "uniform float2 press",
            "uniform float pressAmount",
            "uniform float clearMode",
            "uniform float dispersion",
            "uniform float bevel"
        )
        required.forEach { assertTrue(VELRA_GLASS_SHADER.contains(it), it) }
    }

    @Test
    fun `shader has no loops`() {
        val code = VELRA_GLASS_SHADER.replace("uniform shader backdrop", "")
        assertTrue(!code.contains("for ("), "no loops in AGSL")
        assertTrue(!code.contains("while ("), "no while in AGSL")
        assertEquals(code.count { it == '{' }, code.count { it == '}' }, "balanced braces")
    }

    @Test
    fun `full strength when nothing weak`() {
        assertEquals(KlyntTier.SHADER, selectGlassTier(false, false, false))
    }

    @Test
    fun `any weakness degrades to scrim`() {
        assertEquals(KlyntTier.SCRIM, selectGlassTier(true, false, false))
        assertEquals(KlyntTier.SCRIM, selectGlassTier(false, true, false))
        assertEquals(KlyntTier.SCRIM, selectGlassTier(false, false, true))
    }

    @Test
    fun `spring converges`() {
        var pos = 0f
        var vel = 0f
        repeat(600) {
            val (p, v) = GlassMotion.springStep(pos, vel, 1f, 170f, 0.72f, 1f / 120f)
            pos = p
            vel = v
        }
        assertTrue(GlassMotion.isSettled(pos, vel, 1f))
        assertTrue(pos > 0.95f)
    }

    @Test
    fun `soc detector maps correctly`() {
        val elite = SocDetector.profileFor("sm8650", "xiaomi")
        assertTrue(elite.highQuality, "sm8650 should be high quality")
        val mid = SocDetector.profileFor("mt6789", "xiaomi")
        assertTrue(mid.frostedFallback, "mt6789 should be frosted fallback")
        val exynos = SocDetector.profileFor("s5e8950", "samsung")
        assertTrue(exynos.thermalListenerRequired, "Exynos needs thermal listener")
    }
}
