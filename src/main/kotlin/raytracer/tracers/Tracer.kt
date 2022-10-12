package raytracer.tracers

import raytracer.utilities.Ray
import raytracer.utilities.black
import raytracer.world.World

abstract class Tracer(protected val world: World) {

    open fun trace(ray: Ray, depth: Int) = black()
}