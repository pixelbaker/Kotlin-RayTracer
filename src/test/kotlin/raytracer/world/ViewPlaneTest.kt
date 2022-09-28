package raytracer.world

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ViewPlaneTest {

    @Test
    internal fun `default constructor`() {
        val vp = ViewPlane()
        assertEquals(400, vp.hres)
        assertEquals(1.0, vp.invGamma)
    }

    @Test
    internal fun `copy constructor`() {
        val vp = ViewPlane(ViewPlane(hres = 1920))
        assertEquals(1920, vp.hres)
        assertEquals(1.0, vp.invGamma)
    }

    @Test
    internal fun `setting gamma affect inv gamma`() {
        val vp = ViewPlane()
        vp.gamma = 2.0
        assertEquals(.5, vp.invGamma)
    }
}