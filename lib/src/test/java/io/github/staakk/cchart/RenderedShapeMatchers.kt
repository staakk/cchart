package io.github.staakk.cchart

import io.github.staakk.cchart.renderer.BoundingShape
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import kotlin.math.abs

class RectMatcher(
    private val expected: BoundingShape.Rect,
    private val precision: Float = 0.001f
) : TypeSafeMatcher<BoundingShape>() {
    override fun describeTo(description: Description?) {
        description?.appendText("RenderedShape.Rect")
    }

    override fun matchesSafely(item: BoundingShape?): Boolean {
        return item != null &&
                item is BoundingShape.Rect &&
                expected.data == item.data &&
                abs(expected.labelAnchorX - item.labelAnchorX) < precision &&
                abs(expected.labelAnchorY - item.labelAnchorY) < precision &&
                abs(expected.topLeft.x - item.topLeft.x) < precision &&
                abs(expected.topLeft.y - item.topLeft.y) < precision &&
                abs(expected.bottomRight.x - item.bottomRight.x) < precision &&
                abs(expected.bottomRight.y - item.bottomRight.y) < precision
    }
}

fun matchesRect(expected: BoundingShape.Rect, precision: Float = 0.001f): Matcher<BoundingShape> =
    RectMatcher(expected, precision)