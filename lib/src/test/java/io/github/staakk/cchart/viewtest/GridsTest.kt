package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.features
import io.github.staakk.cchart.grid.Grid
import io.github.staakk.cchart.style.lineStyle
import io.github.staakk.cchart.grid.GridLinesProviders
import io.github.staakk.cchart.grid.GridOrientation
import org.junit.Rule
import org.junit.Test

class GridsTest {
    @get:Rule
    val paparazzi = createPaparazziRule()

    @Test
    fun verticalGrid() = runTestFor(Grid(orientation = GridOrientation.Vertical))

    @Test
    fun horizontalGrid() = runTestFor(Grid(orientation = GridOrientation.Horizontal))

    @Test
    fun verticalAndHorizontal() = runTestFor(
        Grid(orientation = GridOrientation.Vertical),
        Grid(orientation = GridOrientation.Horizontal)
    )

    @Test
    fun multipleVertical() = runTestFor(
        Grid(
            orientation = GridOrientation.Vertical,
            gridLinesProvider = GridLinesProviders.multiple(0.5f)
        ),
        Grid(
            orientation = GridOrientation.Vertical,
            gridLinesProvider = GridLinesProviders.multiple(1f / 3f),
            lineStyle { brush = SolidColor(Color.Red) }
        ),
    )

    @Test
    fun multipleHorizontal() = runTestFor(
        Grid(
            orientation = GridOrientation.Horizontal,
            gridLinesProvider = GridLinesProviders.multiple(0.5f)
        ),
        Grid(
            orientation = GridOrientation.Horizontal,
            gridLinesProvider = GridLinesProviders.multiple(1f / 3f),
            lineStyle { brush = SolidColor(Color.Red) }
        ),
    )

    private fun runTestFor(vararg grids: Grid) {
        paparazzi.snapshot {
            Chart(
                modifier = Modifier
                    .aspectRatio(1f, false),
                viewport = Viewport(0f, 10f, 0f, 5f)
            ) {
                features(*grids)
            }
        }
    }
}