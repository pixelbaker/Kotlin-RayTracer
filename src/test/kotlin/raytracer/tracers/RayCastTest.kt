package raytracer.tracers

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import raytracer.lights.Ambient
import raytracer.lights.Light
import raytracer.materials.Matte
import raytracer.utilities.Ray
import raytracer.utilities.ShadingRecord
import raytracer.utilities.black
import raytracer.utilities.red
import raytracer.world.World
import kotlin.test.assertEquals

internal class RayCastTest {
    @Test
    internal fun `no object hit, background color returned`() {
        //Given
        val world = mockk<World>()
        val shadingRecord = ShadingRecord(world)
        shadingRecord.hitAnObject = false
        every { world.hitObjects(any()) } returns shadingRecord
        every { world.backgroundColor } returns black()

        val cut = RayCast(world)

        //When
        val result = cut.trace(Ray(), 0)

        //Then
        assertEquals(black(), result)
    }

    @Test
    internal fun `object hit, object color returned`() {
        //Given
        val world = mockk<World>()
        val shadingRecord = ShadingRecord(world)
        shadingRecord.hitAnObject = true
        shadingRecord.material = Matte().apply { setKa(1.0); setCd(red()) }
        every { world.hitObjects(any()) } returns shadingRecord
        every { world.ambient } returns Ambient()
        every { world.lights } returns mutableListOf<Light>()

        val cut = RayCast(world)

        //When
        val result = cut.trace(Ray(), 0)

        //Then
        assertEquals(red(), result)
    }
}
