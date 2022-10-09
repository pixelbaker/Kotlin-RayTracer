package raytracer.world

class ViewPlane(
    var hres: Int = 400, // horizontal image resolution
    var vres: Int = 400, // vertical image resolution
    var pixelSize: Double = 1.0,
    val numSamples: Int = 1, // number of samples per pixel
    var gamma: Double = 1.0, // gamma correction factor
    var showOutOfGamut: Boolean = false, //display red if RGBColor out of gamut
) {
    constructor(vp: ViewPlane) : this(
        hres = vp.hres,
        vres = vp.vres,
        pixelSize = vp.pixelSize,
        numSamples = vp.numSamples,
        gamma = vp.gamma,
        showOutOfGamut = vp.showOutOfGamut,
    )

    val invGamma: Double // the inverse of the gamma correction factor
        get() = 1.0 / gamma
}
