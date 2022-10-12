import raytracer.cameras.Pinhole
import raytracer.geometries.Sphere
import raytracer.lights.Directional
import raytracer.materials.Matte
import raytracer.tracers.MultipleObjects
import raytracer.utilities.Point3D
import raytracer.utilities.RGBColor
import raytracer.utilities.Vector3D
import raytracer.utilities.red
import raytracer.world.BuildScript

val pinholeScene: BuildScript = {
    with(it) {
        viewPlane.hres = 300
        viewPlane.vres = 300

        tracer = MultipleObjects(this)

        camera = Pinhole().apply { eye = Point3D(0.0, 0.0, 500.0) }

        lights.add(
            Directional(direction = Vector3D(0.0, 1.0, 1.0))
        )

        objects.add(
            //Sphere(Point3D(-40.0, 40.0, 40.0), 50.0).apply { color = RGBColor(red) })
            Sphere(Point3D(0.0, 0.0, 5.0), 50.0).apply {
                material = Matte().apply { setKd(2.0); setKa(.1); setCd(RGBColor(red)) }
            })
        val yellowMatte = Matte().apply { setKd(2.0); setKa(.1); setCd(RGBColor(1.0, 1.0, 0.0)) }
        val yellowSphere = Sphere(Point3D(50.0, 0.0, 0.0), 40.0).apply { material = yellowMatte }
        objects.add(yellowSphere)
        objects.add(yellowSphere.clone().apply { center = Point3D(-50.0, 0.0, 0.0) })
        objects.add(yellowSphere.clone().apply { center = Point3D(0.0, 50.0, 0.0) })
        objects.add(yellowSphere.clone().apply { center = Point3D(0.0, -50.0, 0.0) })
        objects.add(yellowSphere.clone().apply { center = Point3D(-40.0, -40.0, 0.0) })
        objects.add(yellowSphere.clone().apply { center = Point3D(40.0, -40.0, 0.0) })
        objects.add(yellowSphere.clone().apply { center = Point3D(-40.0, 40.0, 0.0) })
        objects.add(yellowSphere.clone().apply { center = Point3D(40.0, 40.0, 0.0) })
    }
}