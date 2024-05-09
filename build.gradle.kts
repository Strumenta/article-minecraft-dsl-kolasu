plugins {}

allprojects {
    group = "com.strumenta.articles.minecraftdsl"

    repositories {
        mavenLocal()
        mavenCentral()
    }
}

tasks.wrapper {
    gradleVersion = "8.2.1"
    distributionType = Wrapper.DistributionType.ALL
}
