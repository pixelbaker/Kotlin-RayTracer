package raytracer.world

import raytracer.samplers.MultiJittered
import raytracer.samplers.Regular
import raytracer.samplers.Sampler

class ViewPlane() {

    constructor(vp: ViewPlane) : this() {
        hres = vp.hres
        vres = vp.vres
        pixelSize = vp.pixelSize
        numSamples = vp.numSamples
        gamma = vp.gamma
        showOutOfGamut = vp.showOutOfGamut
        sampler = vp.sampler.clone()
    }

    /**
     * horizontal image resolution
     */
    var hres: Int = 400

    /**
     * vertical image resolution
     */
    var vres: Int = 400

    var pixelSize: Double = 1.0

    /**
     * number of samples per pixel
     */
    private var _numSamples: Int = 1
    var numSamples: Int
        get() = _numSamples
        set(value) {
            sampler = if (value == 1) Regular(numSamples = 1) else MultiJittered(numSamples = value)
            _numSamples = value
        }

    /**
     * sampler algorithm
     */
    var sampler: Sampler = Regular(numSamples)
        set(value) {
            _numSamples = value.numSamples
            field = value
        }

    /**
     * gamma correction factor
     */
    var gamma: Double = 1.0

    /**
     * the inverse of the gamma correction factor
     */
    val invGamma: Double
        get() = 1.0 / gamma

    /**
     * display red if RGBColor out of gamut
     */
    var showOutOfGamut: Boolean = false
}
