package raytracer.utilities

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class Point2DTest {

    @Test
    internal fun `default constructor`() {
        val point = Point2D()
        assertEquals(0.0, point.x)
        assertEquals(0.0, point.y)
    }

    @Test
    internal fun `x and y is supplied`() {
        val point = Point2D(x = 1.0, y = 2.0)
        assertEquals(1.0, point.x)
        assertEquals(2.0, point.y)
    }

    @Test
    internal fun `one value to rule them all`() {
        val point = Point2D(5.0)
        assertEquals(5.0, point.x)
        assertEquals(5.0, point.y)
    }

    @Test
    internal fun `copy constructor`() {
        val point = Point2D(Point2D(1.0, 2.0))
        assertEquals(1.0, point.x)
        assertEquals(2.0, point.y)
    }

    @Test
    internal fun `multiplication by a float on the right`() {
        val point = Point2D(1.0, 2.0)
        val result = point * 2.0
        assertEquals(2.0, result.x)
        assertEquals(4.0, result.y)
        assertNotEquals(point, result)
    }
}