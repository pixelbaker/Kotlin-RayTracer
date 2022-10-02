package raytracer.geometries

import org.junit.jupiter.api.Test
import raytracer.materials.Material
import raytracer.utilities.*
import raytracer.world.World
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotSame
import kotlin.test.assertTrue

internal class SphereTest {
    class TestMaterial : Material() {
        override fun clone() = TestMaterial()
    }

    @Test
    internal fun `default constructor`() {
        val cut = Sphere()
        assertEquals(1.0, cut.radius)
        assertEquals(Point3D(), cut.center)
    }

    @Test
    internal fun `copy constructor`() {
        val testMaterial = TestMaterial()
        val sphereToCopyFrom = Sphere(Point3D(1.0), 2.0)
        sphereToCopyFrom.material = testMaterial

        //When
        val cut = Sphere(sphereToCopyFrom)

        assertNotSame(testMaterial, cut.material)
        assertEquals(Point3D(1.0), cut.center)
        assertEquals(2.0, cut.radius)
    }

    @Test
    internal fun `validate clone`() {
        val cut: GeometricObject = Sphere()
        cut.material = TestMaterial()
        val temp = cut.clone()
        val sphere: Sphere = temp as Sphere

        assertNotSame(sphere, cut)
        assertNotSame(sphere.material, cut.material)
    }

    @Test
    internal fun `ray misses sphere when being above`() {
        val shadingRecord = ShadingRecord(World())
        val tmin = RayParam()
        val cut = Sphere()
        val ray = Ray(
            origin = Point3D(2.0),
            direction = Vector3D(1.0, .0, .0)
        )

        val hitAnObject = cut.hit(ray, tmin, shadingRecord)

        assertFalse(hitAnObject)
        assertEquals(0.0, tmin.t)
    }

    @Test
    internal fun `ray hits sphere twice (front and back)`() {
        val shadingRecord = ShadingRecord(World())
        val tmin = RayParam()
        val cut = Sphere()
        val ray = Ray(
            origin = Point3D(-2.0, .0, .0),
            direction = Vector3D(1.0, .0, .0)
        )

        val hitAnObject = cut.hit(ray, tmin, shadingRecord)

        assertTrue(hitAnObject)
        assertEquals(1.0, tmin.t)
    }

    @Test
    internal fun `ray hits sphere once (just the shell)`() {
        val shadingRecord = ShadingRecord(World())
        val tmin = RayParam()
        val cut = Sphere()
        val ray = Ray(
            origin = Point3D(-1.0, 1.0, .0),
            direction = Vector3D(1.0, .0, .0)
        )

        val hitAnObject = cut.hit(ray, tmin, shadingRecord)

        assertTrue(hitAnObject)
        assertEquals(1.0, tmin.t)
    }

    @Test
    internal fun `ray misses the sphere when being behind`() {
        val shadingRecord = ShadingRecord(World())
        val tmin = RayParam()
        val cut = Sphere()
        val ray = Ray(
            origin = Point3D(2.0, .0, .0),
            direction = Vector3D(1.0, .0, .0)
        )

        val hitAnObject = cut.hit(ray, tmin, shadingRecord)

        assertFalse(hitAnObject)
        assertEquals(.0, tmin.t)
    }

    @Test
    internal fun `ray is being cast on the spheres shell traveling outward (no hit)`() {
        val shadingRecord = ShadingRecord(World())
        val tmin = RayParam()
        val cut = Sphere()
        val ray = Ray(
            origin = Point3D(1.0, .0, .0),
            direction = Vector3D(1.0, .0, .0)
        )

        val hitAnObject = cut.hit(ray, tmin, shadingRecord)

        assertFalse(hitAnObject)
        assertEquals(.0, tmin.t)
    }

    @Test
    internal fun `ray hit sphere from inside`() {
        val shadingRecord = ShadingRecord(World())
        val tmin = RayParam()
        val cut = Sphere()
        val ray = Ray(
            origin = Point3D(0.0, .0, .0),
            direction = Vector3D(1.0, .0, .0)
        )

        val hitAnObject = cut.hit(ray, tmin, shadingRecord)

        assertTrue(hitAnObject)
        assertEquals(1.0, tmin.t)
    }

    @Test
    internal fun `ray hit sphere twice when originating on the sphere shell facing inward`() {
        val shadingRecord = ShadingRecord(World())
        val tmin = RayParam()
        val cut = Sphere()
        val ray = Ray(
            origin = Point3D(-1.0, .0, .0),
            direction = Vector3D(1.0, .0, .0)
        )

        val hitAnObject = cut.hit(ray, tmin, shadingRecord)

        assertTrue(hitAnObject)
        assertEquals(2.0, tmin.t)
    }
}
