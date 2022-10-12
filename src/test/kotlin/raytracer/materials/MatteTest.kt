package raytracer.materials

import raytracer.brdfs.invPI
import raytracer.lights.Ambient
import raytracer.lights.Directional
import raytracer.utilities.*
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
    internal fun `shade with one additional directional light, which triggers diffuse calculation`() {
        //Given
        val cut = Matte().apply {
            setCd(white())
            setKa(0.5)
            setKd(1.0)
        }

        val lightDirection = Vector3D(0.0, 1.0, 0.0)
        val world = World()
            .apply {
                ambient = Ambient(0.5, red())
                lights.add(Directional(1.0, green(), lightDirection))
            }

        val shadingRecord = ShadingRecord(world).apply {
            ray = Ray(Point3D(0.0, 0.0, 1.0), Vector3D(0.0, 0.0, 1.0))
            normal = Normal(lightDirection)
        }

        //When
        val result = cut.shade(shadingRecord)

        //Then
        assertEquals(RGBColor(.25, invPI, 0.0), result)
    }

    @Test
    internal fun `shade with one additional directional light, without diffuse calculation`() {
        //Given
        val cut = Matte().apply {
            setCd(white())
            setKa(0.5)
            setKd(1.0)
        }
        
        val world = World()
            .apply {
                ambient = Ambient(0.5, red())
                lights.add(Directional(1.0, green(), Vector3D(0.0, 0.0, -1.0)))
            }

        val shadingRecord = ShadingRecord(world).apply {
            ray = Ray(Point3D(0.0, 0.0, 1.0), Vector3D(0.0, 0.0, 1.0))
            normal = Normal(Vector3D(0.0, 0.0, 1.0))
        }

        //When
        val result = cut.shade(shadingRecord)

        //Then
        assertEquals(RGBColor(.25, 0.0, 0.0), result)
    }
}