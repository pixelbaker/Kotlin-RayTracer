package raytracer.samplers

import raytracer.utilities.Point2D
import raytracer.utilities.Point3D
import java.lang.Math.pow
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

abstract class Sampler {

    init {
        setupShuffledIndices()
    }

    constructor(numSamples: Int = 1, numSets: Int = 83) {
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

    abstract fun clone(): Sampler

    /**
     * the number of sample points in a set
     */
    var numSamples: Int = 1
        private set

    /**
     * the number of sample sets
     */
    var numSets: Int = 83
        private set

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
    private var diskSamples: MutableList<Point2D> = emptyList<Point2D>().toMutableList()

    /**
     * sample points on a unit hemisphere
     */
    private var hemisphereSamples: MutableList<Point3D> = emptyList<Point3D>().toMutableList()

    /**
     * sample points on a unit sphere
     */
    private var sphereSamples: MutableList<Point3D> = emptyList<Point3D>().toMutableList()

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
     * shuffle the x coordinates of the points within each set
     */
    protected fun shuffleXCoordinates() {
        for (p in 0 until numSets) {
            for (i in 0 until numSamples - 1) {
                val targetIndex = Random.nextInt(0, Int.MAX_VALUE) % numSamples + p * numSamples
                val tempIndex = i + p * numSamples + 1
                val temp: Double = samples[tempIndex].x
                samples[tempIndex].x = samples[targetIndex].x
                samples[targetIndex].x = temp
            }
        }
    }

    /**
     * shuffle the y coordinates of the points within each set
     */
    private fun shuffleYCoordinates() {
        for (p in 0 until numSets) {
            for (i in 0 until numSamples) {
                val targetIndex = Random.nextInt(0, Int.MAX_VALUE) % numSamples + p * numSamples
                val tempIndex = i + p * numSamples + 1
                val temp: Double = samples[tempIndex].y
                samples[tempIndex].y = samples[targetIndex].y
                samples[targetIndex].y = temp
            }
        }
    }

    /**
     * Maps the 2D sample points in the square [-1,1] X [-1,1] to a unit disk, using Peter Shirley's concentric map function
     */
    private fun mapSamplesToUnitDisk() {
        // polar coordinates
        var r: Double
        var phi: Double

        // sample point on unit disk
        val sp = Point2D()

        for (j in 0 until samples.size) {
            // map sample point to [-1, 1] X [-1,1]
            sp.x = 2.0 * samples[j].x - 1.0
            sp.y = 2.0 * samples[j].y - 1.0

            if (sp.x > -sp.y) { // sectors 1 and 2
                if (sp.x > sp.y) { // sector 1
                    r = sp.x
                    phi = sp.y / sp.x
                } else { // sector 2
                    r = sp.y
                    phi = 2.0 - sp.x / sp.y
                }
            } else { // sectors 3 and 4
                if (sp.x < sp.y) { // sector 3
                    r = -sp.x
                    phi = 4.0 + sp.y / sp.x
                } else { // sector 4
                    r = sp.y
                    phi = if (sp.y != 0.0) // avoid division by zero at origin
                        6.0 - sp.x / sp.y
                    else
                        0.0
                }
            }
            phi *= PI / 4.0

            val x = r * cos(phi)
            val y = r * sin(phi)
            diskSamples.add(Point2D(x, y))
        }

        samples.clear()
    }

    /**
     * Maps the 2D sample points to 3D points on a unit hemisphere with a cosine power density distribution in the polar angle
     */
    private fun mapSamplesToHemisphere(exp: Double) {
        for (j in 0 until samples.size) {
            val cosPhi = cos(2.0 * PI * samples[j].x)
            val sinPhi = sin(2.0 * PI * samples[j].x)

            val cosTheta = pow(1.0 - samples[j].y, 1.0 / (exp + 1.0))
            val sinTheta = sqrt(1.0 - cosTheta * cosTheta)

            val pu = sinTheta * cosPhi
            val pv = sinTheta * sinPhi
            val pw = cosTheta
            hemisphereSamples.add(Point3D(pu, pv, pw))
        }
    }

    /**
     * Maps the 2D sample points to 3D points on a unit sphere with a uniform density distribution over the surface.
     * This is used for modelling a spherical light
     */
    private fun mapSamplesToSphere() {
        for (j in 0 until numSamples * numSets) {
            val r1 = samples[j].x
            val r2 = samples[j].y
            val z = 1.0 - 2.0 * r1
            val r = sqrt(1.0 - z * z)
            val phi = 2.0 * PI * r2
            val x = r * cos(phi)
            val y = r * sin(phi)

            sphereSamples.add(Point3D(x, y, z))
        }
    }

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

    private fun calcIndex(): Int {
        val numSamplesUL = numSamples.toULong()

        if (count % numSamplesUL == 0UL) { // start of a new pixel
            jump = (Random.nextInt(0, Int.MAX_VALUE) % numSets) * numSamples
        }

        val jumpUL = jump.toULong()
        val shuffledIndex = (jumpUL + count++ % numSamplesUL).toInt()
        return jump + shuffledIndices[shuffledIndex]
    }

    /**
     * get next sample on unit square
     */
    fun sampleUnitSquare(): Point2D {
        return samples[calcIndex()]
    }

    /**
     * get next sample on unit disk
     */
    fun sampleUnitDisk(): Point2D {
        return diskSamples[calcIndex()]
    }

    /**
     * get next sample on unit hemisphere
     */
    fun sampleHemisphere(): Point3D {
        return hemisphereSamples[calcIndex()]
    }

    /**
     * get next sample on unit sphere
     */
    fun sampleSphere(): Point3D {
        return sphereSamples[calcIndex()]
    }

    /**
     * only used to set up a vector noise table
     * This is a specialised function called in LatticeNoise::init_vector_table
     * It doesn't shuffle the indices
     * this is not discussed in the book, but see the file LatticeNoise.cpp in Chapter 31
     */
    fun sampleOneSet(): Point2D {
        val index = (count++ % numSamples.toULong()).toInt()
        return samples[index]
    }
}