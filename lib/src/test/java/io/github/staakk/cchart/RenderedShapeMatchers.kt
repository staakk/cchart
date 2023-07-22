package io.github.staakk.cchart

import io.github.staakk.cchart.bounds.Bounds
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import kotlin.math.abs

class RectMatcher(
    private val expected: Bounds.Rect,
    private val precision: Float = 0.001f
) : TypeSafeMatcher<Bounds>() {
    override fun describeTo(description: Description?) {
        description?.appendText("$expected")
    }

    override fun matchesSafely(item: Bounds?): Boolean {
        return item != null &&
                item is Bounds.Rect &&
                expected.point == item.point &&
                abs(expected.labelAnchorX - item.labelAnchorX) < precision &&
                abs(expected.labelAnchorY - item.labelAnchorY) < precision &&
                abs(expected.topLeft.x - item.topLeft.x) < precision &&
                abs(expected.topLeft.y - item.topLeft.y) < precision &&
                abs(expected.bottomRight.x - item.bottomRight.x) < precision &&
                abs(expected.bottomRight.y - item.bottomRight.y) < precision
    }
}

fun matchesRect(expected: Bounds.Rect, precision: Float = 0.001f): Matcher<Bounds> =
    RectMatcher(expected, precision)