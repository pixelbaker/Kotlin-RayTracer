package raytracer.utilities

class Point2D(
    var x: Double = 0.0,
    var y: Double = 0.0) {

    constructor(arg: Double) : this() {
        this.x = arg
        this.y = arg
    }

    constructor(p: Point2D) : this() {
        this.x = p.x
        this.y = p.y
    }

    fun assignment(rhs : Point2D) =
        if (rhs == this)
            this
        else {
            x = rhs.x
            y = rhs.y
            this
        }

    operator fun times(a: Double) = Point2D(x * a, y * a)
}
