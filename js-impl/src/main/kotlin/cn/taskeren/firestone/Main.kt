package cn.taskeren.firestone

import cn.taskeren.firestone.js.common.Console
import cn.taskeren.firestone.js.firestone.FirestoneJs
import org.mozilla.javascript.Context
import org.mozilla.javascript.ScriptableObject
import org.slf4j.LoggerFactory
import java.io.File

fun main(args: Array<String>) {
	val scriptPath = args.getOrNull(0) ?: return println("firestone.jar <script-path>")
	execute(scriptPath)
}

private val logger = LoggerFactory.getLogger("Firestone Ignite")

private fun execute(path: String) {
	Context.enter().use { ctx ->
		val scope = ctx.initStandardObjects()

		ScriptableObject.putProperty(scope, "console", Context.javaToJS(Console, scope))
		ScriptableObject.putProperty(scope, "firestone", Context.javaToJS(FirestoneJs(), scope))

		runCatching {
			ctx.evaluateReader(scope, File(path).bufferedReader(), path, 1, null)
		}.onFailure {
			logger.error("Error occurred when executing script $path", it)
		}
	}
}
