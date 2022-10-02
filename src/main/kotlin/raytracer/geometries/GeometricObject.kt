package raytracer.geometries

import raytracer.materials.Material
import raytracer.utilities.Ray
import raytracer.utilities.ShadingRecord

abstract class GeometricObject() {
    constructor(obj: GeometricObject) : this() {
        material = obj.material?.clone()
    }

    abstract fun clone(): GeometricObject

    abstract fun hit(ray: Ray, t: Double, shadingRecord: ShadingRecord): Boolean

    var material: Material? = null
        protected set
}