package raytracer.geometries

import raytracer.materials.Material
import raytracer.utilities.*

abstract class GeometricObject() {

    constructor(obj: GeometricObject) : this() {
        material = obj.material?.clone()
    }

    abstract fun clone(): GeometricObject

    abstract fun hit(ray: Ray, tmin: RayParam, shadingRecord: ShadingRecord): Boolean

    var material: Material? = null
    var color: RGBColor = RGBColor(white) //Deprecated as soon as material works
}