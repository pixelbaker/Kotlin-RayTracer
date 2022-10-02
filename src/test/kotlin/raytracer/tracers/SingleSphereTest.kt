package raytracer.tracers

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import raytracer.geometries.Sphere
import raytracer.utilities.Ray
import raytracer.utilities.black
import raytracer.utilities.red
import raytracer.world.World
import kotlin.test.assertEquals

internal class SingleSphereTest {
    @Test
    internal fun `return red on hit`() {
        val world = World()
        world.sphere = mockk<Sphere>()
        every { world.sphere.hit(any(), any(), any()) } returns true

        val cut = SingleSphere(world)

        val color = cut.trace(Ray())

        assertEquals(red, color)
    }

    @Test
    internal fun `return black on miss`() {
        val world = World()
        world.sphere = mockk<Sphere>()
        every { world.sphere.hit(any(), any(), any()) } returns false

        val cut = SingleSphere(world)

        val color = cut.trace(Ray())

        assertEquals(black, color)
    }
}