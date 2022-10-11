package raytracer.brdfs

import raytracer.utilities.RGBColor
import raytracer.utilities.ShadingRecord
import raytracer.utilities.Vector3D
import raytracer.world.World
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame

internal class LambertianTest {
    @Test
    internal fun `default constructor`() {
        val cut = Lambertian()
        assertEquals(0.0, cut.kd)
        assertEquals(RGBColor(0.0), cut.cd)
    }

    @Test
    internal fun `copy constructor`() {
        val lambertianToCopyFrom = Lambertian(kd = 2.0, RGBColor(1.0))
        val cut = Lambertian(lambertianToCopyFrom)
        assertEquals(2.0, cut.kd)
        assertEquals(RGBColor(1.0), cut.cd)
    }

    @Test
    internal fun `clone Lambertian`() {
        val cut = Lambertian(kd = 2.0, RGBColor(1.0))
        val result = cut.clone()
        assertNotSame(cut, result)
        assertEquals(2.0, result.kd)
        assertNotSame(cut.cd, result.cd)
        assertEquals(RGBColor(1.0), result.cd)
    }

    @Test
    internal fun `f returns expected Color`() {
        val shadingRecord = ShadingRecord(World())

        var cut = Lambertian()
        var result = cut.f(shadingRecord, Vector3D(), Vector3D())
        assertEquals(RGBColor(0.0), result)

        cut = Lambertian(1.0, RGBColor(1.0))
        result = cut.f(shadingRecord, Vector3D(), Vector3D())
        assertEquals(RGBColor(invPI), result)

        cut = Lambertian(2.0, RGBColor(1.0))
        result = cut.f(shadingRecord, Vector3D(), Vector3D())
        assertEquals(RGBColor(2 * invPI), result)
    }

    @Test
    internal fun `rho returns expected Color`() {
        val shadingRecord = ShadingRecord(World())

        var cut = Lambertian()
        var result = cut.rho(shadingRecord, Vector3D())
        assertEquals(RGBColor(0.0), result)

        cut = Lambertian(1.0, RGBColor(1.0))
        result = cut.rho(shadingRecord, Vector3D())
        assertEquals(RGBColor(1.0), result)

        cut = Lambertian(2.0, RGBColor(1.0))
        result = cut.rho(shadingRecord, Vector3D())
        assertEquals(RGBColor(2.0), result)
    }
}