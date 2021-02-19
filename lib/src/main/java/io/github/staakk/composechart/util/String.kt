package io.github.staakk.composechart.util

fun String.countLines(): Int {
    var count = 1
    var wasReturn = false
    val iterator = iterator()
    while (iterator.hasNext()) {
        var current = iterator.next()
        if (current == '\n' && wasReturn) current = iterator.next()
        when (current) {
            '\n' -> count++
            '\r' -> {
                count++
                wasReturn = true
            }
        }
    }
    return count
}