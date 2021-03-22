package io.github.staakk.cchart.data

import org.junit.Assert.assertEquals
import org.junit.Test

class SeriesTest {

    private val viewport = Viewport(minX = 0f, maxX = 1f, minY = 0f, maxY = 1f)

    @Test
    fun `should return empty list for line not in viewport`() {
        val series = Series(listOf(pointOf(-1f, -1f), pointOf(-0.5f, -1f)))

        assertEquals(emptyList<Point>(), series.getLineInViewport(viewport))
    }

    @Test
    fun `should return previous and next point for line crossing the viewport`() {
        val series = seriesOf(
            pointOf(-1, 0.5f),
            pointOf(-0.5, 0.5f),
            pointOf(0.5, 0.5f),
            pointOf(1.5, 0.5f),
            pointOf(2.0, 0.5f),
        )

        assertEquals(
            listOf(
                pointOf(-0.5, 0.5f),
                pointOf(0.5, 0.5f),
                pointOf(1.5, 0.5f),
            ),
            series.getLineInViewport(viewport)
        )
    }

    @Test
    fun `should return line inside viewport`() {
        val series = seriesOf(
            pointOf(0.1, 0.5f),
            pointOf(0.5, 0.5f),
            pointOf(0.9, 0.5f),
        )

        assertEquals(
            listOf(
                pointOf(0.1, 0.5f),
                pointOf(0.5, 0.5f),
                pointOf(0.9, 0.5f),
            ),
            series.getLineInViewport(viewport)
        )
    }

    @Test
    fun `should return line segments inside viewport`() {
        val series = seriesOf(
            pointOf(-1, 0.5f),
            pointOf(-0.5, 0.5f),
            pointOf(0.5, 0.5f),
            pointOf(1.5, 0.5f),
            pointOf(2.0, 0.5f),
            pointOf(1.5, 0.5f),
            pointOf(0.5, 0.5f),
            pointOf(-0.5, 0.5f),
            pointOf(-1, 0.5f),
        )

        assertEquals(
            listOf(
                pointOf(-0.5, 0.5f),
                pointOf(0.5, 0.5f),
                pointOf(1.5, 0.5f),
                pointOf(1.5, 0.5f),
                pointOf(0.5, 0.5f),
                pointOf(-0.5, 0.5f),
            ),
            series.getLineInViewport(viewport)
        )
    }

}