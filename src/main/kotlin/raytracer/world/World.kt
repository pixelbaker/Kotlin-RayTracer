package raytracer.world

import raytracer.cameras.Camera
import raytracer.geometries.GeometricObject
import raytracer.lights.Ambient
import raytracer.lights.Light
import raytracer.tracers.Tracer
import raytracer.utilities.*
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB


typealias BuildScript = (World) -> Unit

class World {
    lateinit var image: BufferedImage
    lateinit var tracer: Tracer
    lateinit var camera: Camera
    val viewPlane: ViewPlane = ViewPlane()
    var backgroundColor = black()
    var ambient: Light = Ambient()

    val lights = emptyList<Light>().toMutableList()
    val objects = emptyList<GeometricObject>().toMutableList()

    fun build(buildScript: BuildScript) {
        buildScript(this)
        image = BufferedImage(viewPlane.hres, viewPlane.vres, TYPE_INT_RGB)
    }

    /**
     * @param raw is the pixel color computed by the ray tracer its RGB floating point components can be arbitrarily large
     */
    fun drawPixel(row: Int, column: Int, raw: RGBColor) {
        // mapped_color has all components in the range [0, 1], but still floating point
        var mappedColor = if (viewPlane.showOutOfGamut)
            raw.clampToColor()
        else
            raw.maxToOne()

        mappedColor = if (viewPlane.gamma != 1.0)
            mappedColor.powc(viewPlane.invGamma)
        else
            mappedColor

        // have to start from max y coordinate to convert to screen coordinates
        val x = column
        val y = viewPlane.vres - row - 1


        // display color has integer components for computer display in the range [0, 255]
        val r = (mappedColor.r * 255).toInt()
        val g = (mappedColor.g * 255).toInt()
        val b = (mappedColor.b * 255).toInt()

        image.setRGB(x, y, Color(r, g, b).rgb)
    }

    fun hitObjects(ray: Ray): ShadingRecord {
        val shadingRecord = ShadingRecord(this)
        val t = RayParam()
        var normal = Normal()
        var localHitPoint = Point3D()
        var tmin = Double.MAX_VALUE

        for (obj in objects) {
            if (obj.hit(ray, t, shadingRecord) && (t.t < tmin)) {
                tmin = t.t
                normal = shadingRecord.normal
                localHitPoint = shadingRecord.localHitPoint
                shadingRecord.hitAnObject = true
                shadingRecord.material = obj.material
                shadingRecord.hitPoint = ray.origin + (ray.direction * t.t)
            }
        }

        if (shadingRecord.hitAnObject) {
            shadingRecord.t = tmin
            shadingRecord.normal = normal
            shadingRecord.localHitPoint = localHitPoint
        }

        return shadingRecord
    }
}