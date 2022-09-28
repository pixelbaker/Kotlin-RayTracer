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

    @Test
    internal fun `vector joining two points`() {
        val vector = Point3D() - Point3D(1.0)
        assertEquals(-1.0, vector.x)
        assertEquals(-1.0, vector.y)
        assertEquals(-1.0, vector.z)
    }

    @Test
    internal fun `addition of a vector`() {
        val point = Point3D() + Vector3D(1.0)
        assertEquals(1.0, point.x)
        assertEquals(1.0, point.y)
        assertEquals(1.0, point.z)
    }

    @Test
    internal fun `subtraction of a vector`() {
        val point = Point3D() - Vector3D(1.0)
        assertEquals(-1.0, point.x)
        assertEquals(-1.0, point.y)
        assertEquals(-1.0, point.z)
    }

    @Test
    internal fun `multiplication by a double`() {
        val point = Point3D(1.0) * 2.0
        assertEquals(2.0, point.x)
        assertEquals(2.0, point.y)
        assertEquals(2.0, point.z)
    }

    @Test
    internal fun `square of distance between two points`() {
        val squareDistance = Point3D(2.0).d_squared(Point3D(1.0))
        assertEquals(3.0, squareDistance)
    }

    @Test
    internal fun `distance bewteen two points`() {
        val squareDistance = Point3D(0.0).distance(Point3D(1.0))
        assertEquals(1.7320, squareDistance, .01)
    }
}
