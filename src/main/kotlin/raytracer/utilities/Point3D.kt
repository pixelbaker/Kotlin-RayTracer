package raytracer.utilities

class Point3D(
    val x: Double = 0.0,
    val y: Double = 0.0,
    val z: Double = 0.0,) {

    constructor(a: Double): this(a, a, a)

    constructor(p: Point3D): this(p.x, p.y, p.z)

    operator fun unaryMinus() = Point3D(-x, -y, -z)
}