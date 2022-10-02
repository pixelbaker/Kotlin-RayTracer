package raytracer.utilities

import raytracer.materials.Material
import raytracer.world.World
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame

internal class ShadingRecordTest {
    @Test
    internal fun `default constructor`() {
        val world = World()
        val cut = ShadingRecord(world)
        assertEquals(world, cut.world)
    }

    @Test
    internal fun `copy constructor`() {
        //Given
        val testMaterial = object : Material() {
            override fun clone() = this
        }
        val testHitPoint = Point3D(1.0)
        val testLocalHitPoint = Point3D(2.0)
        val testNormal = Normal(3.0)
        val testRay = Ray(Point3D(4.0), Vector3D(5.0))
        val world = World()

        val shadingRecordToCopyFrom = ShadingRecord(world)
        with(shadingRecordToCopyFrom) {
            hitAnObject = true
            material = testMaterial
            hitPoint = testHitPoint
            localHitPoint = testLocalHitPoint
            normal = testNormal
            ray = testRay
            depth = 1
            t = 2.0
        }

        //When
        val cut = ShadingRecord(shadingRecordToCopyFrom)

        //Then
        assertEquals(world, cut.world)
        assertEquals(testMaterial, cut.material)

        assertEquals(testHitPoint, cut.hitPoint)
        assertNotSame(testHitPoint, cut.hitPoint)

        assertEquals(testLocalHitPoint, cut.localHitPoint)
        assertNotSame(testLocalHitPoint, cut.localHitPoint)

        assertEquals(testNormal, cut.normal)
        assertNotSame(testNormal, cut.normal)

        assertEquals(testRay, cut.ray)
        assertNotSame(testRay, cut.ray)

        assertEquals(true, cut.hitAnObject)
        assertEquals(1, cut.depth)
        assertEquals(2.0, cut.t)
    }
}