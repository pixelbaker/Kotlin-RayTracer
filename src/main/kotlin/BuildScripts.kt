import raytracer.cameras.Pinhole
import raytracer.geometries.Sphere
import raytracer.tracers.MultipleObjects
import raytracer.tracers.SingleSphere
import raytracer.utilities.Point3D
import raytracer.utilities.RGBColor
import raytracer.utilities.red
import raytracer.utilities.white
import raytracer.world.BuildScript

val pinholeScene: BuildScript = {
    with(it) {
        viewPlane.hres = 300
        viewPlane.vres = 300

        tracer = MultipleObjects(this)

        camera = Pinhole().apply { eye = Point3D(0.0, 0.0, 500.0) }

        objects.add(
            Sphere(Point3D(-45.0, 45.0, 40.0), 50.0).apply { color = RGBColor(red) })
        objects.add(
            Sphere(Point3D(20.0, 0.0, 0.0), 40.0).apply { color = RGBColor(1.0, 1.0, 0.0) })
        objects.add(
            Sphere(Point3D(80.0, -30.0, -65.0), 110.0).apply { color = RGBColor(white) })
    }
}

val singleSphereScene: BuildScript = {
    with(it) {
        viewPlane.hres = 200
        viewPlane.vres = 200
        viewPlane.gamma = 1.0

        tracer = SingleSphere(this)
        sphere = Sphere(radius = 80.0)
    }
}