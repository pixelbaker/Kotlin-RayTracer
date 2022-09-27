package raytracer.utilities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Point3DTest {
    @Test
    internal fun `default constructor`() {
        val point = Point3D()
        assertEquals(0.0, point.x)
        assertEquals(0.0, point.y)
        assertEquals(0.0, point.z)
    }

    @Test
    internal fun `x, y and z is supplied`() {
        val point = Point3D(x = 1.0, y = 2.0, z = 3.0)
        assertEquals(1.0, point.x)
        assertEquals(2.0, point.y)
        assertEquals(3.0, point.z)
    }

    @Test
    internal fun `one value to rule them all`() {
        val point = Point3D(5.0)
        assertEquals(5.0, point.x)
        assertEquals(5.0, point.y)
        assertEquals(5.0, point.z)
    }

    @Test
    internal fun `copy constructor`() {
        val point = Point3D(Point3D(1.0, 2.0, z = 3.0))
        assertEquals(1.0, point.x)
        assertEquals(2.0, point.y)
        assertEquals(3.0, point.z)
    }

    @Test
    internal fun `unary operator overloading`() {
        val point = -Point3D(1.0, 2.0, z = 3.0)
        assertEquals(-1.0, point.x)
        assertEquals(-2.0, point.y)
        assertEquals(-3.0, point.z)
    }
}
