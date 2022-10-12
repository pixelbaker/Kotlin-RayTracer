package raytracer.samplers

import raytracer.utilities.Point2D
import raytracer.utilities.Point3D

abstract class Sampler(

    /**
     * the number of sample points in a set
     */
    private val numSamples: Int = 1,

    /**
     * the number of sample sets
     */
    private val numSets: Int = 83,
) {

    init {
        setupShuffledIndices()
    }

    constructor(s: Sampler) : this(numSamples = s.numSamples, numSets = s.numSets) {
        samples = s.samples
        shuffledIndices = s.shuffledIndices
        diskSamples = s.diskSamples
        hemisphereSamples = s.hemisphereSamples
        sphereSamples = s.sphereSamples
        count = s.count
        jump = s.jump
    }

    /**
     * generate sample patterns in a unit square
     */
    abstract fun generateSamples()

    /**
     * sets up randomly shuffled indices for the samples array
     */
    private fun setupShuffledIndices() {
        val indices = IntArray(numSamples) { it }

        for (p in 0 until numSets) {
            indices.shuffle()
            shuffledIndices += indices
        }
    }

    /**
     * sample points on a unit square
     */
    private var samples: List<Point2D> = emptyList()

    /**
     * shuffled samples array indices
     */
    private var shuffledIndices: IntArray = IntArray(numSamples * numSets)

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
    private var count: Long = 0

    /**
     * random index jump
     */
    private var jump: Int = 0
}