package raytracer.utilities

import raytracer.materials.Material
import raytracer.world.World

data class ShadingRecord(
    val world: World,

    /**
     * Did the ray hit an object?
     */
    var hitAnObject: Boolean = false,

    /**
     * Pointer to the nearest object's material
     */
    var material: Material? = null,

    /**
     * World coordinates of intersection
     */
    var hitPoint: Point3D = Point3D(),

    /**
     * World coordinates of hit point on generic object (used for texture transformations)
     */
    var localHitPoint: Point3D = Point3D(),

    /**
     * Normal at hit point
     */
    var normal: Normal = Normal(),

    /**
     * Required for specular highlights and area lights
     */
    var ray: Ray = Ray(),

    /**
     * recursion depth
     */
    var depth: Int = 0,

    /**
     * ray parameter
     */
    var t: Double = 0.0,

    var color: RGBColor = RGBColor(),
) {

    constructor(shadingRecord: ShadingRecord) : this(
        world = shadingRecord.world,
        hitAnObject = shadingRecord.hitAnObject,
        material = shadingRecord.material,
        hitPoint = Point3D(shadingRecord.hitPoint),
        localHitPoint = Point3D(shadingRecord.localHitPoint),
        normal = Normal(shadingRecord.normal),
        ray = Ray(shadingRecord.ray),
        depth = shadingRecord.depth,
        t = shadingRecord.t,
    )
    
}
