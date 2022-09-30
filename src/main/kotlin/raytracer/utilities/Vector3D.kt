package raytracer.utilities

import kotlin.math.sqrt

data class Vector3D(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0,
) {
    constructor(a: Double) : this(a, a, a)

    constructor(v: Vector3D) : this(v.x, v.y, v.z)

    constructor(p: Point3D) : this(p.x, p.y, p.z)

    operator fun unaryMinus() = Vector3D(-x, -y, -z)

    operator fun times(a: Double) = Vector3D(x * a, y * a, z * a)

    operator fun times(v: Vector3D) = x * v.x + y * v.y + z * v.z

    operator fun div(a: Double) = Vector3D(x / a, y / a, z / a)

    operator fun plus(v: Vector3D) = Vector3D(x + v.x, y + v.y, z + v.z)

    operator fun plusAssign(v: Vector3D) {
        x += v.x
        y += v.y
        z += v.z
    }

    operator fun minus(v: Vector3D) = Vector3D(x - v.x, y - v.y, z - v.z)

    operator fun rem(v: Vector3D) = Vector3D(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x)

    fun lengthSquared() = x * x + y * y + z * z

    fun length() = sqrt(lengthSquared())

    fun normalize() {
        val length = sqrt(x * x + y * y + z * z)
        x /= length
        y /= length
        z /= length
    }

    fun hat(): Vector3D {
        normalize()
        return this
    }
}
