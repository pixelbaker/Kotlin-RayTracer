package raytracer.tracers

import raytracer.utilities.*
import raytracer.world.World

class SingleSphere(world: World) : Tracer(world) {
    override fun trace(ray: Ray) =
        if (world.sphere.hit(ray, RayParam(), ShadingRecord(world)))
            RGBColor(red)
        else
            RGBColor(black)
}