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

    class ShuffleSampler :
        Sampler(numSamples = 10, numSets = 1) {

        override fun clone() = this

        override fun generateSamples() {
            repeat(numSamples) {
                samples.add(Point2D(it.toDouble(), it.toDouble()))
            }
        }

        fun _shuffleXCoordinates() = shuffleXCoordinates()
        fun _shuffleYCoordinates() = shuffleYCoordinates()
    }

    @Test
    internal fun `shuffle only X coordinates`() {
        //Given
        val cut = ShuffleSampler()
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

    @Test
    internal fun `shuffle only y coordinates`() {
        //Given
        val cut = ShuffleSampler()
        cut.generateSamples()

        //When
        cut._shuffleYCoordinates()

        //Then
        val yCoordinates = (1..10)
            .map { cut.sampleUnitSquare() }
            .sortedBy { it.x }
            .map { it.y }

        val orderBeforeShuffling = listOf(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        assertFalse(orderBeforeShuffling == yCoordinates, "asending order has not been shuffled")
        assertEquals(orderBeforeShuffling.toSet(), yCoordinates.toSet(), "Numbers are missing")
    }

    class UnitDiskSampler : Sampler(numSamples = 10, numSets = 1) {

        override fun clone() = this

        override fun generateSamples() {
            samples.add(Point2D(-.5, 0.5))
            samples.add(Point2D(0.5, 0.5))
            samples.add(Point2D(-.5, -.5))
            samples.add(Point2D(0.5, -.5))
            samples.add(Point2D(-1.0, 1.0))
            samples.add(Point2D(1.0, 1.0))
            samples.add(Point2D(-1.0, -1.0))
            samples.add(Point2D(1.0, -1.0))
            samples.add(Point2D(0.0, 0.0))
            samples.add(Point2D(1.0, 0.5))
        }

        fun _mapSamplesToUnitDisk() = mapSamplesToUnitDisk()
    }

    @Test
    internal fun `map samples to unit disk`() {
        //Given
        val cut = UnitDiskSampler()
        cut.generateSamples()

        //When
        cut._mapSamplesToUnitDisk()

        //Then
        val samples = (1..cut.numSamples).map { cut.sampleUnitDisk() }

        val expected = listOf(
            Point2D(x = -2.8977774788672046, y = 0.7764571353075631),
            Point2D(x = -2.0, y = 2.4492935982947064E-16),
            Point2D(x = -0.7764571353075609, y = 2.897777478867205),
            Point2D(x = 0.0, y = 0.0),
            Point2D(x = 0.7071067811865476, y = 0.7071067811865475),
            Point2D(x = 0.7071067811865477, y = 0.7071067811865475),
            Point2D(x = 1.0, y = 0.0),
            Point2D(x = 1.4142135623730954, y = 1.414213562373095),
            Point2D(x = 2.121320343559643, y = 2.1213203435596424),
            Point2D(x = 3.6739403974420594E-16, y = 2.0),
        )
        assertEquals(expected.size, samples.size)
        expected.forEach { assertContains(samples, it) }
    }
}