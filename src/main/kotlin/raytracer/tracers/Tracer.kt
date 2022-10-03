package raytracer.tracers

import raytracer.utilities.RGBColor
import raytracer.utilities.Ray
import raytracer.utilities.black
import raytracer.world.World

abstract class Tracer(protected val world: World) {
    /**
     * Only overridden in the tracers SingleSphere and MultipleObjects
     */
    open fun trace(ray: Ray) = RGBColor(black)
    
    open fun trace(ray: Ray, depth: Int) = RGBColor(black)
}