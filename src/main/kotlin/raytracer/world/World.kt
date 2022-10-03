package raytracer.world

import raytracer.cameras.Camera
import raytracer.geometries.GeometricObject
import raytracer.geometries.Sphere
import raytracer.tracers.Tracer
import raytracer.utilities.*
import kotlin.math.max

private const val kHugeValue = 1.0E10

class World {
    val viewPlane: ViewPlane = ViewPlane()
    val backgroundColor = RGBColor(black)
    var tracer: Tracer? = null

    //var ambient: Light? = null
    var camera: Camera? = null
    var sphere = Sphere(radius = 85.0)
    private val objects = emptyList<GeometricObject>().toMutableList()

    //val lights: List<Light> = emptyList()
    private var pixels: MutableMap<Int, String> = mutableMapOf()

    fun add(obj: GeometricObject) {
        objects.add(obj)
    }

    fun build() {}

    fun renderScene() {
        val depth = 100.0
        val vres = viewPlane.vres
        val hres = viewPlane.hres
        val pixelSize = viewPlane.pixelSize
        val ray = Ray(direction = Vector3D(.0, .0, -1.0))

        for (row in 0 until vres) {
            for (column in 0..hres) {
                // This uses orthographic viewing along the zw axis
                val x = pixelSize * (column - hres / 2.0 + 0.5)
                val y = pixelSize * (row - vres / 2.0 + 0.5)
                ray.origin = Point3D(x, y, depth)

                val pixelColor = tracer?.trace(ray)!!

                displayPixel(row, column, pixelColor)
            }
        }
        for (row in pixels) {
            println(row)
        }
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

    fun displayPixel(row: Int, column: Int, raw: RGBColor) {
        var mappedColor = if (viewPlane.showOutOfGamut)
            clampToColor(raw)
        else
            maxToOne(raw)

        mappedColor = if (viewPlane.gamma != 1.0)
            mappedColor.powc((viewPlane.invGamma))
        else
            mappedColor

        // have to start from max y coordinate to convert to screen coordinates
        val x = column
        val y = viewPlane.vres - row - 1

        val r = (mappedColor.r * 255).toInt()
        val g = (mappedColor.g * 255).toInt()
        val b = (mappedColor.b * 255).toInt()

        pixels[y] = pixels.withDefault { "" }[y] + if (r > 0) "o" else " "
    }

    fun maxToOne(color: RGBColor): RGBColor {
        val maxValue = max(color.r, max(color.g, color.b))
        return if (maxValue > 1.0) (color / maxValue) else color
    }

    fun clampToColor(raw: RGBColor): RGBColor {
        return if (raw.r > 1.0 || raw.g > 1.0 || raw.b > 1.0) RGBColor(red) else raw
    }
}