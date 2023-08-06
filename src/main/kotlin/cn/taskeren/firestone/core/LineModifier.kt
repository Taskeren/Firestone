package cn.taskeren.firestone.core

interface LineModifier : Modifier {

	fun modifyLine(line: String): String

	override fun modify(wholeDocument: String): String {
		return wholeDocument.lines().joinToString(separator = "\n") {
			modifyLine(it)
		}
	}
}