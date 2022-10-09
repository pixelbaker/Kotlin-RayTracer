package raytracer.lights

import org.junit.jupiter.api.Test
import raytracer.utilities.RGBColor
import raytracer.utilities.ShadingRecord
import raytracer.utilities.Vector3D
import raytracer.world.World
import kotlin.test.assertEquals
import kotlin.test.assertNotSame

internal class DirectionalTest {
    @Test
    internal fun `default constructor`() {
        val cut = Directional()
        assertEquals(1.0, cut.radianceScale)
        assertEquals(RGBColor(1.0), cut.color)
        assertEquals(Vector3D(0.0, 1.0, 0.0), cut.direction)
    }

    @Test
    internal fun `copy constructor`() {
        val directionalToCopyFrom =
            Directional(radianceScale = 2.0, color = RGBColor(2.0), direction = Vector3D(1.0, 2.0, 3.0))
        val cut = Directional(directionalToCopyFrom)
        assertEquals(2.0, cut.radianceScale)
        assertEquals(RGBColor(2.0), cut.color)
        assertEquals(Vector3D(1.0, 2.0, 3.0), cut.direction)
        assertNotSame(directionalToCopyFrom.color, cut.color)
        assertNotSame(directionalToCopyFrom.direction, cut.direction)
    }

    @Test
    internal fun `clone Directional`() {
        val cut = Directional(radianceScale = 2.0, color = RGBColor(2.0), direction = Vector3D(1.0, 2.0, 3.0))
        val result = cut.clone()
        assertNotSame(cut, result)
        assertEquals(2.0, result.radianceScale)
        assertEquals(RGBColor(2.0), result.color)
        assertEquals(Vector3D(1.0, 2.0, 3.0), result.direction)
    }

    @Test
    internal fun `get the direction of the directional light`() {
        val cut = Directional(direction = Vector3D(1.0, 2.0, 3.0))
        val direction = cut.getDirection(ShadingRecord(World()))
        assertEquals(Vector3D(1.0, 2.0, 3.0), direction)
    }

    @Test
    internal fun `radiance is scaled correctly`() {
        val cut = Directional(radianceScale = 2.0, color = RGBColor(1.0))
        val radiance = cut.getRadiance(ShadingRecord(World()))
        assertEquals(RGBColor(2.0), radiance)
    }
}