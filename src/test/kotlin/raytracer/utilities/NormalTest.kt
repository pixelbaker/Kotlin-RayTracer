package raytracer.utilities

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class NormalTest {
    @Test
    internal fun `default constructor`() {
        val normal = Normal()
        assertEquals(0.0, normal.x)
        assertEquals(0.0, normal.y)
        assertEquals(0.0, normal.z)
    }

    @Test
    internal fun `x, y and z is supplied`() {
        val normal = Normal(x = 1.0, y = 2.0, z = 3.0)
        assertEquals(1.0, normal.x)
        assertEquals(2.0, normal.y)
        assertEquals(3.0, normal.z)
    }

    @Test
    internal fun `one value to rule them all`() {
        val normal = Normal(5.0)
        assertEquals(5.0, normal.x)
        assertEquals(5.0, normal.y)
        assertEquals(5.0, normal.z)
    }

    @Test
    internal fun `copy constructor`() {
        val normal = Normal(Normal(1.0, 2.0, 3.0))
        assertEquals(1.0, normal.x)
        assertEquals(2.0, normal.y)
        assertEquals(3.0, normal.z)
    }

    @Test
    internal fun `construction by vector`() {
        val normal = Normal(Vector3D(1.0, 2.0, z = 3.0))
        assertEquals(1.0, normal.x)
        assertEquals(2.0, normal.y)
        assertEquals(3.0, normal.z)
    }

    @Test
    internal fun `unary operator overloading`() {
        val normal = -Normal(1.0, 2.0, z = 3.0)
        assertEquals(-1.0, normal.x)
        assertEquals(-2.0, normal.y)
        assertEquals(-3.0, normal.z)
    }

    @Test
    internal fun `multiplication by a double on the right`() {
        val normal = Normal(1.0) * 3.0
        assertEquals(3.0, normal.x)
        assertEquals(3.0, normal.y)
        assertEquals(3.0, normal.z)
    }

    @Test
    internal fun `division by a double on the right`() {
        val normal = Normal(1.0) / 2.0
        assertEquals(.5, normal.x)
        assertEquals(.5, normal.y)
        assertEquals(.5, normal.z)
    }

    @Test
    internal fun `addition of two normals`() {
        val normal = Normal(1.0) + Normal(1.0)
        assertEquals(2.0, normal.x)
        assertEquals(2.0, normal.y)
        assertEquals(2.0, normal.z)
    }

    @Test
    internal fun `dot product with a vector on the right`() {
        val dotProduct: Double = Normal(1.0) * Vector3D(1.0)
        assertEquals(3.0, dotProduct)
    }

    @Test
    internal fun `normalize normal`() {
        val normal = Normal(1.0).normalize()
        assertEquals(0.5773, normal.x, .0001)
        assertEquals(0.5773, normal.y, .0001)
        assertEquals(0.5773, normal.z, .0001)
    }
}