package io.github.staakk.cchart.data

data class Data(
    val series: List<Series>
) {
    val bounds: DataBounds

    init {
        var maxX = Float.MIN_VALUE
        var minX = Float.MAX_VALUE
        var maxY = Float.MIN_VALUE
        var minY = Float.MAX_VALUE

        series.forEach { s ->
            s.points.forEach { p ->
                if (p.x > maxX) maxX = p.x
                if (p.x < minX) minX = p.x
                if (p.y > maxY) maxY = p.y
                if (p.y < minY) minY = p.y
            }
        }

        bounds = DataBounds(
            maxX = maxX,
            minX = minX,
            maxY = maxY,
            minY = minY,
        )
    }
}