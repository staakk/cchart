package io.github.staakk.cchart

/**
 * Contains commonly used zoom settings.
 *
 * @see Chart
 */
object ZoomRange {
    /**
     * No zoom.
     */
    val None = 1f..1f

    /**
     * Unrestricted zoom.
     */
    val Unrestricted = 0f..Float.MAX_VALUE
}