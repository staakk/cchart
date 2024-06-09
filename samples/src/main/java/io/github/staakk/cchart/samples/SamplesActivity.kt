package io.github.staakk.cchart.samples

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val screens: List<Pair<@Composable () -> Unit, String>> = listOf(
    @Composable { AnimatedBarColorChartScreen() } to "Animated bar color",
    @Composable { AnimatedBarSizeChartScreen() } to "Animated bar size",
    @Composable { BarChartScreen() } to "Bar chart",
    @Composable { DateLabelsChartScreen() } to "Date labels",
    @Composable { PopupChartScreen() } to "Displaying popup",
    @Composable { GridChartScreen() } to "Grid",
    @Composable { LabeledPointsScreen() } to "Labeled point chart",
    @Composable { LineChartScreen() } to "Line chart",
    @Composable { ViewportUpdatesScreen() } to "Observing viewport",
    @Composable { PanAndZoomScreen() } to "Panning and zooming",
    @Composable { CombinedChartScreen() } to "Point & line chart",
    @Composable { PointChartScreen() } to "Point chart"
)

class SamplesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    var selected by remember { mutableIntStateOf(0) }
    screens[selected].first()

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = "Select one of the examples below",
        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
    )
    LazyColumn {
        screens.forEachIndexed { index, (_, name) ->
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selected = index }
                        .padding(16.dp),
                    text = name,
                    style = TextStyle(fontSize = 14.sp)
                )
            }
        }
    }
}