package raytracer.cameras

import raytracer.utilities.Point2D
import raytracer.utilities.Vector3D
import raytracer.world.World

class Pinhole : Camera {
    var viewPlaneDistance: Double = 500.0
    var zoom: Double = 1.0

    constructor(
        viewDistance: Double = 500.0,
        zoom: Double = 1.0,
    ) : super() {
        this.viewPlaneDistance = viewDistance
        this.zoom = zoom
    }

    constructor(c: Pinhole) : super(c) {
        viewPlaneDistance = c.viewPlaneDistance
        zoom = c.zoom
    }

    override fun renderScene(world: World) {
        TODO("Not yet implemented")
    }

    fun getDirection(p: Point2D): Vector3D {
        val u = this.u * p.x
        val v = this.v * p.y
        val w = this.w * viewPlaneDistance
        val direction = u + v - w
        direction.normalize()
        return direction
    }
}

private operator fun Double.times(vector: Vector3D): Vector3D {
    return vector * this
}
