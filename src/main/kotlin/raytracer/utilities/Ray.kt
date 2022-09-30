package raytracer.utilities

class Ray(
    val origin: Point3D = Point3D(),
    val direction: Vector3D = Vector3D()
) {
    constructor(ray: Ray) : this(
        Point3D(ray.origin),
        Vector3D(ray.direction)
    )
}