package raytracer.samplers

import raytracer.utilities.Point2D
import raytracer.utilities.Point3D
import kotlin.random.Random

abstract class Sampler {

    init {
        setupShuffledIndices()
    }

    constructor(numSamples: Int, numSets: Int) {
        this.numSamples = numSamples
        this.numSets = numSets
        setupShuffledIndices()
    }

    constructor(s: Sampler) {
        numSamples = s.numSamples
        numSets = s.numSets
        samples = s.samples.toMutableList()
        shuffledIndices = s.shuffledIndices.clone()
        diskSamples = s.diskSamples.toMutableList()
        hemisphereSamples = s.hemisphereSamples.toMutableList()
        sphereSamples = s.sphereSamples.toMutableList()
        count = s.count
        jump = s.jump
    }

    /**
     * the number of sample points in a set
     */
    protected var numSamples: Int = 1

    /**
     * the number of sample sets
     */
    protected var numSets: Int = 83

    /**
     * sample points on a unit square
     */
    protected var samples: MutableList<Point2D> = emptyList<Point2D>().toMutableList()

    /**
     * shuffled samples array indices
     */
    protected lateinit var shuffledIndices: IntArray

    /**
     * sample points on a unit disk
     */
    private var diskSamples: List<Point2D> = emptyList()

    /**
     * sample points on a unit hemisphere
     */
    private var hemisphereSamples: List<Point3D> = emptyList()

    /**
     * sample points on a unit sphere
     */
    private var sphereSamples: List<Point3D> = emptyList()

    /**
     * the current number of sample points used
     */
    private var count: ULong = 0UL

    /**
     * random index jump
     */
    private var jump: Int = 0

    /**
     * generate sample patterns in a unit square
     */
    abstract fun generateSamples()

    /**
     * sets up randomly shuffled indices for the samples array
     */
    private fun setupShuffledIndices() {
        shuffledIndices = IntArray(0)
        val indices = IntArray(numSamples) { it }

        for (p in 0 until numSets) {
            indices.shuffle()
            shuffledIndices += indices
        }
    }


    fun sampleUnitSquare(): Point2D {
        val numSamplesUL = numSamples.toULong()

        if (count % numSamplesUL == 0UL) {
            jump = (Random.nextInt(0, Int.MAX_VALUE) % numSets) * numSamples
        }

        val jumpUL = jump.toULong()
        val shuffledIndex = (jumpUL + count++ % numSamplesUL).toInt()
        val shiftedShuffledIndex = jump + shuffledIndices[shuffledIndex]
        return samples[shiftedShuffledIndex]
    }
}