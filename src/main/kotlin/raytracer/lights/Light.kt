package raytracer.lights

import raytracer.utilities.RGBColor
import raytracer.utilities.ShadingRecord
import raytracer.utilities.Vector3D
import raytracer.utilities.black

abstract class Light {
    open fun getRadiance(shadingRecord: ShadingRecord): RGBColor {
        return black()
    }

    abstract fun getDirection(shadingRecord: ShadingRecord): Vector3D

    abstract fun clone(): Light
}
