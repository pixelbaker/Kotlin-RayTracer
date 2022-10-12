package raytracer.world

import raytracer.samplers.Sampler

class ViewPlane(

    /**
     * horizontal image resolution
     */
    var hres: Int = 400,

    /**
     * vertical image resolution
     */
    var vres: Int = 400,

    var pixelSize: Double = 1.0,

    /**
     * number of samples per pixel
     */
    var numSamples: Int = 1,

    /**
     * gamma correction factor
     */
    var gamma: Double = 1.0,

    /**
     * display red if RGBColor out of gamut
     */
    var showOutOfGamut: Boolean = false,
) {

    constructor(vp: ViewPlane) : this(
        hres = vp.hres,
        vres = vp.vres,
        pixelSize = vp.pixelSize,
        numSamples = vp.numSamples,
        gamma = vp.gamma,
        showOutOfGamut = vp.showOutOfGamut,
    )

    /**
     * the inverse of the gamma correction factor
     */
    val invGamma: Double
        get() = 1.0 / gamma

    var sampler: Sampler? = null
}
