package raytracer.brdfs

import raytracer.utilities.RGBColor
import raytracer.utilities.ShadingRecord
import raytracer.utilities.Vector3D


const val invPI: Double = 0.3183098861837906715

class Lambertian(
    var kd: Double = 0.0,
    var cd: RGBColor = RGBColor(0.0),
) : BRDF() {
    
    constructor(lambertian: Lambertian) : this(kd = lambertian.kd, cd = RGBColor(lambertian.cd))

    override fun clone() = Lambertian(this)

    override fun f(shadingRecord: ShadingRecord, wo: Vector3D, wi: Vector3D) = cd * kd * invPI

    override fun rho(shadingRecord: ShadingRecord, wo: Vector3D) = cd * kd
}