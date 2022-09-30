package raytracer.cameras

import raytracer.utilities.Point3D
import raytracer.utilities.Vector3D
import raytracer.world.World

abstract class Camera(
    var eye: Point3D = Point3D(.0, .0, 500.0),
    var lookat: Point3D = Point3D(),
    var rollAngle: Double = .0,
    var u: Vector3D = Vector3D(1.0, 0.0, 0.0),
    var v: Vector3D = Vector3D(0.0, 1.0, 0.0),
    var w: Vector3D = Vector3D(0.0, 0.0, 1.0),
    var up: Vector3D = Vector3D(0.0, 1.0, 0.0),
    var exposureTime: Double = 1.0,
) {
    constructor(c: Camera) : this(
        eye = Point3D(c.eye),
        lookat = Point3D(c.lookat),
        rollAngle = c.rollAngle,
        u = Vector3D(c.u),
        v = Vector3D(c.v),
        w = Vector3D(c.w),
        up = Vector3D(c.up),
        exposureTime = c.exposureTime,
    )

    abstract fun renderScene(world: World)

    fun compute_uvw() {
        w = eye - lookat
        w.normalize()
        u = up % w
        u.normalize()
        v = w % u

        // take care of the singularity by hardwiring in specific camera orientations
        if (eye.x == lookat.x &&
            eye.z == lookat.z &&
            eye.y > lookat.y
        ) {
            // camera looking vertically down
            u = Vector3D(0.0, 0.0, 1.0)
            v = Vector3D(1.0, 0.0, 0.0)
            w = Vector3D(0.0, 1.0, 0.0)
        }

        if (eye.x == lookat.x &&
            eye.z == lookat.z &&
            eye.y < lookat.y
        ) {
            // camera looking vertically up
            u = Vector3D(1.0, 0.0, 0.0)
            v = Vector3D(0.0, 0.0, 1.0)
            w = Vector3D(0.0, -1.0, 0.0)
        }
    }
}