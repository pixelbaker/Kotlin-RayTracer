package raytracer.materials

import org.junit.jupiter.api.Test
import raytracer.utilities.ShadingRecord
import raytracer.utilities.black
import raytracer.world.World
import kotlin.test.assertEquals

internal class MaterialTest {
    @Test
    internal fun `default call to shade returns black`() {
        //Given
        val cut = object : Material() {
            override fun clone() = this
        }
        val world = World()
        val shadingRecord = ShadingRecord(world)

        //When
        val result = cut.shade(shadingRecord)

        //Then
        assertEquals(black(), result)
    }
}