package raytracer.materials

import raytracer.brdfs.Lambertian
import raytracer.utilities.RGBColor
import raytracer.utilities.ShadingRecord

class Matte(
    private val ambient: Lambertian = Lambertian(),
    private val diffuse: Lambertian = Lambertian(),
) : Material() {
    override fun clone(): Material = Matte(this)

    constructor(matte: Matte) : this(matte.ambient, matte.diffuse)

    fun setKa(ka: Double) {
        ambient.kd = ka
    }

    fun getKa() = ambient.kd

    fun setKd(kd: Double) {
        diffuse.kd = kd
    }

    fun getKd() = diffuse.kd

    fun setCd(color: RGBColor) {
        ambient.cd = color
        diffuse.cd = color
    }

    fun getCd() = ambient.cd

    override fun shade(shadingRecord: ShadingRecord): RGBColor {
        val world = shadingRecord.world

        val wo = -shadingRecord.ray.direction
        val worldAmbientRadiance = world.ambient.getRadiance(shadingRecord)
        val radiance = ambient.rho(shadingRecord, wo) * worldAmbientRadiance

        for (light in world.lights) {
            val wi = light.getDirection(shadingRecord)
            val ndotwi = shadingRecord.normal * wi

            if (ndotwi > 0.0) {
                val f = diffuse.f(shadingRecord, wo, wi)
                val lightRadiance = light.getRadiance(shadingRecord)
                radiance += f * lightRadiance * ndotwi
            }
        }

        return radiance
    }
}
