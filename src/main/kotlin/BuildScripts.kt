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

        camera = with(Pinhole()) {
            eye = Point3D(0.0, 0.0, 500.0)
            this
        }

        objects.add(
            Sphere(radius = 50.0, center = Point3D(-45.0, 45.0, 40.0))
                .also { it.color = RGBColor(red) })
        objects.add(
            Sphere(radius = 40.0, center = Point3D(20.0, 0.0, 0.0))
                .also { it.color = RGBColor(1.0, 1.0, 0.0) })
        objects.add(
            Sphere(radius = 110.0, center = Point3D(80.0, -30.0, -65.0))
                .also { it.color = RGBColor(white) })
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