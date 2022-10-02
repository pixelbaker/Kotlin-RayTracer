package raytracer.geometries

import org.junit.jupiter.api.Test
import raytracer.materials.Material
import raytracer.utilities.Ray
import raytracer.utilities.RayParam
import raytracer.utilities.ShadingRecord
import kotlin.test.assertNotNull
import kotlin.test.assertNull

internal class GeometricObjectTest {
    @Test
    internal fun `default constructor`() {
        val cut = object : GeometricObject() {

            override fun clone(): GeometricObject = this

            override fun hit(ray: Ray, tmin: RayParam, shadingRecord: ShadingRecord) = true
        }

        assertNull(cut.material)
    }

    @Test
    internal fun `copy constructor`() {
        //Given
        val geometryToCopyFrom = object : GeometricObject() {
            init {
                material = object : Material() {
                    override fun clone() = this
                }
            }

            override fun clone() = this

            override fun hit(ray: Ray, tmin: RayParam, shadingRecord: ShadingRecord) = true
        }

        //When
        val cut = object : GeometricObject(geometryToCopyFrom) {
            override fun clone() = this

            override fun hit(ray: Ray, tmin: RayParam, shadingRecord: ShadingRecord) = true
        }

        //Then
        assertNotNull(cut.material)
    }
}