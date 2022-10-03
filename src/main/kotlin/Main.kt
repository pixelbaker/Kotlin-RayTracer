import raytracer.tracers.SingleSphere
import raytracer.world.World

fun main(args: Array<String>) {
    val world = World()
    world.tracer = SingleSphere(world)
    world.renderScene()
}