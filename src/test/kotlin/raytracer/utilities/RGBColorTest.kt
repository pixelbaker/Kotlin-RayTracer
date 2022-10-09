package raytracer.utilities

import kotlin.test.*

internal class RGBColorTest {
    @Test
    internal fun `default constructor`() {
        val color = RGBColor()
        assertEquals(0.0, color.r)
        assertEquals(0.0, color.g)
        assertEquals(0.0, color.b)
    }

    @Test
    internal fun `r, g and b is supplied`() {
        val color = RGBColor(r = 1.0, g = 2.0, b = 3.0)
        assertEquals(1.0, color.r)
        assertEquals(2.0, color.g)
        assertEquals(3.0, color.b)
    }

    @Test
    internal fun `one value to rule them all`() {
        val color = RGBColor(a = 5.0)
        assertEquals(5.0, color.r)
        assertEquals(5.0, color.g)
        assertEquals(5.0, color.b)
    }

    @Test
    internal fun `copy constructor`() {
        val color = RGBColor(RGBColor(1.0, 2.0, 3.0))
        assertEquals(1.0, color.r)
        assertEquals(2.0, color.g)
        assertEquals(3.0, color.b)
    }

    @Test
    internal fun `compound addition`() {
        val result = RGBColor(2.0)
        result += RGBColor(1.0)
        assertEquals(3.0, result.r)
        assertEquals(3.0, result.g)
        assertEquals(3.0, result.b)
    }

    @Test
    internal fun `compound multiplication by a double`() {
        val result = RGBColor(1.0)
        result *= 3.0
        assertEquals(3.0, result.r)
        assertEquals(3.0, result.g)
        assertEquals(3.0, result.b)
    }

    @Test
    internal fun `multiplication by a double`() {
        val color = RGBColor(1.0)
        val result = color * 3.0
        assertEquals(3.0, result.r)
        assertEquals(3.0, result.g)
        assertEquals(3.0, result.b)
    }

    @Test
    internal fun `division by float`() {
        val color = RGBColor(3.0) / 3.0
        assertEquals(1.0, color.r)
        assertEquals(1.0, color.g)
        assertEquals(1.0, color.b)
    }

    @Test
    internal fun `compound division by a double`() {
        val result = RGBColor(3.0)
        result /= 3.0
        assertEquals(1.0, result.r)
        assertEquals(1.0, result.g)
        assertEquals(1.0, result.b)
    }

    @Test
    internal fun `compound division by an Integer`() {
        val result = RGBColor(3.0)
        result /= 3
        assertEquals(1.0, result.r)
        assertEquals(1.0, result.g)
        assertEquals(1.0, result.b)
    }

    @Test
    internal fun `component-wise multiplication of two colors`() {
        val color = RGBColor(2.0) * RGBColor(2.0)
        assertEquals(4.0, color.r)
        assertEquals(4.0, color.g)
        assertEquals(4.0, color.b)
    }

    @Test
    internal fun `are two RGBColors the same`() {
        assertTrue(RGBColor(2.0) == RGBColor(2.0))
    }

    @Test
    internal fun `the average of the three components`() {
        assertEquals(3.0, RGBColor(3.0).average())
    }

    @Test
    internal fun `raise each component to the specified power`() {
        val color = RGBColor(2.0).powc(2.0)
        assertEquals(4.0, color.r)
        assertEquals(4.0, color.g)
        assertEquals(4.0, color.b)
    }

    @Test
    internal fun `no value is above 1 so no maxing to 1 needed`() {
        val cut = RGBColor(1.0)
        val result = cut.maxToOne()
        assertSame(cut, result)
        assertEquals(RGBColor(1.0), result)
    }

    @Test
    internal fun `one value is above 1 all values will be evenily scaled down until 1 is reached`() {
        val cut = RGBColor(2.0, 1.0, 0.5)
        val result = cut.maxToOne()
        assertNotSame(cut, result)
        assertEquals(RGBColor(1.0, .5, .25), result)
    }

    @Test
    internal fun `no value is above 1 so no clamping to red needed`() {
        val cut = RGBColor(1.0)
        val result = cut.clampToColor()
        assertSame(cut, result)
        assertEquals(RGBColor(1.0), result)
    }

    @Test
    internal fun `one value is above 1, red will be returned`() {
        val cut = RGBColor(2.0, 1.0, 0.5)
        val result = cut.clampToColor()
        assertNotSame(cut, result)
        assertEquals(red, result)
    }
}