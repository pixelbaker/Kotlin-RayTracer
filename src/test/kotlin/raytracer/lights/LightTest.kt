package raytracer.lights

import raytracer.utilities.ShadingRecord
import raytracer.utilities.Vector3D
import raytracer.utilities.black
import raytracer.world.World
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LightTest {
    @Test
    internal fun `default radiance implementation returns black`() {
        val cut = object : Light() {
            override fun getDirection(shadingRecord: ShadingRecord) = Vector3D()
            override fun clone() = this
        }
        val color = cut.getRadiance(ShadingRecord(World()))
        assertEquals(black(), color)
    }
}
