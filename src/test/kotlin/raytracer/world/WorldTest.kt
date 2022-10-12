package raytracer.world

import raytracer.geometries.Sphere
import raytracer.materials.Matte
import raytracer.utilities.*
import java.awt.Color
import kotlin.test.Test
import kotlin.test.assertEquals

internal class WorldTest {
    @Test
    internal fun `A scene with no objects, will result in a boring Shading Record`() {
        //Given
        val cut = World()

        //When
        val result = cut.hitObjects(Ray())

        //Then
        assertEquals(ShadingRecord(cut), result)
    }

    @Test
    internal fun `A scene with one object, and a Ray that misses`() {
        //Given
        val cut = World().apply {
            objects.add(Sphere())
        }

        //When
        val result = cut.hitObjects(Ray())

        //Then
        assertEquals(false, result.hitAnObject)
        assertEquals(ShadingRecord(cut), result)
    }

    @Test
    internal fun `A scene with one object, and a Ray hits it`() {
        //Given
        val matte = Matte()
        val cut = World().apply {
            objects.add(Sphere(radius = 2.0).apply { material = matte })
        }

        //When
        val result = cut.hitObjects(Ray(Point3D(0.0, 0.0, 4.0), Vector3D(0.0, 0.0, -1.0)))

        //Then
        val expected = ShadingRecord(
            world = cut,
            hitAnObject = true,
            material = matte,
            hitPoint = Point3D(x = 0.0, y = 0.0, z = 2.0),
            localHitPoint = Point3D(x = 0.0, y = 0.0, z = 2.0),
            normal = Normal(x = 0.0, y = 0.0, z = 1.0),
            t = 2.0
        )
        assertEquals(expected, result)
    }

    @Test
    internal fun `A scene with two objects, and a Ray hits both`() {
        //Given
        val matte = Matte()
        val cut = World().apply {
            objects.add(Sphere(radius = 2.0).apply { material = matte })
            objects.add(Sphere(radius = 1.0).apply { material = matte })
        }

        //When
        val result = cut.hitObjects(Ray(Point3D(0.0, 0.0, 4.0), Vector3D(0.0, 0.0, -1.0)))

        //Then
        val expected = ShadingRecord(
            world = cut,
            hitAnObject = true,
            material = matte,
            hitPoint = Point3D(x = 0.0, y = 0.0, z = 2.0),
            localHitPoint = Point3D(x = 0.0, y = 0.0, z = 2.0),
            normal = Normal(x = 0.0, y = 0.0, z = 1.0),
            t = 2.0
        )
        assertEquals(expected, result)
    }

    private fun assertRed(c: Color) {
        assertEquals(c.red, 255)
        assertEquals(c.green, 0)
        assertEquals(c.blue, 0)
    }

    @Test
    internal fun `drawing a pixel with outOfGammut`() {
        //Given
        val cut = World()
        cut.viewPlane.vres = 1
        cut.viewPlane.vres = 1
        cut.viewPlane.showOutOfGamut = true
        cut.build {}

        //When
        cut.drawPixel(0, 0, RGBColor(2.0))

        //Then
        val rgbInt = cut.image.getRGB(0, 0)
        assertRed(Color(rgbInt, false))
    }

    @Test
    internal fun `gamma adjustment of the colors`() {
        //Given
        val cut = World()
        cut.viewPlane.vres = 1
        cut.viewPlane.vres = 1
        cut.viewPlane.gamma = 2.2
        cut.build {}

        //When
        cut.drawPixel(0, 0, RGBColor(1.0, .5, .25))

        //Then
        val rgbInt = cut.image.getRGB(0, 0)
        val c = Color(rgbInt, false)
        assertEquals(c.red, 255)
        assertEquals(c.green, 186)
        assertEquals(c.blue, 135)
    }
}