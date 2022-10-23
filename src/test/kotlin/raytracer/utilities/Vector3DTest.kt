package raytracer.utilities

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Vector3DTest {
    @Test
    internal fun `default constructor`() {
        val vector = Vector3D()
        assertEquals(0.0, vector.x)
        assertEquals(0.0, vector.y)
        assertEquals(0.0, vector.z)
    }

    @Test
    internal fun `x, y and z is supplied`() {
        val vector = Vector3D(x = 1.0, y = 2.0, z = 3.0)
        assertEquals(1.0, vector.x)
        assertEquals(2.0, vector.y)
        assertEquals(3.0, vector.z)
    }

    @Test
    internal fun `one value to rule them all`() {
        val vector = Vector3D(5.0)
        assertEquals(5.0, vector.x)
        assertEquals(5.0, vector.y)
        assertEquals(5.0, vector.z)
    }

    @Test
    internal fun `copy constructor`() {
        val vector = Vector3D(Vector3D(1.0, 2.0, 3.0))
        assertEquals(1.0, vector.x)
        assertEquals(2.0, vector.y)
        assertEquals(3.0, vector.z)
    }

    @Test
    internal fun `construction by point`() {
        val vector = Vector3D(Point3D(1.0, 2.0, z = 3.0))
        assertEquals(1.0, vector.x)
        assertEquals(2.0, vector.y)
        assertEquals(3.0, vector.z)
    }

    @Test
    internal fun `unary operator overloading`() {
        val vector = -Vector3D(1.0, 2.0, z = 3.0)
        assertEquals(-1.0, vector.x)
        assertEquals(-2.0, vector.y)
        assertEquals(-3.0, vector.z)
    }

    @Test
    internal fun `vector length`() {
        val vector = Vector3D(1.0)
        assertEquals(1.7320, vector.length(), .1)
    }

    @Test
    internal fun `length squared`() {
        val vector = Vector3D(1.0)
        assertEquals(3.0, vector.lengthSquared())
    }

    @Test
    internal fun `multiplication by a double on the right`() {
        val vector = Vector3D(1.0) * 3.0
        assertEquals(3.0, vector.x)
        assertEquals(3.0, vector.y)
        assertEquals(3.0, vector.z)
    }

    @Test
    internal fun `division by a double`() {
        val vector = Vector3D(3.0) / 3.0
        assertEquals(1.0, vector.x)
        assertEquals(1.0, vector.y)
        assertEquals(1.0, vector.z)
    }

    @Test
    internal fun `addition of two vectors`() {
        val vector = Vector3D(1.0) + Vector3D(1.0)
        assertEquals(2.0, vector.x)
        assertEquals(2.0, vector.y)
        assertEquals(2.0, vector.z)
    }

    @Test
    internal fun `subtraction of two vectors`() {
        val vector = Vector3D(3.0) - Vector3D(2.0)
        assertEquals(1.0, vector.x)
        assertEquals(1.0, vector.y)
        assertEquals(1.0, vector.z)
    }

    @Test
    internal fun `dot product`() {
        val dotProduct: Double = Vector3D(1.0) * Vector3D(1.0)
        assertEquals(3.0, dotProduct)
    }

    @Test
    internal fun `cross product`() {
        val vector = Vector3D(1.0) % Vector3D(1.0)
        assertEquals(0.0, vector.x)
        assertEquals(0.0, vector.y)
        assertEquals(0.0, vector.z)
    }

    @Test
    internal fun `normalize vector`() {
        val vector = Vector3D(1.0)
        vector.normalize()
        assertEquals(0.5773, vector.x, .0001)
        assertEquals(0.5773, vector.y, .0001)
        assertEquals(0.5773, vector.z, .0001)
    }

    @Test
    internal fun `return a unit vector, and normalize the vector`() {
        val vector = Vector3D(1.0).hat()
        assertEquals(0.5773, vector.x, .0001)
        assertEquals(0.5773, vector.y, .0001)
        assertEquals(0.5773, vector.z, .0001)
    }
}