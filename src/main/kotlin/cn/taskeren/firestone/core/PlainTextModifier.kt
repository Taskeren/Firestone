package cn.taskeren.firestone.core

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

fun PlainTextModifier.matchAndReplace(
	matcher: (String) -> Boolean,
	replacer: () -> ((String) -> String),
) = addRule(PlainTextModifier.Rule(matcher, replacer()))

fun PlainTextModifier.matchAllAndReplace(
	lineValue: String,
	replacer: () -> (line: String) -> String
) = addRule(PlainTextModifier.Rule({ lineValue == it }, replacer()))

fun PlainTextModifier.matchAnyAndReplace(
	wordValue: String,
	replacer: () -> (String) -> String
) = addRule(PlainTextModifier.Rule({ wordValue in it }, replacer()))