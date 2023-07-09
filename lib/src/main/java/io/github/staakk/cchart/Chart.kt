package io.github.staakk.cchart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import io.github.staakk.cchart.data.*
import io.github.staakk.cchart.data.Viewport.Companion.getViewport
import io.github.staakk.cchart.renderer.*
import io.github.staakk.cchart.util.detectTransformGestures

/**
 * Creates chart for visualising data.
 *
 * @param modifier Modifier to apply to this layout node.
 * @param viewport The viewport for the chart. Should be expressed int the same space
 * as the data represented on the chart.
 * @param maxViewport The maximal viewport that can be displayed by this chart.
 * @param minViewportSize Minimal size of the viewport.
 * @param maxViewportSize Maximal size of the viewport.
 * @param content A block that describes the contents of the chart.
 */
@Composable
fun Chart(
    modifier: Modifier = Modifier,
    viewport: Viewport? = null,
    maxViewport: Viewport? = viewport,
    minViewportSize: Size? = viewport?.size,
    maxViewportSize: Size? = viewport?.size,
    enableZoom: Boolean = false,
    onClick: (Offset, Data<*>) -> Unit = { _, _ -> },
    content: ChartScope.() -> Unit
) {
    val scope = ChartScopeImpl()
    scope.content()

    val viewportState = remember {
        mutableStateOf(viewport ?: (scope.newSeries.keys.getViewport()))
    }
    Chart(
        modifier = modifier,
        viewport = viewportState,
        maxViewport = maxViewport ?: viewportState.value,
        minViewportSize = minViewportSize ?: viewportState.value.size,
        maxViewportSize = maxViewportSize ?: viewportState.value.size,
        enableZoom = enableZoom,
        onClick = onClick,
        scope = scope
    )
}

@Composable
fun Chart(
    modifier: Modifier = Modifier,
    chartState: ChartState,
    maxViewport: Viewport = chartState.viewport,
    minViewportSize: Size = chartState.viewport.size,
    maxViewportSize: Size = chartState.viewport.size,
    enableZoom: Boolean = false,
    onClick: (Offset, Data<*>) -> Unit = { _, _ -> },
    content: ChartScope.() -> Unit
) {
    val scope = ChartScopeImpl()
    scope.content()

    Chart(
        modifier = modifier,
        viewport = chartState.viewportState,
        maxViewport = maxViewport,
        minViewportSize = minViewportSize,
        maxViewportSize = maxViewportSize,
        enableZoom = enableZoom,
        onClick = onClick,
        scope = scope
    )
}

@Composable
private fun Chart(
    modifier: Modifier = Modifier,
    viewport: MutableState<Viewport>,
    maxViewport: Viewport = viewport.value,
    minViewportSize: Size = viewport.value.size,
    maxViewportSize: Size = viewport.value.size,
    enableZoom: Boolean = false,
    onClick: (Offset, Data<*>) -> Unit,
    scope: ChartScopeImpl
) {
    BoxWithConstraints(modifier = modifier) {
        val density = LocalDensity.current
        val renderedPoints = remember { mutableStateOf(listOf<BoundingShape>()) }
        val canvasSize = with(density) { Size(width = maxWidth.toPx(), height = maxHeight.toPx()) }
        val rendererContext = chartContext(viewport.value, canvasSize) // TODO can be remembered?

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(viewport) {
                    detectTransformGestures { _, pan, zoom, direction ->
                        val current = if (enableZoom) {
                            viewport.value.applyZoom(
                                zoom,
                                direction,
                                minViewportSize,
                                maxViewportSize
                            )
                        } else {
                            viewport.value
                        }

                        val dx = -pan.x / rendererContext.scaleX
                        val dy = pan.y / rendererContext.scaleY
                        viewport.value = current.applyPan(dx, dy, maxViewport)
                    }
                }
                .pointerInput(renderedPoints) {
                    detectTapGestures { offset ->
                        val point = renderedPoints.value
                            .firstOrNull { it.contains(offset) }
                            ?.data
                            ?: pointOf(
                                rendererContext.toChartX(offset.x),
                                rendererContext.toChartY(offset.y)
                            )
                        onClick(offset, point)
                    }
                }
        ) drawScope@{
            val rendererScope = RendererScope(this, rendererContext)
            scope.gridRenderers.forEach { with(it) { rendererScope.render() } }
            renderedPoints.value = scope
                .newSeries
                .flatMap { (series, other) ->
                    val (drawer, boundsProvider) = other
                    val rendererPoints = series
                        .map { with(it) { toRendererPoint(rendererScope.chartContext) } }

                    with(drawer) { rendererScope.draw(rendererPoints) }
                    boundsProvider.provide(rendererPoints)
                }

            scope.axisRenderers.forEach { with(it) { rendererScope.render() } }
            scope.labelsRenderers.forEach { with(it) { rendererScope.render() } }
        }

        scope.dataLabels.forEach {
            DataLabels(
                modifier = Modifier,
                renderedShapes = renderedPoints.value,
                canvasSize = canvasSize,
                labelContent = it
            )
        }

        AnchoredViews(
            modifier = Modifier,
            canvasSize = canvasSize,
            anchors = scope.anchors.mapKeys {
                it.key to Offset(
                    x = rendererContext.toRendererX(it.key.x),
                    y = rendererContext.toRendererY(it.key.y)
                )
            }
        )
    }
}

class ChartState(
    viewport: Viewport
) {
    internal val viewportState = mutableStateOf(viewport)

    val viewport: Viewport get() = viewportState.value
}