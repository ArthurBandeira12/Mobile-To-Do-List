package project.to.doapp.arthur

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform