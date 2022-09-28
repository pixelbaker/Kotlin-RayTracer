package raytracer.utilities

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class RGBColorTest {
    @Test
    internal fun `default constructor`() {
        val color = RGBColor()
        Assertions.assertEquals(0.0, color.r)
        Assertions.assertEquals(0.0, color.g)
        Assertions.assertEquals(0.0, color.b)
    }

    @Test
    internal fun `r, g and b is supplied`() {
        val color = RGBColor(r = 1.0, g = 2.0, b = 3.0)
        Assertions.assertEquals(1.0, color.r)
        Assertions.assertEquals(2.0, color.g)
        Assertions.assertEquals(3.0, color.b)
    }

    @Test
    internal fun `one value to rule them all`() {
        val color = RGBColor(a = 5.0)
        Assertions.assertEquals(5.0, color.r)
        Assertions.assertEquals(5.0, color.g)
        Assertions.assertEquals(5.0, color.b)
    }

    @Test
    internal fun `copy constructor`() {
        val color = RGBColor(RGBColor(1.0, 2.0, 3.0))
        Assertions.assertEquals(1.0, color.r)
        Assertions.assertEquals(2.0, color.g)
        Assertions.assertEquals(3.0, color.b)
    }

    @Test
    internal fun `addition of two colors`() {
        val color = RGBColor(1.0) + RGBColor(1.0)
        Assertions.assertEquals(2.0, color.r)
        Assertions.assertEquals(2.0, color.g)
        Assertions.assertEquals(2.0, color.b)
    }

    @Test
    internal fun `compound addition`() {
        val result = RGBColor(2.0)
        result += RGBColor(1.0)
        Assertions.assertEquals(3.0, result.r)
        Assertions.assertEquals(3.0, result.g)
        Assertions.assertEquals(3.0, result.b)
    }

    @Test
    internal fun `multiplication by a double on the right`() {
        val color = RGBColor(1.0) * 3.0
        Assertions.assertEquals(3.0, color.r)
        Assertions.assertEquals(3.0, color.g)
        Assertions.assertEquals(3.0, color.b)
    }

    @Test
    internal fun `compound multiplication by a double`() {
        val result = RGBColor(1.0)
        result *= 3.0
        Assertions.assertEquals(3.0, result.r)
        Assertions.assertEquals(3.0, result.g)
        Assertions.assertEquals(3.0, result.b)
    }

    @Test
    internal fun `division by float`() {
        val color = RGBColor(3.0) / 3.0
        Assertions.assertEquals(1.0, color.r)
        Assertions.assertEquals(1.0, color.g)
        Assertions.assertEquals(1.0, color.b)
    }

    @Test
    internal fun `compound division by a double`() {
        val result = RGBColor(3.0)
        result /= 3.0
        Assertions.assertEquals(1.0, result.r)
        Assertions.assertEquals(1.0, result.g)
        Assertions.assertEquals(1.0, result.b)
    }

    @Test
    internal fun `component-wise multiplication of two colors`() {
        val color = RGBColor(2.0) * RGBColor(2.0)
        Assertions.assertEquals(4.0, color.r)
        Assertions.assertEquals(4.0, color.g)
        Assertions.assertEquals(4.0, color.b)
    }

    @Test
    internal fun `are two RGBColors the same`() {
        Assertions.assertTrue(RGBColor(2.0) == RGBColor(2.0))
    }

    @Test
    internal fun `the average of the three components`() {
        Assertions.assertEquals(3.0, RGBColor(3.0).average())
    }

    @Test
    internal fun `raise each component to the specified power`() {
        val color = RGBColor(2.0).powc(2.0)
        Assertions.assertEquals(4.0, color.r)
        Assertions.assertEquals(4.0, color.g)
        Assertions.assertEquals(4.0, color.b)
    }
}