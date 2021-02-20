package io.github.staakk.cchart.data

import java.time.LocalDate

data class Point(
    val x: Float,
    val y: Float,
)

fun pointOf(x: Float, y: Float) = Point(x, y)

fun pointOf(x: Int, y: Int) = Point(x.toFloat(), y.toFloat())

fun pointOf(x: Long, y: Long) = Point(x.toFloat(), y.toFloat())

fun pointOf(x: Double, y: Double) = Point(x.toFloat(), y.toFloat())

fun pointOf(x: LocalDate, y: Float) = Point(x.toEpochDay().toFloat(), y)
