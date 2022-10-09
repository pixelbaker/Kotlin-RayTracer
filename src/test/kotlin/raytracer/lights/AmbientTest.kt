package raytracer.lights

import raytracer.utilities.RGBColor
import raytracer.utilities.ShadingRecord
import raytracer.utilities.Vector3D
import raytracer.world.World
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame

internal class AmbientTest {
    @Test
    internal fun `default constructor`() {
        val cut = Ambient()
        assertEquals(RGBColor(1.0), cut.color)
        assertEquals(1.0, cut.radianceScale)
    }

    @Test
    internal fun `copy constructor`() {
        val ambientToCopyFrom = Ambient(2.0, RGBColor(0.5))
        val cut = Ambient(ambientToCopyFrom)
        assertEquals(RGBColor(0.5), cut.color)
        assertEquals(2.0, cut.radianceScale)
        assertNotSame(ambientToCopyFrom.color, cut.color)
    }

    @Test
    internal fun `clone Ambient`() {
        val cut = Ambient(2.0, RGBColor(0.5))
        val result = cut.clone()
        assertNotSame(cut, result)
        assertEquals(RGBColor(0.5), result.color)
        assertEquals(2.0, result.radianceScale)
    }

    @Test
    internal fun `getDirection returns zero-vector`() {
        val cut = Ambient()
        val direction = cut.getDirection(ShadingRecord(World()))
        assertEquals(Vector3D(0.0), direction)
    }

    @Test
    internal fun `double radiance`() {
        val cut = Ambient(radianceScale = 2.0)
        val radiance = cut.getRadiance(ShadingRecord(World()))
        assertEquals(RGBColor(2.0), radiance)
    }
}