package raytracer.samplers

import raytracer.utilities.Point2D
import kotlin.math.sqrt

class Regular : Sampler {

    init {
        generateSamples()
    }

    constructor(numSamples: Int) : super(numSamples = numSamples) {
        generateSamples()
    }

    constructor(regular: Regular) : super(regular)

    override fun clone(): Sampler = Regular(this)

    override fun generateSamples() {
        val numSamplesSqrted = sqrt(numSamples.toDouble()).toInt()

        for (j in 0 until numSets) {
            for (p in 0 until numSamplesSqrted) {
                for (q in 0 until numSamplesSqrted) {
                    val x = (q + .5) / numSamplesSqrted
                    val y = (p + .5) / numSamplesSqrted
                    samples.add(Point2D(x, y))
                }
            }
        }
    }
}