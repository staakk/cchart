package io.github.staakk.cchart.util

inline fun <T> List<T>.indexOfFirstFrom(fromIndex: Int, predicate: (T) -> Boolean): Int {
    require(fromIndex < this.size) { "Index out of bounds $fromIndex, size: ${this.size}"}
    require(fromIndex >= 0) { "Index cannot be negative." }
    for (i in fromIndex until this.size) {
        if (predicate(this[i])) return i
    }
    return -1
}