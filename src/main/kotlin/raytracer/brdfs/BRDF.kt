package raytracer.brdfs

import raytracer.utilities.RGBColor
import raytracer.utilities.ShadingRecord
import raytracer.utilities.Vector3D
import raytracer.utilities.black

abstract class BRDF {
    
    abstract fun clone(): BRDF

    /**
     * @param wo reflected direction
     * @param wi incoming direction
     */
    open fun f(shadingRecord: ShadingRecord, wo: Vector3D, wi: Vector3D): RGBColor = black()

    /**
     * @param wo reflected direction
     * @param wi incoming direction
     */
    open fun sample_f(shadingRecord: ShadingRecord, wo: Vector3D, wi: Vector3D): RGBColor = black()

    /**
     * @param wo reflected direction
     * @param wi incoming direction
     */
    open fun sample_f(shadingRecord: ShadingRecord, wo: Vector3D, wi: Vector3D, pdf: Double): RGBColor = black()

    /**
     * @param wo reflected direction
     */
    open fun rho(shadingRecord: ShadingRecord, wo: Vector3D): RGBColor = black()
}