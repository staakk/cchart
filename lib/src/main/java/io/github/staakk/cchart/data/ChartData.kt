package io.github.staakk.cchart.data

data class ChartData(
    val series: List<Series>
) {
    val bounds: DataBounds

    init {
        var maxX = Int.MIN_VALUE
        var minX = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        var minY = Int.MAX_VALUE

        series.forEach { s ->
            s.points.forEach { p ->
                if (p.x > maxX) maxX = p.x
                if (p.x < minX) minX = p.x
                if (p.y > maxY) maxY = p.y
                if (p.y < minY) minY = p.y
            }
        }

        bounds = DataBounds(
            maxX = maxX.toFloat(),
            minX = minX.toFloat(),
            maxY = maxY.toFloat(),
            minY = minY.toFloat(),
        )
    }
}