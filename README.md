# Compose chart

Flexible and simple library for creating charts using Jetpack Compose.

### Disclaimer: this is not production ready code. I've created this lib to explore some custom drawing options of Jetpack Compose

## Features

* Multiple chart types supported out of the box:
    * Bar chart (also with grouping bars)
    * Line chart
    * Points chart
    * Combinations of all the above
* Zooming and panning
* Customisation flexibility:
    * Adjustable drawing of axis, labels, chart's data
    * Customisable grids
    * Possibility to provide own renderers for chart's elements
* Adding labels for data on chart

Here are few examples together with the code:

![Image of the chart](viewtest/screenshots/debug/io.github.staakk.cchart.viewtest.ReadmeGalleryTest_lineChart.png)
<details>
<summary>
<b>Click to view the code.</b>
</summary>

<!-- insert=line_chart -->
```kotlin
val horizontalLabelRenderer = horizontalLabelRenderer()
val verticalLabelRenderer = verticalLabelRenderer()
Chart(
  modifier = Modifier
    .padding(start = 32.dp, bottom = 32.dp)
    .aspectRatio(1f, false)
    .padding(bottom = 16.dp),
  viewport = Viewport(0f, 10f, 0f, 10f)
) {
  series(
    seriesOf(
      pointOf(0f, 1.3f),
      pointOf(1f, 2.4f),
      pointOf(2f, 2.3f),
      pointOf(3f, 4.8f),
      pointOf(4f, 4.3f),
      pointOf(5f, 5.3f),
      pointOf(6f, 5.7f),
      pointOf(7f, 6.3f),
      pointOf(8f, 6.1f),
      pointOf(9f, 8.3f),
      pointOf(10f, 9.1f),
    ),
    renderer = lineRenderer(lineDrawer = drawLine(brush = SolidColor(Blue)))
  )

  verticalAxis(
    verticalAxisRenderer(
      axisDrawer = axisDrawer(brush = SolidColor(DarkGrey))
    )
  )

  horizontalAxis(
    horizontalAxisRenderer(
      axisDrawer = axisDrawer(brush = SolidColor(DarkGrey))
    )
  )

  verticalAxisLabels(verticalLabelRenderer)

  horizontalAxisLabels(horizontalLabelRenderer)

  grid(
    gridRenderer(
      brush = SolidColor(LightGrey),
      orientation = GridOrientation.HORIZONTAL
    )
  )
}
```
</details>

![Image of the chart](viewtest/screenshots/debug/io.github.staakk.cchart.viewtest.ReadmeGalleryTest_barChart.png)
<details>
<summary>
<b>Click to view the code.</b>
</summary>

<!-- insert=bar_chart -->
```kotlin
val horizontalLabelRenderer = horizontalLabelRenderer(
  labelsProvider = object : LabelsProvider {
    private val pattern = "MMMM \nyyyy"
    private val formatter = DateTimeFormatter.ofPattern(pattern)

    override fun provide(
      min: Float,
      max: Float
    ): List<Pair<String, Float>> {
      var currentDate = LocalDate.ofEpochDay(min.toLong()).withDayOfMonth(1)
      val endDate = LocalDate.ofEpochDay(max.toLong()).withDayOfMonth(1)

      val labels = mutableListOf<Pair<String, Float>>()
      while (currentDate.isBefore(endDate)) {
        labels.add(
          currentDate.format(formatter) to currentDate.toEpochDay()
            .toFloat()
        )
        currentDate = currentDate.plusMonths(1)
      }
      return labels
    }
  }
)
val verticalLabelRenderer = verticalLabelRenderer { min, max ->
  (min.toInt()..max.toInt())
    .filter { it % 25 == 0 }
    .map { "$it%" to it.toFloat() }
}
Chart(
  modifier = Modifier
    .padding(start = 32.dp, bottom = 32.dp)
    .aspectRatio(1f, false)
    .padding(bottom = 16.dp),
  viewport = Viewport(
    minX = LocalDate.of(2020, 9, 1).toEpochDay(),
    maxX = LocalDate.of(2021, 1, 1).toEpochDay(),
    minY = 0f,
    maxY = 100f
  )
) {
  series(
    groupedSeriesOf(
      listOf(
        pointOf(LocalDate.of(2020, 10, 1).toEpochDay(), 78f),
        pointOf(LocalDate.of(2020, 10, 1).toEpochDay(), 68f),
      ),
      listOf(
        pointOf(LocalDate.of(2020, 11, 1).toEpochDay(), 56f),
        pointOf(LocalDate.of(2020, 11, 1).toEpochDay(), 45f),
      ),
      listOf(
        pointOf(LocalDate.of(2020, 12, 1).toEpochDay(), 82f),
        pointOf(LocalDate.of(2020, 12, 1).toEpochDay(), 86f),
      )
    ),
    renderer = barGroupRenderer(
      preferredWidth = 64f,
      barDrawer = drawBar { index, _ ->
        SolidColor(
          when (index) {
            0 -> DeepPurple
            1 -> Green
            else -> Pink
          }
        )
      }
    )
  )

  verticalAxis(
    verticalAxisRenderer(
      axisDrawer = axisDrawer(brush = SolidColor(DarkGrey))
    )
  )

  horizontalAxis(
    horizontalAxisRenderer(
      axisDrawer = axisDrawer(brush = SolidColor(DarkGrey))
    )
  )

  dataLabels {
    Text(
      modifier = Modifier.align(
        HorizontalAlignment.CENTER,
        VerticalAlignment.TOP
      ),
      text = "${data.y.toInt()}%",
      style = TextStyle(fontSize = 12.sp)
    )
  }

  verticalAxisLabels(verticalLabelRenderer)

  horizontalAxisLabels(horizontalLabelRenderer)
}
```
</details>

![Image of the chart](viewtest/screenshots/debug/io.github.staakk.cchart.viewtest.ReadmeGalleryTest_twoAxisChart.png)
<details>
<summary>
<b>Click to view the code.</b>
</summary>

<!-- insert=two_axis_chart -->
```kotlin
val horizontalLabelRenderer = horizontalLabelRenderer()
val density = LocalDensity.current
Chart(
  modifier = Modifier
    .padding(start = 32.dp, bottom = 32.dp, end = 32.dp)
    .aspectRatio(1f, false)
    .padding(bottom = 16.dp),
  viewport = Viewport(0f, 10f, 0f, 10f)
) {
  series(
    seriesOf(
      pointOf(0f, 1.3f),
      pointOf(1f, 2.4f),
      pointOf(2f, 2.3f),
      pointOf(3f, 4.8f),
      pointOf(4f, 4.3f),
      pointOf(5f, 5.3f),
      pointOf(6f, 5.7f),
      pointOf(7f, 6.3f),
      pointOf(8f, 6.1f),
      pointOf(9f, 8.3f),
      pointOf(10f, 9.1f),
    ),
    renderer = combine(
      lineRenderer(lineDrawer = drawLine(brush = SolidColor(Blue))),
      pointRenderer(
        size = with(density) { 8.dp.toPx() }.let { Size(it, it) },
        pointDrawer = drawCircle(brush = SolidColor(LightBlue))
      )
    )
  )

  series(
    seriesOf(
      pointOf(0f, 9.1f),
      pointOf(1f, 8.3f),
      pointOf(2f, 6.1f),
      pointOf(3f, 6.3f),
      pointOf(4f, 5.3f),
      pointOf(5f, 4.3f),
      pointOf(6f, 4.8f),
      pointOf(7f, 5.7f),
      pointOf(8f, 2.3f),
      pointOf(9f, 2.4f),
      pointOf(10f, 1.3f),
    ),
    renderer = combine(
      lineRenderer(lineDrawer = drawLine(brush = SolidColor(Green))),
      pointRenderer(
        size = with(density) { 8.dp.toPx() }.let { Size(it, it) },
        pointDrawer = drawCircle(brush = SolidColor(LightGreen))
      )
    )
  )

  verticalAxis(
    verticalAxisRenderer(
      axisDrawer = axisDrawer(brush = SolidColor(Blue)),
      location = 0f
    )
  )

  verticalAxis(
    verticalAxisRenderer(
      axisDrawer = axisDrawer(brush = SolidColor(Green)),
      location = 1f
    )
  )

  horizontalAxis(
    horizontalAxisRenderer(
      axisDrawer = axisDrawer(brush = SolidColor(DarkGrey))
    )
  )

  verticalAxisLabels(
    verticalLabelRenderer(
      paint = Paint().apply {
        color = Blue.toArgb()
        typeface = Typeface.DEFAULT
        textSize = with(density) { 12.sp.toPx() }
        isAntiAlias = true
      },
      location = 0f,
      alignment = Alignment.CenterLeft
    )
  )

  verticalAxisLabels(verticalLabelRenderer(
    paint = Paint().apply {
      color = Green.toArgb()
      typeface = Typeface.DEFAULT
      textSize = with(density) { 12.sp.toPx() }
      isAntiAlias = true
    },
    location = 1f,
    alignment = Alignment.CenterRight,
    labelOffset = Offset(12f, 0f)
  ) { min, max -> (min.toInt()..(max.toInt() + 1)).map { "${it * 2}" to it.toFloat() } })

  horizontalAxisLabels(horizontalLabelRenderer)

  grid(
    gridRenderer(
      brush = SolidColor(LightGrey),
      orientation = GridOrientation.HORIZONTAL
    )
  )
}
```
</details>

For more examples check out [the samples](samples/src/main/java/io/github/staakk/cchart/samples).
