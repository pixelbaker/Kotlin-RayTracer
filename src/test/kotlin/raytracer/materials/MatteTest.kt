package raytracer.materials

import raytracer.lights.Ambient
import raytracer.utilities.RGBColor
import raytracer.utilities.ShadingRecord
import raytracer.utilities.red
import raytracer.world.World
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame

internal class MatteTest {
    @Test
    internal fun `clone Matte`() {
        val cut = Matte()
        val result = cut.clone()
        assertNotSame(cut, result)
    }

    @Test
    internal fun `get and set ka`() {
        val cut = Matte()
        assertEquals(0.0, cut.getKa())
        cut.setKa(1.0)
        assertEquals(1.0, cut.getKa())
    }

    @Test
    internal fun `get and set kd`() {
        val cut = Matte()
        assertEquals(0.0, cut.getKd())
        cut.setKd(1.0)
        assertEquals(1.0, cut.getKd())
    }

    @Test
    internal fun `get and set cd`() {
        val cut = Matte()
        assertEquals(RGBColor(0.0), cut.getCd())
        cut.setCd(RGBColor(1.0))
        assertEquals(RGBColor(1.0), cut.getCd())
    }

    @Test
    internal fun `shade only with ambient light`() {
        //Given
        val cut = Matte().apply {
            setCd(red())
            setKa(1.0)
        }

        //When
        val result = cut.shade(ShadingRecord(World()))

        //Then
        assertEquals(red(), result)
    }

    @Test
    internal fun `shade with one additional ambient light`() {
        //Given
        val cut = Matte().apply {
            setCd(red())
            setKa(1.0)
        }

        val world = World().apply { lights.add(Ambient(color = RGBColor(0.0, 1.0, 0.0))) }

        //When
        val result = cut.shade(ShadingRecord(world))

        //Then
        assertEquals(red(), result)
    }
}