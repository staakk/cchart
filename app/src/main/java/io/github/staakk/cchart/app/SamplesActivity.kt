package io.github.staakk.cchart.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.DataBounds
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.renderer.BarRenderer
import io.github.staakk.cchart.renderer.LineRenderer
import io.github.staakk.cchart.renderer.PointRenderer

class SamplesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Chart(
                        modifier = Modifier.weight(1f),
                        bounds = DataBounds(-1f, 5f, 0f, 9f)
                    ) {
                        data(
                            seriesOf(
                                "First",
                                pointOf(0f, 1f),
                                pointOf(2f, 8f),
                                pointOf(3f, 3f),
                                pointOf(4f, 4f),
                            ),
                            seriesOf(
                                "Second",
                                pointOf(0f, 0f),
                                pointOf(2f, 8f),
                                pointOf(3f, 3f),
                                pointOf(4f, 4f),
                            ),
                            seriesOf(
                                "Third",
                                pointOf(0f, 1f),
                                pointOf(2f, 8f),
                                pointOf(3f, 3f),
                                pointOf(4f, 4f),
                            )
                        )

                        seriesRendererFor(
                            "First",
                            renderer = PointRenderer(SolidColor(Color.Blue), 10f)
                        )
                        seriesRendererFor(
                            "Second", renderer = LineRenderer(
                                SolidColor(Color.Green),
                                strokeWidth = 5f,
                                pathEffect = PathEffect.dashPathEffect(
                                    FloatArray(2) { 20f })
                            )
                        )
                        seriesRendererFor(
                            "Third",
                            renderer = BarRenderer(SolidColor(Color.Red), 15f)
                        )
                    }

                    Text(modifier = Modifier.weight(1f), text = "Another fine chart")
                }
            }
        }
    }
}