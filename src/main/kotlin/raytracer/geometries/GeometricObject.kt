package raytracer.geometries

import raytracer.materials.Material
import raytracer.utilities.Ray
import raytracer.utilities.RayParam
import raytracer.utilities.ShadingRecord

abstract class GeometricObject() {

    constructor(obj: GeometricObject) : this() {
        material = obj.material.clone()
    }

    abstract fun clone(): GeometricObject

    abstract fun hit(ray: Ray, tmin: RayParam, shadingRecord: ShadingRecord): Boolean

    lateinit var material: Material
}