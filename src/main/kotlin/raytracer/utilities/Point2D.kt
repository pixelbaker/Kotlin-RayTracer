package raytracer.utilities

data class Point2D(
    val x: Double = 0.0,
    val y: Double = 0.0) {

    constructor(arg: Double) : this(arg, arg)

    constructor(p: Point2D) : this(p.x, p.y)

    operator fun times(a: Double) = Point2D(x * a, y * a)
}
