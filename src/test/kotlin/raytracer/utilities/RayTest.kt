package raytracer.utilities

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

internal class RayTest {
    @Test
    internal fun `default constructor`() {
        val ray = Ray()
        assertEquals(Point3D(), ray.origin)
        assertEquals(Vector3D(), ray.direction)
    }

    @Test
    internal fun `x, y and z is supplied`() {
        val ray = Ray(
            Point3D(x = 1.0, y = 2.0, z = 3.0),
            Vector3D(4.0, 5.0, 6.0),
        )
        assertEquals(1.0, ray.origin.x)
        assertEquals(2.0, ray.origin.y)
        assertEquals(3.0, ray.origin.z)
        assertEquals(4.0, ray.direction.x)
        assertEquals(5.0, ray.direction.y)
        assertEquals(6.0, ray.direction.z)
    }

    @Test
    internal fun `copy constructor`() {
        val rayToCopyFrom = Ray(
            Point3D(x = 1.0, y = 2.0, z = 3.0),
            Vector3D(4.0, 5.0, 6.0),
        )
        val ray = Ray(rayToCopyFrom)
        assertNotEquals(ray.origin, rayToCopyFrom.origin)
        assertNotEquals(ray.direction, rayToCopyFrom.direction)
        assertEquals(1.0, ray.origin.x)
        assertEquals(2.0, ray.origin.y)
        assertEquals(3.0, ray.origin.z)
        assertEquals(4.0, ray.direction.x)
        assertEquals(5.0, ray.direction.y)
        assertEquals(6.0, ray.direction.z)
    }
}