package raytracer.lights

import raytracer.utilities.RGBColor
import raytracer.utilities.ShadingRecord
import raytracer.utilities.Vector3D

class Directional(
    var radianceScale: Double = 1.0,
    var color: RGBColor = RGBColor(1.0),
    var direction: Vector3D = Vector3D(0.0, 1.0, 0.0),
) : Light() {

    constructor(directional: Directional) : this(
        radianceScale = directional.radianceScale,
        color = RGBColor(directional.color),
        direction = Vector3D(directional.direction)
    )

    override fun clone() = Directional(this)

    override fun getDirection(shadingRecord: ShadingRecord) = Vector3D(direction)

    override fun getRadiance(shadingRecord: ShadingRecord) = color * radianceScale
}