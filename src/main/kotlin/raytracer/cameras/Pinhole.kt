package raytracer.cameras

import raytracer.utilities.*
import raytracer.world.ViewPlane
import raytracer.world.World
import kotlin.math.sqrt

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
        val viewPlane = ViewPlane(world.viewPlane)
        val ray = Ray()
        var depth = 0
        val samplePoint = Point2D()
        val num: Int = sqrt(viewPlane.numSamples.toDouble()).toInt()

        viewPlane.pixelSize /= zoom
        ray.origin = Point3D(eye)

        for (row in 0 until viewPlane.vres) {
            for (column in 0 until viewPlane.hres) {
                val radiance = black()

                for (sub_row in 0 until num) {
                    for (sub_column in 0 until num) {
                        samplePoint.x = viewPlane.pixelSize * (column - 0.5 * viewPlane.hres + (sub_column + .5) / num)
                        samplePoint.y = viewPlane.pixelSize * (row - 0.5 * viewPlane.vres + (sub_row + .5) / num)
                        ray.direction = getDirection(samplePoint)
                        radiance += world.tracer.trace(ray, depth)
                    }
                }

                radiance /= viewPlane.numSamples
                radiance *= exposureTime

                world.drawPixel(row, column, radiance)
            }
        }
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
