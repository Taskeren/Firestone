package cn.taskeren.firestone.core.plain

import cn.taskeren.firestone.core.LineModifier

class PlainTextModifier : LineModifier {

	data class Rule(
		val matcher: (String) -> Boolean,
		val modifier: (String) -> String,
	)

	private val rules = mutableListOf<Rule>()

	fun addRule(rule: Rule) = rules.add(rule)

	override fun modifyLine(line: String): String {
		var tempStr = line
		rules.forEach { (matcher, modifier) ->
			if(matcher(tempStr)) {
				tempStr = modifier(tempStr)
			}
		}
		return tempStr
	}

}