package raytracer.lights

import raytracer.utilities.RGBColor
import raytracer.utilities.ShadingRecord
import raytracer.utilities.Vector3D

class Ambient(
    val radianceScale: Double = 1.0,
    val color: RGBColor = RGBColor(1.0),
) : Light() {
    constructor(ambient: Ambient) : this(
        radianceScale = ambient.radianceScale,
        color = RGBColor(ambient.color)
    )

    override fun getDirection(shadingRecord: ShadingRecord) = Vector3D(0.0)

    override fun clone() = Ambient(this)

    override fun getRadiance(shadingRecord: ShadingRecord) = color * radianceScale
}