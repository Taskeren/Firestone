package cn.taskeren.firestone.kt

import org.slf4j.LoggerFactory
import java.net.URL
import java.nio.file.Path
import java.util.jar.Manifest
import javax.script.ScriptEngineManager
import javax.script.ScriptException
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.system.exitProcess

fun main(args: Array<String>) {

	val logger = LoggerFactory.getLogger("Firestone")

	val path = args.getOrNull(0)
		?: return println("./firestone <script-path>")

	/**
	 * Allow the remote scripts to be run without asking the user.
	 */
	val argAllowRemote = "--allow-remote-script" in args

	// print firestone metadata
	val manifest = getFirestoneManifest()
	val fsVersion = manifest?.mainAttributes?.getValue("Implementation-Version") ?: "Unknown"
	println("Firestone ($fsVersion)")

	try {
		if(path.startsWith("url:")) { // run remote script
			val scriptUrlText = path.substring(4)
			// malware warning
			if(!argAllowRemote) {
				println("You are running an remote script, which can be malicious.")
				println("Script Url: $scriptUrlText")
				print("Are you sure to continue? [y/N] ")
				if(readlnOrNull()?.lowercase() != "y") {
					logger.info("User terminated the remote script.")
					return
				}
			}
			runScriptUrl(URL(scriptUrlText))
		} else {
			runScriptPath(Path(path))
		}
	} catch(e: ScriptException) {
		logger.error("Your script ran into an error!")
		logger.error("Failed to eval the script!", e)
		exitProcess(1)
	}

}

private val engine by lazy {
	ScriptEngineManager().getEngineByExtension("kts")
		?: error("Cannot get Kotlin Script engine. This is an unreachable error!")
}

internal fun runScriptPath(path: Path): Any? {
	return engine.eval(path.readText())
}

internal fun runScriptUrl(url: URL): Any? {
	return engine.eval(url.readText())
}

private fun getFirestoneManifest(): Manifest? {
	val resources = FirestoneKts::class.java.classLoader.getResources("META-INF/MANIFEST.MF")
	for(resource in resources) {
		val manifest = Manifest(resource.openStream())
		if(manifest.mainAttributes.getValue("Main-Class") == "cn.taskeren.firestone.kt.MainKt") {
			return manifest
		}
	}
	return null
}