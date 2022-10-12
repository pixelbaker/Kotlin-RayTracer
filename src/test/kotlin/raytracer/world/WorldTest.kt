package raytracer.world

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import raytracer.tracers.Tracer
import raytracer.utilities.RGBColor
import raytracer.utilities.black
import java.awt.Color
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

internal class WorldTest {
    @Test
    @Ignore
    internal fun `2x2 pixel scene must set color of 4 pixels`() {
        //Given
        val tracer = mockk<Tracer>()
        every { tracer.trace(any()) }.returns(RGBColor(black))

        val cut = World()
        cut.viewPlane.vres = 2
        cut.viewPlane.hres = 2
        cut.tracer = tracer
        cut.build {}

        //When
        //cut.renderScene()

        //Then
        verify(exactly = 4) { tracer.trace(any()) }
        for (row in 0..1) {
            for (column in 0..1) {
                val rgbInt = cut.image.getRGB(row, column)
                assertBlack(Color(rgbInt, false))
            }
        }
    }

    private fun assertBlack(c: Color) {
        assertEquals(c.red, 0)
        assertEquals(c.green, 0)
        assertEquals(c.blue, 0)
    }

    private fun assertRed(c: Color) {
        assertEquals(c.red, 255)
        assertEquals(c.green, 0)
        assertEquals(c.blue, 0)
    }

    @Test
    internal fun `drawing a pixel with outOfGammut`() {
        //Given
        val cut = World()
        cut.viewPlane.vres = 1
        cut.viewPlane.vres = 1
        cut.viewPlane.showOutOfGamut = true
        cut.build {}

        //When
        cut.drawPixel(0, 0, RGBColor(2.0))

        //Then
        val rgbInt = cut.image.getRGB(0, 0)
        assertRed(Color(rgbInt, false))
    }

    @Test
    internal fun `gamma adjustment of the colors`() {
        //Given
        val cut = World()
        cut.viewPlane.vres = 1
        cut.viewPlane.vres = 1
        cut.viewPlane.gamma = 2.2
        cut.build {}

        //When
        cut.drawPixel(0, 0, RGBColor(1.0, .5, .25))

        //Then
        val rgbInt = cut.image.getRGB(0, 0)
        val c = Color(rgbInt, false)
        assertEquals(c.red, 255)
        assertEquals(c.green, 186)
        assertEquals(c.blue, 135)
    }
}