package raytracer.cameras

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import raytracer.tracers.Tracer
import raytracer.utilities.Point2D
import raytracer.world.ViewPlane
import raytracer.world.World
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PinholeTest() {
    @Test
    internal fun `default constructor`() {
        val cut = Pinhole()

        with(cut) {
            assertEquals(500.0, viewPlaneDistance)
            assertEquals(1.0, zoom)
        }
    }

    @Test
    internal fun `copy constructor`() {
        val pinholeToCopyFrom = Pinhole(100.0, 2.0)
        val cut = Pinhole(pinholeToCopyFrom)

        with(cut) {
            assertEquals(100.0, viewPlaneDistance)
            assertEquals(2.0, zoom)
        }
    }

    @Test
    internal fun `get the direction`() {
        val cut = Pinhole()

        val direction = cut.getDirection(Point2D(2.0, 2.0))

        assertEquals(.00399, direction.x, .0001)
        assertEquals(.00399, direction.y, .0001)
        assertEquals(-.99998, direction.z, .0001)
    }

    @Test
    internal fun `rendering 2x2 image should display 4 pixels`() {
        //Given
        val world = mockk<World>()
        every { world.drawPixel(any(), any(), any()) } returns Unit
        every { world.viewPlane } returns ViewPlane().apply { vres = 2; hres = 2 }
        every { world.tracer } returns object : Tracer(world) {}

        val cut = Pinhole()

        //When
        cut.renderScene(world)

        //Then
        verify(exactly = 4) { world.drawPixel(any(), any(), any()) }
    }
}
