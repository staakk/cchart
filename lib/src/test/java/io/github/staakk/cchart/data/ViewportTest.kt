package io.github.staakk.cchart.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import io.github.staakk.cchart.data.Viewport.Companion.getViewport
import io.github.staakk.cchart.data.Viewport.Companion.getViewportFromGroupedSeries
import org.junit.Assert.*
import org.junit.Test

class ViewportTest {

    @Test
    fun `should create viewport from series`() {
        val series = listOf(
            seriesOf(
                pointOf(0f, 0f),
                pointOf(0.5f, 0.5f)
            ),
            seriesOf(
                pointOf(1f, 1f)
            )
        )

        val expected = Viewport(0f, 1f, 0f, 1f)
        assertEquals(expected, series.getViewport())
    }

    @Test
    fun `should create viewport from grouped series`() {
        val series = listOf(
            groupedSeriesOf(
                pointOf(0f, 0f),
                pointOf(0.5f, 0.5f)
            ),
            groupedSeriesOf(
                listOf(
                    pointOf(1f, 1f),
                    pointOf(1f, 2f)
                )
            )
        )

        val expected = Viewport(0f, 1f, 0f, 2f)
        assertEquals(expected, series.getViewportFromGroupedSeries())
    }

    @Test
    fun `should apply pan`() {
        val viewport = Viewport(0f, 1f, 0f, 1f)
        val maxViewport = Viewport(0f, 2f, 0f, 2f)

        assertEquals(
            Viewport(1f, 2f, 1f, 2f),
            viewport.applyPan(1f, 1f, maxViewport)
        )

        assertEquals(
            Viewport(1f, 2f, 1f, 2f),
            viewport.applyPan(2f, 2f, maxViewport)
        )

        assertEquals(
            Viewport(0f, 1f, 0f, 1f),
            viewport.applyPan(-2f, -2f, maxViewport)
        )
    }

    @Test
    fun `should apply zoom`() {
        val viewport = Viewport(0f, 1f, 0f, 1f)
        val minSize = Size(0.1f, 0.1f)
        val maxSize = Size(1.5f, 1.5f)

        assertEquals(
            Viewport(0.25f, 0.75f, 0.25f, 0.75f),
            viewport.applyZoom(2f, Offset(1f, 1f), minSize, maxSize)
        )

        assertEquals(
            Viewport(0.25f, 0.75f, 0f, 1f),
            viewport.applyZoom(2f, Offset(1f, 0f), minSize, maxSize)
        )

        assertEquals(
            Viewport(0f, 1f, 0.25f, 0.75f),
            viewport.applyZoom(2f, Offset(0f, 1f), minSize, maxSize)
        )

        assertEquals(
            Viewport(0.45f, 0.55f, 0.45f, 0.55f),
            viewport.applyZoom(100f, Offset(1f, 1f), minSize, maxSize)
        )

        assertEquals(
            Viewport(-0.25f, 1.25f, -0.25f, 1.25f),
            viewport.applyZoom(0.01f, Offset(1f, 1f), minSize, maxSize)
        )
    }

    @Test
    fun `should add viewports`() {
        val viewport1 = Viewport(0, 1, 0, 1)
        val viewport2 = Viewport(1, 2, 1, 2)

        assertEquals(
            Viewport(0, 2, 0, 2),
            viewport1 + viewport2
        )
    }

    @Test
    fun `should check if viewport contains point`() {
        val viewport = Viewport(0, 1, 0, 1)

        assertTrue(viewport.contains(pointOf(0.5f, 0.5f)))
        assertFalse(viewport.contains(pointOf(0.5f, 2f)))
        assertFalse(viewport.contains(pointOf(0.5f, -2f)))
        assertFalse(viewport.contains(pointOf(2f, 0.5f)))
        assertFalse(viewport.contains(pointOf(-2f, 0.5f)))
    }
}