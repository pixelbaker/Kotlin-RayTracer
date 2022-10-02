package raytracer.world

import raytracer.geometries.Sphere
import raytracer.tracers.Tracer
import raytracer.utilities.RGBColor

class World {
    var sphere = Sphere()
    var tracer: Tracer? = null
    val viewPlane: ViewPlane = ViewPlane()

    fun displayPixel(row: Int, column: Int, pixelColor: RGBColor) {

    }
}