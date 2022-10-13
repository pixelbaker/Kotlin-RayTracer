package raytracer.cameras

import raytracer.utilities.*
import raytracer.world.ViewPlane
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
        val viewPlane = ViewPlane(world.viewPlane)
        val ray = Ray()
        var depth = 0
        val pixelPoint = Point2D()

        viewPlane.pixelSize /= zoom
        ray.origin = Point3D(eye)

        for (row in 0 until viewPlane.vres) {
            for (column in 0 until viewPlane.hres) {
                val radiance = black()

                repeat(viewPlane.numSamples) {
                    val samplePoint = viewPlane.sampler.sampleUnitSquare()

                    pixelPoint.x = viewPlane.pixelSize * (column - 0.5 * viewPlane.hres + samplePoint.x)
                    pixelPoint.y = viewPlane.pixelSize * (row - 0.5 * viewPlane.vres + samplePoint.y)
                    ray.direction = getDirection(pixelPoint)
                    radiance += world.tracer.trace(ray, depth)
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
