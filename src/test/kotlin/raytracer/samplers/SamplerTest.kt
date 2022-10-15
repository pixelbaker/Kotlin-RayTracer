package raytracer.samplers

import raytracer.utilities.Point2D
import raytracer.utilities.Point3D
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

    class UnitDiskSampler : Sampler(numSamples = 9, numSets = 1) {

        override fun clone() = this

        override fun generateSamples() {
            samples.add(Point2D(x = 0.16666666666666666, y = 0.16666666666666666))
            samples.add(Point2D(x = 0.5, y = 0.16666666666666666))
            samples.add(Point2D(x = 0.8333333333333334, y = 0.16666666666666666))
            samples.add(Point2D(x = 0.16666666666666666, y = 0.5))
            samples.add(Point2D(x = 0.5, y = 0.5))
            samples.add(Point2D(x = 0.8333333333333334, y = 0.5))
            samples.add(Point2D(x = 0.16666666666666666, y = 0.8333333333333334))
            samples.add(Point2D(x = 0.5, y = 0.8333333333333334))
            samples.add(Point2D(x = 0.8333333333333334, y = 0.8333333333333334))
        }

        fun _mapSamplesToUnitDisk() = mapSamplesToUnitDisk()
        fun _mapSamplesToHemisphere(exp: Double) = mapSamplesToHemisphere(exp)
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
            Point2D(x = -0.6666666666666667, y = 8.164311994315689E-17),
            Point2D(x = -0.4714045207910317, y = 0.4714045207910318),
            Point2D(x = -0.4714045207910316, y = 0.47140452079103184),
            Point2D(x = 0.0, y = 0.0),
            Point2D(x = 4.0821559971578444E-17, y = 0.6666666666666667),
            Point2D(x = 1.2246467991473532E-16, y = 0.6666666666666667),
            Point2D(x = 0.47140452079103184, y = 0.4714045207910317),
            Point2D(x = 0.4714045207910318, y = 0.4714045207910317),
            Point2D(x = 0.6666666666666667, y = 0.0),
        )
        assertEquals(expected.size, samples.size)
        expected.forEach { assertContains(samples, it, "$it missing") }
    }

    @Test
    internal fun `map samples to hemisphere`() {
        //Given
        val cut = UnitDiskSampler()
        cut.generateSamples()

        //When
        cut._mapSamplesToHemisphere(1.0)

        //Then
        val samples = (1..cut.numSamples).map { cut.sampleHemisphere() }

        val expected = listOf(
            Point3D(x = -0.9128709291752769, y = 1.117944461449173E-16, z = 0.40824829046386296),
            Point3D(x = -0.7071067811865475, y = 8.659560562354932E-17, z = 0.7071067811865476),
            Point3D(x = -0.40824829046386296, y = 4.999599621739487E-17, z = 0.9128709291752769),
            Point3D(x = 0.20412414523193154, y = -0.3535533905932737, z = 0.9128709291752769),
            Point3D(x = 0.20412414523193154, y = 0.3535533905932737, z = 0.9128709291752769),
            Point3D(x = 0.3535533905932738, y = -0.6123724356957945, z = 0.7071067811865476),
            Point3D(x = 0.3535533905932738, y = 0.6123724356957945, z = 0.7071067811865476),
            Point3D(x = 0.45643546458763856, y = 0.7905694150420949, z = 0.40824829046386296),
            Point3D(x = 0.45643546458763856, y = -0.7905694150420949, z = 0.40824829046386296),
        )
        assertEquals(expected.size, samples.size)
        expected.forEach { assertContains(samples, it) }
    }
}