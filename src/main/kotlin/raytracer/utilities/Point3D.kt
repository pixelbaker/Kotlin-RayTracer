package raytracer.utilities

import kotlin.math.sqrt

data class Point3D(
    val x: Double = 0.0,
    val y: Double = 0.0,
    val z: Double = 0.0,
) {
    constructor(a: Double) : this(a, a, a)

    constructor(p: Point3D) : this(p.x, p.y, p.z)

    operator fun unaryMinus() = Point3D(-x, -y, -z)

    operator fun minus(p: Point3D) = Vector3D(x - p.x, y - p.y, z - p.z)

    operator fun plus(v: Vector3D) = Point3D(x + v.x, y + v.y, z + v.z)

    operator fun minus(p: Vector3D) = Point3D(x - p.x, y - p.y, z - p.z)

    operator fun times(a: Double) = Point3D(x * a, y * a, z * a)

    fun d_squared(p: Point3D): Double =
        (x - p.x) * (x - p.x) +
                (y - p.y) * (y - p.y) +
                (z - p.z) * (z - p.z)

    fun distance(p: Point3D): Double = sqrt(
        (x - p.x) * (x - p.x) +
                (y - p.y) * (y - p.y) +
                (z - p.z) * (z - p.z)
    )
}
