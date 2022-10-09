package raytracer.tracers

import raytracer.utilities.RGBColor
import raytracer.utilities.Ray
import raytracer.world.World

class MultipleObjects(world: World) : Tracer(world) {
    override fun trace(ray: Ray): RGBColor {
        val shadingRecord = world.hitObjects(ray)

        return if (shadingRecord.hitAnObject)
            RGBColor(shadingRecord.color)
        else
            RGBColor(world.backgroundColor)
    }
}