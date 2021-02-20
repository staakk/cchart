package io.github.staakk.cchart.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
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
import io.github.staakk.cchart.data.ChartData
import io.github.staakk.cchart.data.DataPoint
import io.github.staakk.cchart.data.Series
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
                        modifier = Modifier.weight(1f)
                    ) {
                        items(
                            ChartData(
                                series = listOf(
                                    Series(
                                        name = "First",
                                        points = listOf(
                                            DataPoint(0, 1),
                                            DataPoint(2, 8),
                                            DataPoint(3, 3),
                                            DataPoint(4, 4),
                                        ),
                                    ),
                                    Series(
                                        name = "Second",
                                        points = listOf(
                                            DataPoint(0, 0),
                                            DataPoint(2, 8),
                                            DataPoint(3, 3),
                                            DataPoint(4, 4),
                                        ),
                                    ),
                                    Series(
                                        name = "Third",
                                        points = listOf(
                                            DataPoint(0, 1),
                                            DataPoint(2, 8),
                                            DataPoint(3, 3),
                                            DataPoint(4, 4),
                                        ),
                                    )
                                )
                            )
                        )

                        seriesRendererFor("First", PointRenderer(SolidColor(Color.Blue), 10f))
                        seriesRendererFor(
                            "Second", LineRenderer(
                                SolidColor(Color.Green),
                                strokeWidth = 5f,
                                pathEffect = PathEffect.dashPathEffect(
                                    FloatArray(2) { 20f })
                            )
                        )
                        seriesRendererFor("Third", BarRenderer(SolidColor(Color.Red), 15f))
                    }

                    Text(modifier = Modifier.weight(1f), text = "Another fine chart")
                }
            }
        }
    }
}