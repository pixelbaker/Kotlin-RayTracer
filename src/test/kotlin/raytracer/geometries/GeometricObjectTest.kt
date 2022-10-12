package raytracer.geometries

import org.junit.jupiter.api.Test
import raytracer.materials.Material
import raytracer.utilities.Ray
import raytracer.utilities.RayParam
import raytracer.utilities.ShadingRecord
import kotlin.test.assertNotNull

internal class GeometricObjectTest {

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