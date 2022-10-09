package raytracer.world

import raytracer.cameras.Camera
import raytracer.geometries.GeometricObject
import raytracer.geometries.Sphere
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
    val viewPlane: ViewPlane = ViewPlane()
    val backgroundColor = RGBColor(black)
    var tracer: Tracer? = null
    var camera: Camera? = null
    var ambient: Light = Ambient()
    val lights = emptyList<Light>().toMutableList()
    val objects = emptyList<GeometricObject>().toMutableList()

    var sphere = Sphere()

    var image = BufferedImage(viewPlane.hres, viewPlane.vres, TYPE_INT_RGB)

    fun build(buildScript: BuildScript) {
        buildScript(this)
        image = BufferedImage(viewPlane.hres, viewPlane.vres, TYPE_INT_RGB)
    }

    fun renderScene() {
        val depth = 100.0
        val vres = viewPlane.vres
        val hres = viewPlane.hres
        val pixelSize = viewPlane.pixelSize
        val ray = Ray(direction = Vector3D(.0, .0, -1.0))

        for (row in 0 until vres) {
            for (column in 0 until hres) {
                // This uses orthographic viewing along the zw axis
                val x = pixelSize * (column - hres / 2.0 + 0.5)
                val y = pixelSize * (row - vres / 2.0 + 0.5)
                ray.origin = Point3D(x, y, depth)

                val pixelColor = tracer?.trace(ray)!!

                drawPixel(row, column, pixelColor)
            }
        }
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

    fun hitBareBonesObjects(ray: Ray): ShadingRecord {
        val t = RayParam()
        var tmin = kHugeValue
        val shadingRecord = ShadingRecord(this)

        for (obj in objects) {
            val hit = obj.hit(ray, t, shadingRecord)
            if (hit && t.t < tmin) {
                shadingRecord.hitAnObject = true
                tmin = t.t
                shadingRecord.color = RGBColor(obj.color)
            }
        }

        return shadingRecord
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