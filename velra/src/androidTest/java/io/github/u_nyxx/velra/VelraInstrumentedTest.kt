package io.github.u_nyxx.velra

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class VelraInstrumentedTest {

    @get:Rule
    val composeRule = AndroidComposeTestRule.create({
        com.unyxx.velra.sample.MainActivity()
    })

    @Test
    fun `velra glass view renders without crash`() {
        composeRule.onNodeWithContentDescription("velra_glass_view")
            .performClick()
    }

    @Test
    fun `soc detector returns profile`() {
        val profile = com.unyxx.velra.sample.SocDetector.resolve()
        assertTrue(profile != null, "SocDetector must return a profile")
    }
}
