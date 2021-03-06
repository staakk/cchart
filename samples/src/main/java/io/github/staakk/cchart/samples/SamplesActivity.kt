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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val LINE_CHART_ID = 0
const val POINT_CHART_ID = 1
const val COMBINED_CHART_ID = 2
const val BAR_CHART_ID = 3
const val DATE_LABELS_CHART = 4
const val GRID_CHART = 5
const val PAN_AND_ZOOM_CHART = 6
const val LABELED_POINTS_CHART = 7

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
    val selected = remember { mutableStateOf(0) }
    when (selected.value) {
        LINE_CHART_ID -> LineChartScreen()
        POINT_CHART_ID -> PointChartScreen()
        COMBINED_CHART_ID -> CombinedChartScreen()
        BAR_CHART_ID -> BarChartScreen()
        DATE_LABELS_CHART -> DateLabelsChartScreen()
        GRID_CHART -> GridChartScreen()
        PAN_AND_ZOOM_CHART -> PanAndZoomScreen()
        LABELED_POINTS_CHART -> LabeledPointsScreen()
    }

    MenuHeader()
    LazyColumn {
        item { Item(LINE_CHART_ID, "Line chart") { selected.value = it } }
        item { Item(POINT_CHART_ID, "Point chart") { selected.value = it } }
        item { Item(LABELED_POINTS_CHART, "Labeled point chart") { selected.value = it } }
        item { Item(COMBINED_CHART_ID, "Point & line chart") { selected.value = it } }
        item { Item(BAR_CHART_ID, "Bar chart") { selected.value = it } }
        item { Item(DATE_LABELS_CHART, "Chart with date labels") { selected.value = it } }
        item { Item(GRID_CHART, "Chart with grid") { selected.value = it } }
        item { Item(PAN_AND_ZOOM_CHART, "Chart with panning and zooming") { selected.value = it } }
    }
}

@Composable
fun MenuHeader() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = "Select one of the examples below",
        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
    )
}

@Composable
fun Item(index: Int, text: String, onSelected: (Int) -> Unit) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelected(index) }
            .padding(16.dp),
        text = text,
        style = TextStyle(fontSize = 14.sp)
    )
}