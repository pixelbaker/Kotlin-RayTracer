package raytracer.tracers

import raytracer.utilities.RGBColor
import raytracer.utilities.Ray
import raytracer.world.World

class MultipleObjects(world: World) : Tracer(world) {
    override fun trace(ray: Ray, depth: Int): RGBColor {
        //val shadingRecord = world.hitBareBonesObjects(ray)
        val shadingRecord = world.hitObjects(ray)

        return if (shadingRecord.hitAnObject) {
            shadingRecord.ray = Ray(ray)
            shadingRecord.material!!.shade(shadingRecord)
        } else {
            RGBColor(world.backgroundColor)
        }
    }
}