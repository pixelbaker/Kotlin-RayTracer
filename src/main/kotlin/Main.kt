import raytracer.geometries.Sphere
import raytracer.tracers.SingleSphere
import raytracer.world.BuildScript
import raytracer.world.World
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import javax.imageio.ImageIO
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    val world = World()
    world.build(singleSphereScene)

    measureTime { world.renderScene() }
        .also { println("Render Time: ${it.toDouble(DurationUnit.SECONDS)} sec") }

    saveImage(world.imageResult)
}

private var singleSphereScene: BuildScript = {
    with(it) {
        viewPlane.hres = 200
        viewPlane.vres = 200
        viewPlane.gamma = 1.0

        tracer = SingleSphere(this)
        sphere = Sphere(radius = 80.0)
    }
}

private fun saveImage(image: BufferedImage?) {
    try {
        val path = Paths.get("", "render.png")
            .toAbsolutePath()
            .toString()
            .also { println(it) }
        val file = File(path)
        ImageIO.write(image, "png", file)
    } catch (e: IOException) {
        println(e)
    }
}