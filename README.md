# Compose chart

[![](https://jitpack.io/v/staakk/cchart.svg)](https://jitpack.io/#staakk/cchart)

Flexible and simple library for creating charts using Jetpack Compose.

__Note__: currently this library is under development and is using some beta/alpha release components.

![Image of the chart](lib/src/test/snapshots/images/io.github.staakk.cchart.readme_ReadmeImageTest_recordReadmeImage.png)

## How to use __cchart__

You can find the documentation here [staakk.github.io/cchart/](https://staakk.github.io/cchart/)

Add the following dependencies to your project:
```groovy
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```

```groovy
dependencies {
        implementation "com.github.staakk:cchart:$version"
}
```

Where `version` can be either a tag or a commit hash.

## Examples

For example usage check out [the samples](samples/src/main/java/io/github/staakk/cchart/samples).

## Features

Some of the notable features are:
* Support for panning and zooming
* Built in renderers for the following the following types of charts:
  * Line
  * Point
  * Bars (including grouping of different series)
* Easy to extend
* Easy to add animations
* Support for user defined grids and axes
* Support for zooming and panning
* Clickable data
* Showing arbitrary compose views on the chart
