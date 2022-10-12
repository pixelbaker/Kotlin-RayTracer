package raytracer.brdfs

import raytracer.utilities.ShadingRecord
import raytracer.utilities.Vector3D
import raytracer.utilities.black
import raytracer.world.World
import kotlin.test.Test
import kotlin.test.assertEquals

internal class BRDFTest {
    val cut = object : BRDF() {
        override fun clone() = this
    }

    @Test
    internal fun `default implementations f`() {
        val result = cut.f(ShadingRecord(World()), Vector3D(), Vector3D())
        assertEquals(black(), result)
    }

    @Test
    internal fun `default implementations sample_f1`() {
        val result = cut.sample_f(ShadingRecord(World()), Vector3D(), Vector3D())
        assertEquals(black(), result)
    }

    @Test
    internal fun `default implementations sample_f2`() {
        val result = cut.sample_f(ShadingRecord(World()), Vector3D(), Vector3D(), 2.0)
        assertEquals(black(), result)
    }

    @Test
    internal fun `default implementations pho`() {
        val result = cut.rho(ShadingRecord(World()), Vector3D())
        assertEquals(black(), result)
    }
}
