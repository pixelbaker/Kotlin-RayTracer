package raytracer.materials

import raytracer.utilities.ShadingRecord
import raytracer.utilities.black

abstract class Material {
    open fun shade(shadingRecord: ShadingRecord) = black()
    abstract fun clone(): Material
}