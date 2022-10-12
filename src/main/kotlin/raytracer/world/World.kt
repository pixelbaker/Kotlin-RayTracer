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

private const val kHugeValue = 1.0E10

class World {
    lateinit var tracer: Tracer
    lateinit var camera: Camera
    val viewPlane: ViewPlane = ViewPlane()
    val backgroundColor = RGBColor(black)
    var ambient: Light = Ambient()

    val lights = emptyList<Light>().toMutableList()
    val objects = emptyList<GeometricObject>().toMutableList()

    lateinit var image: BufferedImage

    fun build(buildScript: BuildScript) {
        buildScript(this)
        image = BufferedImage(viewPlane.hres, viewPlane.vres, TYPE_INT_RGB)
    }

    fun drawPixel(row: Int, column: Int, raw: RGBColor) {
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
        var tmin = kHugeValue

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