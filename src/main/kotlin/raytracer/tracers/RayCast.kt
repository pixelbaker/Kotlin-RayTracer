package raytracer.tracers

import raytracer.utilities.RGBColor
import raytracer.utilities.Ray
import raytracer.world.World

class RayCast(world: World) : Tracer(world) {
    override fun trace(ray: Ray, depth: Int): RGBColor {
        val shadingRecord = world.hitObjects(ray)

        return if (shadingRecord.hitAnObject) {
            shadingRecord.ray = Ray(ray) // used for specular shading
            shadingRecord.material!!.shade(shadingRecord)
        } else {
            RGBColor(world.backgroundColor)
        }
    }
}