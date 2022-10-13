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

        override fun generateSamples() {
            for (set in 1..numSets) {
                for (sample in 1..numSamples) {
                    samples.add(Point2D(set.toDouble(), sample.toDouble()))
                }
            }
        }

        val _numSamples: Int
            get() = super.numSamples

        val _numSets: Int
            get() = super.numSets

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
        
        assertEquals(samplerToCopyFrom._numSets, cut._numSets)
        assertEquals(samplerToCopyFrom._numSamples, cut._numSamples)

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
}