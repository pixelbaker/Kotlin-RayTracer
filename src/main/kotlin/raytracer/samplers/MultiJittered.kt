package raytracer.samplers

import raytracer.utilities.Point2D
import kotlin.math.sqrt
import kotlin.random.Random

//TODO: Investigate how Random can help me to get rid of this code
const val invRAND_MAX = 1.0 / Int.MAX_VALUE.toDouble()

fun rand_int() = Random.nextInt(0, Int.MAX_VALUE)

fun rand_double() = rand_int().toDouble() * invRAND_MAX

fun rand_double(low: Int, high: Double) = rand_double() * (high - low) + low

fun rand_int(low: Int, high: Int): Int {
    val upperBound = (high - low + 1).toDouble()
    return (rand_double(0, upperBound) + low.toDouble()).toInt()
}

class MultiJittered : Sampler {

    init {
        generateSamples()
    }

    constructor(multiJittered: MultiJittered) : super(multiJittered)

    constructor(numSamples: Int = 16, numSets: Int = 83) : super(numSamples = numSamples, numSets = numSets) {
        generateSamples()
    }

    override fun clone() = MultiJittered(this)

    override fun generateSamples() {
        val numSamplesD = numSamples.toDouble()

        // num_samples needs to be a perfect square
        val numSamplesSqrted = sqrt(numSamplesD).toInt()
        val subcellWidth = 1.0 / numSamplesD

        // fill the samples array with dummy points to allow us to use the [ ] notation when we set the initial patterns
        repeat(numSamples * numSets) {
            samples.add(Point2D())
        }

        // distribute points in the initial patterns
        for (p in 0 until numSets) {
            for (i in 0 until numSamplesSqrted) {
                for (j in 0 until numSamplesSqrted) {
                    samples[i * numSamplesSqrted + j + p * numSamples].x =
                        (i * numSamplesSqrted + j) * subcellWidth + rand_double(0, subcellWidth)
                    samples[i * numSamplesSqrted + j + p * numSamples].y =
                        (j * numSamplesSqrted + i) * subcellWidth + rand_double(0, subcellWidth)
                }
            }
        }

        // shuffle x coordinates
        for (p in 0 until numSets) {
            for (i in 0 until numSamplesSqrted) {
                for (j in 0 until numSamplesSqrted) {
                    val k = rand_int(j, numSamplesSqrted - 1)
                    val t = samples[i * numSamplesSqrted + j + p * numSamples].x
                    samples[i * numSamplesSqrted + j + p * numSamples].x =
                        samples[i * numSamplesSqrted + k + p * numSamples].x
                    samples[i * numSamplesSqrted + k + p * numSamples].x = t
                }
            }
        }

        // shuffle y coordinates
        for (p in 0 until numSets) {
            for (i in 0 until numSamplesSqrted) {
                for (j in 0 until numSamplesSqrted) {
                    val k = rand_int(j, numSamplesSqrted - 1)
                    val t = samples[j * numSamplesSqrted + i + p * numSamples].y
                    samples[j * numSamplesSqrted + i + p * numSamples].y =
                        samples[k * numSamplesSqrted + i + p * numSamples].y
                    samples[k * numSamplesSqrted + i + p * numSamples].y = t
                }
            }
        }
    }
}