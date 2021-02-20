package io.github.staakk.cchart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.util.angleToVec
import kotlin.math.round

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    labelOffset: Float = 0.8f,
    content: PieChartScope.() -> Unit
) {
    val scope = PieChartScopeImpl()
    scope.apply(content)

    Box(modifier = modifier) {
        Box {
            Canvas(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
            ) {
                scope.data.forEach {
                    drawArc(
                        brush = it.item.brush,
                        startAngle = it.startAngle,
                        sweepAngle = it.sweepAngle,
                        useCenter = true,
                        size = Size(size.minDimension, size.minDimension),
                        topLeft = Offset(0f, 0f)
                    )
                }
            }
            scope.data.forEach {
                Box(
                    modifier = Modifier.align(
                        angleToVec(it.startAngle + it.normalisedValue * 360 / 2.0)
                            .times(labelOffset.toDouble())
                            .let { (x, y) -> BiasAbsoluteAlignment(x.toFloat(), y.toFloat()) }
                    )
                ) {
                    scope.label?.invoke(it.item)
                }
            }
        }
    }
}

@Preview(name = "Pie chart")
@Composable
private fun PreviewPieChart() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(modifier = Modifier.padding(8.dp), text = "Nice Pie Chart")
            PieChart(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
            ) {
                items(
                    listOf(
                        PieChartItem(1f, Color.Blue),
                        PieChartItem(2f, Color.Cyan),
                        PieChartItem(2.8f, Color.Green),
                        PieChartItem(3f, Color.Red),
                    )
                ) {
                    Text(
                        modifier = Modifier
                            .background(
                                SolidColor(Color.White),
                                RoundedCornerShape(4.dp),
                                alpha = 0.95f
                            )
                            .padding(4.dp),
                        text = "${round(it.value / 7.8f * 100)}%"
                    )
                }
            }
        }
    }
}

/**
 * Receiver scope which is used by the [PieChart].
 */
interface PieChartScope {

    /**
     * Provides items to be shown on a pie chart.
     *
     * @param items Items to be shown.
     * @param label Composable function for each item`s label.
     */
    fun items(items: List<PieChartItem>, label: @Composable ((PieChartItem) -> Unit)? = null)
}

private class PieChartScopeImpl : PieChartScope {

    var data = emptyList<ItemMeta>()

    var label: @Composable ((PieChartItem) -> Unit)? = null

    var chartMeta: ChartMeta? = null

    override fun items(items: List<PieChartItem>, label: @Composable ((PieChartItem) -> Unit)?) {
        val sum = items.asSequence()
            .map { it.value }
            .sum()

        data = items.fold(mutableListOf<ItemMeta>() to 0f) { (list, startAngle), item ->
            val itemMeta = ItemMeta(
                item = item,
                normalisedValue = item.value / sum,
                startAngle = startAngle,
                sweepAngle = item.value / sum * 360f
            )
            (list.also { it.add(itemMeta) }) to (itemMeta.sweepAngle + startAngle)
        }.first

        this.label = label
        chartMeta = ChartMeta(itemsSum = sum)
    }
}

/**
 * Data to display on the chart.
 */
data class PieChartItem
/**
 * @param value Value to represent.
 * @param brush [Brush] to paint the arc with.
 * @param tag Tag to be passed along with the item.
 */
constructor(
    val value: Float,
    val brush: Brush,
    val tag: Any? = null,
) {

    /**
     * @param value Value to represent.
     * @param color [Color] to paint the arc with.
     * @param tag Tag to be passed along with the item.
     */
    constructor(value: Float, color: Color, tag: Any? = null) : this(value, SolidColor(color), tag)
}

internal data class ItemMeta(
    val item: PieChartItem,
    val normalisedValue: Float,
    val startAngle: Float,
    val sweepAngle: Float,
)

internal data class ChartMeta(
    val itemsSum: Float
)
