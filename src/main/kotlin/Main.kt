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
    world.build(pinholeScene)

    //measureTime { world.renderScene() }
    measureTime { world.camera?.renderScene(world) }
        .also { println("Render Time: ${it.toDouble(DurationUnit.SECONDS)} sec") }

    saveImage(world.image)
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
