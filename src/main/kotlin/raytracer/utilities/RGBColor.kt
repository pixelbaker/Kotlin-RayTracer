package raytracer.utilities

import kotlin.math.pow

data class RGBColor(
    var r: Double = 0.0,
    var g: Double = 0.0,
    var b: Double = 0.0,
) {

    constructor(a: Double) : this(a, a, a)

    constructor(c: RGBColor) : this(c.r, c.g, c.b)

    operator fun plus(c: RGBColor) = RGBColor(r + c.r, g + c.g, b + c.b)

    operator fun plusAssign(c: RGBColor) {
        r += c.r
        g += c.g
        b += c.b
    }

    operator fun times(a: Double) = RGBColor(r * a, g * a, b * a)

    operator fun timesAssign(a: Double) {
        r *= a
        g *= a
        b *= a
    }

    operator fun div(a: Double) = RGBColor(r / a, g / a, b / a)

    operator fun divAssign(a: Double) {
        r /= a
        g /= a
        b /= a
    }

    operator fun times(c: RGBColor) = RGBColor(r * c.r, g * c.g, b * c.b)

    fun average() = (1.0 / 3.0) * (r + g + b)

    fun powc(p: Double) = RGBColor(r.pow(p), g.pow(p), b.pow(p))
}