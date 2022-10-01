package raytracer.tracers

import raytracer.utilities.Ray
import raytracer.utilities.black
import raytracer.world.World
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

internal class TracerTest {
    @Test
    internal fun `default constructor`() {
        val cut = object : Tracer(World()) {
            val worldd: World
                get() = super.world
        }
        assertIs<World>(cut.worldd)
    }

    @Test
    internal fun `trace1 returns black as default`() {
        val cut = object : Tracer(World()) {}
        val result = cut.trace(Ray())
        assertEquals(black, result)
    }

    @Test
    internal fun `trace2 returns black as default`() {
        val cut = object : Tracer(World()) {}
        val result = cut.trace(Ray(), 0)
        assertEquals(black, result)
    }
}