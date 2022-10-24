package raytracer.utilities

import kotlin.math.sqrt

data class Normal(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0,
) {
    constructor(a: Double) : this(a, a, a)

    constructor(v: Vector3D) : this(v.x, v.y, v.z)

    constructor(n: Normal) : this(n.x, n.y, n.z)

    operator fun unaryMinus() = Normal(-x, -y, -z)

    operator fun plus(n: Normal) = Normal(x + n.x, y + n.y, z + n.z)

    /**
     * Dot product with a vector on the right
     */
    operator fun times(v: Vector3D): Double = x * v.x + y * v.y + z * v.z

    operator fun times(a: Double): Normal = Normal(x * a, y * a, z * a)

    operator fun div(a: Double) = Normal(x / a, y / a, z / a)

    /**
     * Convert normal to a unit normal
     */
    fun normalize(): Normal {
        val length = sqrt(x * x + y * y + z * z)
        return div(length)
    }
}