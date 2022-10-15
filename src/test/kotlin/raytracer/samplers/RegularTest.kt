package raytracer.samplers

import org.junit.jupiter.api.Test
import raytracer.utilities.Point2D
import kotlin.test.assertEquals

internal class RegularTest {
    @Test
    internal fun `clone works`() {
        //Given
        val cut = Regular(numSamples = 4)

        //When
        val result = cut.clone()

        //Then
        assertEquals(cut.numSamples, result.numSamples)

        val expected = (1..4).map { cut.sampleUnitSquare() }.toSet()
        val actual = (1..4).map { result.sampleUnitSquare() }.toSet()
        assertEquals(expected, actual)

        val expected2 = setOf(
            Point2D(x = 0.75, y = 0.75),
            Point2D(x = 0.25, y = 0.25),
            Point2D(x = 0.75, y = 0.25),
            Point2D(x = 0.25, y = 0.75),
        )
        assertEquals(expected2, actual)
    }
}