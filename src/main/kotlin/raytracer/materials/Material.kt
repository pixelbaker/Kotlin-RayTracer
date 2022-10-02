package raytracer.materials

import raytracer.utilities.RGBColor
import raytracer.utilities.ShadingRecord
import raytracer.utilities.black

abstract class Material {
    open fun shade(shadingRecord: ShadingRecord) = RGBColor(black)
    abstract fun clone(): Material
}