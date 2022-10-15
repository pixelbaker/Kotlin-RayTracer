import raytracer.cameras.Pinhole
import raytracer.geometries.Sphere
import raytracer.lights.Ambient
import raytracer.lights.Directional
import raytracer.materials.Matte
import raytracer.tracers.RayCast
import raytracer.utilities.*
import raytracer.world.BuildScript

val testScene: BuildScript = {
    with(it) {
        viewPlane.hres = 100
        viewPlane.vres = 100
        tracer = RayCast(this)
        camera = Pinhole().apply { eye = Point3D(0.0, 0.0, 100.0) }

        ambient = Ambient(0.333, red())

        lights.add(Directional(1.0, green(), Vector3D(0.0, 0.0, -1.0)))

        objects.add(
            Sphere(Point3D(0.0, 0.0, 0.0), 10.0).apply {
                material = Matte().apply { setKd(1.0); setKa(0.25); setCd(white()) }
            })
    }
}

val pinholeScene: BuildScript = {
    with(it) {
        viewPlane.hres = 300
        viewPlane.vres = 300
        viewPlane.numSamples = 16

        tracer = RayCast(this)

        camera = Pinhole().apply { eye = Point3D(0.0, 0.0, 400.0) }

        lights.add(
            Directional(direction = Vector3D(0.0, 1.0, 1.0))
        )

        objects.add(
            Sphere(Point3D(0.0, 0.0, 5.0), 50.0).apply {
                material = Matte().apply { setKd(2.0); setKa(.1); setCd(red()) }
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