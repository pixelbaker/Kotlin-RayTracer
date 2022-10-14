package raytracer.world

import raytracer.samplers.Regular
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertTrue

internal class ViewPlaneTest {

    @Test
    internal fun `default constructor`() {
        val cut = ViewPlane()
        assertEquals(400, cut.hres)
        assertEquals(1.0, cut.invGamma)
        assertTrue(cut.sampler is Regular)
    }

    @Test
    internal fun `copy constructor`() {
        val viewPlaneToCopyFrom = ViewPlane().apply { hres = 1920 }
        val cut = ViewPlane(viewPlaneToCopyFrom)
        assertEquals(viewPlaneToCopyFrom.hres, cut.hres)
        assertNotSame(viewPlaneToCopyFrom.sampler, cut.sampler)
        assertEquals(viewPlaneToCopyFrom.numSamples, cut.numSamples)
    }

    @Test
    internal fun `setting gamma affect inv gamma`() {
        val cut = ViewPlane().apply { gamma = 2.0 }
        assertEquals(.5, cut.invGamma)
    }

    @Test
    internal fun `setting a sampler also sets nusmSample`() {
        val cut = ViewPlane().apply { sampler = Regular(25) }
        assertEquals(25, cut.numSamples)
    }

    @Test
    internal fun `setting numSamples also inits Sampler with 25 samples`() {
        val cut = ViewPlane().apply { numSamples = 25 }
        assertEquals(25, cut.sampler.numSamples)
    }

    @Test
    internal fun `showOutOfGamut code coverage gap`() {
        val cut = ViewPlane().apply { showOutOfGamut = true }
        assertTrue(cut.showOutOfGamut)
    }
}