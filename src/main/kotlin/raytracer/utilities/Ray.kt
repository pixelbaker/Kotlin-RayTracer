package raytracer.utilities

data class Ray(
    var origin: Point3D = Point3D(),
    var direction: Vector3D = Vector3D(),
) {
    constructor(ray: Ray) : this(
        Point3D(ray.origin),
        Vector3D(ray.direction)
    )
}
