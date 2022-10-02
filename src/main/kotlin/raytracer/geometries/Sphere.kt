package raytracer.geometries

import raytracer.utilities.*
import kotlin.math.sqrt

private const val kEpsilon = 0.001

class Sphere : GeometricObject {
    var center: Point3D = Point3D()
    var radius: Double = 1.0


    constructor(
        center: Point3D = Point3D(),
        radius: Double = 1.0,
    ) : super() {
        this.center = Point3D(center)
        this.radius = radius
    }

    constructor(s: Sphere) : super(s) {
        center = Point3D(s.center)
        radius = s.radius
    }

    override fun clone() = Sphere(this)

    override fun hit(ray: Ray, tmin: RayParam, shadingRecord: ShadingRecord): Boolean {
        var t: Double
        val temp: Vector3D = ray.origin - center
        val a: Double = ray.direction * ray.direction
        val b: Double = 2.0 * (temp * ray.direction)
        val c: Double = temp * temp - radius * radius
        val disc: Double = b * b - 4.0 * a * c

        if (disc < 0.0)
            return false
        else {
            val e = sqrt(disc)
            val denom = 2.0 * a

            // smaller root
            t = (-b - e) / denom

            if (t > kEpsilon) {
                tmin.t = t
                shadingRecord.normal = Normal((temp + (ray.direction * t)) / radius)
                shadingRecord.localHitPoint = ray.origin + (ray.direction * t)
                return true
            }

            // larger root
            t = (-b + e) / denom

            if (t > kEpsilon) {
                tmin.t = t
                shadingRecord.normal = Normal((temp + (ray.direction * t)) / radius)
                shadingRecord.localHitPoint = ray.origin + (ray.direction * t)
                return true
            }
        }
        return false
    }
}
