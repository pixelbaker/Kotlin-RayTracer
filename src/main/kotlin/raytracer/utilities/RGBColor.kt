package raytracer.utilities

import kotlin.math.max
import kotlin.math.pow

val black = { RGBColor(0.0) }
val white = { RGBColor(1.0) }
val red = { RGBColor(1.0, 0.0, 0.0) }
val green = { RGBColor(0.0, 1.0, 0.0) }


data class RGBColor(
    var r: Double = 0.0,
    var g: Double = 0.0,
    var b: Double = 0.0,
) {
    private val maxValue
        get() = max(r, max(g, b))

    constructor(a: Double) : this(a, a, a)

    constructor(c: RGBColor) : this(c.r, c.g, c.b)

    operator fun plus(c: RGBColor) = RGBColor(r + c.r, g + c.g, b + c.b)

    operator fun div(a: Double) = RGBColor(r / a, g / a, b / a)

    operator fun divAssign(a: Int) = divAssign(a.toDouble())

    operator fun divAssign(a: Double) {
        r /= a
        g /= a
        b /= a
    }

    operator fun times(c: RGBColor) = RGBColor(r * c.r, g * c.g, b * c.b)

    operator fun times(scale: Double) = RGBColor(r * scale, g * scale, b * scale)

    fun average() = (1.0 / 3.0) * (r + g + b)

    fun powc(p: Double) = RGBColor(r.pow(p), g.pow(p), b.pow(p))

    fun maxToOne(): RGBColor {
        return if (maxValue > 1.0) (this / maxValue) else this
    }

    fun clampToColor(): RGBColor {
        return if (maxValue > 1.0) red() else this
    }
}
