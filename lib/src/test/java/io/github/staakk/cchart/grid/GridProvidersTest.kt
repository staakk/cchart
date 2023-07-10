package io.github.staakk.cchart.grid

import io.github.staakk.cchart.grid.GridLinesProviders
import org.junit.Assert.assertEquals
import org.junit.Test

class GridProvidersTest {

    @Test
    fun `should provide ints in range`() {
        assertEquals(
            listOf(0f, 1f, 2f, 3f, 4f, 5f),
            GridLinesProviders.intGrid.provide(0f, 5f)
        )
    }

    @Test
    fun `should provide multiples in range`() {
        assertEquals(
            listOf(0f, 0.2f, 0.4f, 0.6f, 0.8f, 1f),
            GridLinesProviders.multiple(0.2f).provide(0f, 1f)
        )

        assertEquals(
            listOf(0f, 2f, 4f, 6f, 8f, 10f),
            GridLinesProviders.multiple(2f).provide(0f, 10f)
        )

        assertEquals(
            listOf(-2f, -1f, 0f, 1f, 2f),
            GridLinesProviders.multiple(1f).provide(-2.3f, 2.3f)
        )
    }
}