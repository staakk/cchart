package io.github.staakk.cchart.util

import org.junit.Assert.assertEquals
import org.junit.Test

class ListTest {

    @Test
    fun `should return first index matching the predicate from given position`() {
        assertEquals(2, listOf(0, 1, 2, 3, 4).indexOfFirstFrom(1) { it % 2 == 0})
    }

    @Test
    fun `should return -1 when no matching element found`() {
        assertEquals(-1, listOf(0, 1).indexOfFirstFrom(1) { it == 2})
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw when index is negative`() {
        listOf<Int>().indexOfFirstFrom(-1) { true }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw when index is larger than list size`() {
        listOf<Int>().indexOfFirstFrom(0) { true }
    }
}