package raytracer.utilities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

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
    internal fun `assignment tested`() {
        val otherPoint = Point2D(1.0, 2.0)
        val point = Point2D(0.0)
        val result = point.assignment(otherPoint)
        assertEquals(1.0, point.x)
        assertEquals(2.0, point.y)
        assertNotEquals(otherPoint, point)
        assertEquals(point, result)
    }

    @Test
    internal fun `assignment to itself`() {
        val point = Point2D(1.0, 2.0)
        val result = point.assignment(point)
        assertEquals(1.0, point.x)
        assertEquals(2.0, point.y)
        assertEquals(point, result)
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