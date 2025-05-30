plugins {
  kotlin("jvm") version "2.1.21-RC2"
  id("com.gradleup.shadow") version "8.3.0"
  id("xyz.jpenilla.run-paper") version "2.3.1"
  id("com.diffplug.spotless") version "7.0.3"
  id("io.gitlab.arturbosch.detekt") version "1.23.8"
  id("idea")
}

group = "com.github.aoideveloper"

version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/") { name = "papermc-repo" }
  maven("https://oss.sonatype.org/content/groups/public/") { name = "sonatype" }
}

dependencies {
  compileOnly("io.papermc.paper:paper-api:1.21.5-R0.1-SNAPSHOT")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  implementation("net.kyori:adventure-api:4.20.0")
}

tasks {
  runServer {
    // Configure the Minecraft version for our task.
    // This is the only required configuration besides applying the plugin.
    // Your plugin's jar (or shadowJar if present) will be used automatically.
    minecraftVersion("1.21.5")
  }
}

val targetJavaVersion = 21

kotlin { jvmToolchain(targetJavaVersion) }

tasks.build { dependsOn("shadowJar") }

tasks.processResources {
  val props = mapOf("version" to version)
  inputs.properties(props)
  filteringCharset = "UTF-8"
  filesMatching("plugin.yml") { expand(props) }
}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
  javaLauncher =
          javaToolchains.launcherFor {
            vendor = JvmVendorSpec.JETBRAINS
            languageVersion = JavaLanguageVersion.of(21)
          }
  jvmArgs("-XX:+AllowEnhancedClassRedefinition")
}

spotless { kotlin { ktfmt().googleStyle() } }

idea {
  module {
    isDownloadSources = true
    isDownloadJavadoc = true
  }
}