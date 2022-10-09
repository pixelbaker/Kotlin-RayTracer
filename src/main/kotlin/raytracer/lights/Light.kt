package raytracer.lights

import raytracer.utilities.RGBColor
import raytracer.utilities.ShadingRecord
import raytracer.utilities.Vector3D
import raytracer.utilities.black

abstract class Light {
    fun radiance(shadingRecord: ShadingRecord): RGBColor {
        return RGBColor(black)
    }

    abstract fun getDirection(shadingRecord: ShadingRecord): Vector3D
}
