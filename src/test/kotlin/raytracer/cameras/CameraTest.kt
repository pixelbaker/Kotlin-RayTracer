package raytracer.cameras

import raytracer.utilities.Point3D
import raytracer.utilities.Vector3D
import raytracer.world.World
import kotlin.test.Test
import kotlin.test.assertEquals

internal class CameraTest {
    class TestCamera : Camera {
        constructor() : super()
        constructor(c: TestCamera) : super(c)

        override fun renderScene(world: World) {}
    }

    @Test
    internal fun `default constructor`() {
        val cut = TestCamera()

        with(cut) {
            assertEquals(Point3D(.0, .0, 500.0), eye)
            assertEquals(Point3D(), lookat)
            assertEquals(.0, rollAngle)
            assertEquals(Vector3D(1.0, 0.0, 0.0), u)
            assertEquals(Vector3D(0.0, 1.0, 0.0), v)
            assertEquals(Vector3D(0.0, 0.0, 1.0), w)
            assertEquals(Vector3D(0.0, 1.0, 0.0), up)
            assertEquals(1.0, exposureTime)
        }
    }

    @Test
    internal fun `copy constructor`() {
        val cameraToCopyFrom = TestCamera().apply {
            eye = Point3D(1.0)
            lookat = Point3D(2.0)
            rollAngle = 1.0
            u = Vector3D(3.0)
            v = Vector3D(4.0)
            w = Vector3D(5.0)
            up = Vector3D(6.0)
            exposureTime = 2.0
        }

        val cut = TestCamera(cameraToCopyFrom)

        with(cut) {
            assertEquals(Point3D(1.0), eye)
            assertEquals(Point3D(2.0), lookat)
            assertEquals(1.0, rollAngle)
            assertEquals(Vector3D(3.0), u)
            assertEquals(Vector3D(4.0), v)
            assertEquals(Vector3D(5.0), w)
            assertEquals(Vector3D(6.0), up)
            assertEquals(2.0, exposureTime)
        }
    }

    @Test
    internal fun `camera looking vertically down`() {
        val cut = TestCamera()
        cut.eye = Point3D(0.0, 1.0, 0.0)
        cut.lookat = Point3D(0.0, 0.0, 0.0)

        cut.compute_uvw()

        with(cut) {
            assertEquals(Vector3D(0.0, 0.0, 1.0), u)
            assertEquals(Vector3D(1.0, 0.0, 0.0), v)
            assertEquals(Vector3D(0.0, 1.0, 0.0), w)
        }
    }

    @Test
    internal fun `camera looking vertically up`() {
        val cut = TestCamera()
        cut.eye = Point3D(0.0, 0.0, 0.0)
        cut.lookat = Point3D(0.0, 1.0, 0.0)

        cut.compute_uvw()

        with(cut) {
            assertEquals(Vector3D(1.0, 0.0, 0.0), u)
            assertEquals(Vector3D(0.0, 0.0, 1.0), v)
            assertEquals(Vector3D(0.0, -1.0, 0.0), w)
        }
    }

    @Test
    internal fun `compute uvw`() {
        val cut = TestCamera()
        cut.eye = Point3D(0.0, 0.0, 0.0)
        cut.lookat = Point3D(0.0, 0.0, 1.0)

        cut.compute_uvw()

        with(cut) {
            assertEquals(Vector3D(-1.0, 0.0, 0.0), u)
            assertEquals(Vector3D(0.0, 1.0, 0.0), v)
            assertEquals(Vector3D(0.0, 0.0, -1.0), w)
        }
    }
}