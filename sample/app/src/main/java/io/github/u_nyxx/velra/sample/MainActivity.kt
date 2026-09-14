import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.u_nyxx.velra.VelraGlassView
import io.github.u_nyxx.velra.SocDetector

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val profile = SocDetector.resolve(this)
        val view = VelraGlassView(this).apply {
            configure(profile, intensity = 1f, blurEnabled = true)
            setBarRect(0, 0, 1080, 96)
        }
        setContentView(view)
    }
}