package cn.taskeren.firestone.js.firestone

import cn.taskeren.firestone.js.common.Console
import cn.taskeren.firestone.js.common.JavaFunction
import java.nio.file.Path
import kotlin.io.path.*

@Suppress("unused", "MemberVisibilityCanBePrivate")
class FirestoneJs {

	val cwd = Path(".").absolutePathString()

	val console = Console

	// setting

	var backup: Boolean = false

	// file

	fun isFile(path: String): Boolean {
		return Path(path).isRegularFile()
	}

	fun isDir(path: String): Boolean {
		return Path(path).isDirectory()
	}

	// new file

	fun newPlain(path: String) = Path(path).apply {
		if(!exists()) {
			createFile()
		}
	}

	// read

	fun readText(path: String): String {
		return Path(path).readText()
	}

	// write

	fun writeText(path: String, content: String) = Path(path).apply {
		if(!exists()) {
			createFile()
		}
		writeTextBackup(content)
	}

	fun appendText(path: String, content: String) = Path(path).apply {
		if(!exists()) {
			createFile()
		}
		appendTextBackup(content)
	}

	// plain text modifier

	fun newPlainTextModifierScope(block: JavaFunction<PlainTextModifierScope, Any>) {
		block.apply(PlainTextModifierScope(this))
	}

	// logging

	fun log(message: Any?) {
		println(message)
	}

	// internal

	/**
	 * Get parent path by calling file system.
	 */
	private val Path.parentNotNull: Path get() = parent ?: toFile().absoluteFile.parentFile.toPath()

	private fun Path.createBackup() {
		val backupPath = parentNotNull.resolve(this.nameWithoutExtension + "_backup." + this.extension)
		if(backupPath.exists()) {
			backupPath.deleteExisting()
		}
		this.copyTo(backupPath)
	}

	private fun Path.writeTextBackup(text: String) {
		if(backup) {
			createBackup()
		}
		writeText(text)
	}

	private fun Path.appendTextBackup(text: String) {
		if(backup) {
			createBackup()
		}
		appendText(text)
	}

}