package raytracer.samplers

import raytracer.utilities.Point2D
import kotlin.test.*

internal class SamplerTest {

    class TestSampler : Sampler {
        constructor(
            numSamples: Int = 1,
            numSets: Int = 83,
        ) : super(
            numSamples = numSamples,
            numSets = numSets,
        )

        constructor(s: TestSampler) : super(s)

        override fun clone(): Sampler = this

        override fun generateSamples() {
            for (set in 1..numSets) {
                for (sample in 1..numSamples) {
                    samples.add(Point2D(set.toDouble(), sample.toDouble()))
                }
            }
        }

        val _shuffledIndices: IntArray
            get() = super.shuffledIndices

        val _samples: List<Point2D>
            get() = super.samples

    }

    @Test
    internal fun `copy constructor`() {
        val samplerToCopyFrom = TestSampler(2, 2).apply { generateSamples() }
        val cut = TestSampler(samplerToCopyFrom)

        assertNotSame(samplerToCopyFrom, cut)

        assertEquals(samplerToCopyFrom.numSets, cut.numSets)
        assertEquals(samplerToCopyFrom.numSamples, cut.numSamples)

        assertNotSame(samplerToCopyFrom._shuffledIndices, cut._shuffledIndices)
        assertNotEquals(samplerToCopyFrom._shuffledIndices, cut._shuffledIndices)

        assertNotSame(samplerToCopyFrom._samples, cut._samples)
        assertEquals(samplerToCopyFrom._samples, cut._samples)
        assertSame(samplerToCopyFrom._samples[0], cut._samples[0])
    }

    @Test
    internal fun `sample a unit square with default numSamples and numSets`() {
        //Given
        val cut = TestSampler()

        cut.generateSamples()

        //When
        val sampleOne = cut.sampleUnitSquare()
        val sampleTwo = cut.sampleUnitSquare()

        //Then
        assertNotEquals(sampleOne, sampleTwo)
        assertNotEquals(sampleOne.x, sampleTwo.x)
        assertEquals(1.0, sampleOne.y)
        assertEquals(1.0, sampleTwo.y)
    }

    @Test
    internal fun `sample a unit square with default more than one numSamples and numSets`() {
        //Given
        val cut = TestSampler(numSamples = 2)

        cut.generateSamples()

        //When
        val sampleOne = cut.sampleUnitSquare()
        val sampleTwo = cut.sampleUnitSquare()

        //Then
        assertNotEquals(sampleOne, sampleTwo)
        assertEquals(sampleOne.x, sampleTwo.x)
        assertTrue(sampleOne.y == 1.0 || sampleOne.y == 2.0)
        assertTrue(sampleTwo.y == 1.0 || sampleTwo.y == 2.0)
    }

    @Test
    internal fun `sample a unit square twice with only one numSamples and numSets`() {
        //Given
        val cut = TestSampler(numSamples = 1, numSets = 1)

        cut.generateSamples()

        //When
        val sampleOne = cut.sampleUnitSquare()
        val sampleTwo = cut.sampleUnitSquare()

        //Then
        assertEquals(sampleOne, sampleTwo)
        assertEquals(Point2D(1.0, 1.0), sampleTwo)
    }

    @Test
    internal fun `shuffleXCoordinates really only shuffles X`() {
        //Given
        val cut = object : Sampler(numSets = 1, numSamples = 10) {
            override fun clone() = this

            override fun generateSamples() {
                repeat(numSamples) {
                    samples.add(Point2D(it.toDouble(), it.toDouble()))
                }
            }

            fun _shuffleXCoordinates() = shuffleXCoordinates()
        }

        cut.generateSamples()

        //When
        cut._shuffleXCoordinates()

        //Then
        val xCoordinates = (1..10)
            .map { cut.sampleUnitSquare() }
            .sortedBy { it.y }
            .map { it.x }

        val orderBeforeShuffling = listOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        assertFalse(orderBeforeShuffling == xCoordinates, "asending order has not been shuffled")
        assertEquals(orderBeforeShuffling.toSet(), xCoordinates.toSet(), "Numbers are missing")
    }
}