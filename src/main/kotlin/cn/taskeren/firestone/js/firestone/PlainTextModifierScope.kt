package cn.taskeren.firestone.js.firestone

import cn.taskeren.firestone.core.plain.PlainTextModifier
import cn.taskeren.firestone.core.plain.PlainTextModifier.Rule
import cn.taskeren.firestone.js.common.JavaFunction

@Suppress("unused")
class PlainTextModifierScope(private val api: FirestoneJs, private val modifier: PlainTextModifier = PlainTextModifier()) {

	fun replace(
		replacer: JavaFunction<String, String>
	) {
		modifier.addRule(Rule({ true }, replacer::apply))
	}

	fun ifContainsThenReplace(
		what: String,
		replacer: JavaFunction<String, String>
	) {
		modifier.addRule(Rule({ it.contains(what) }, replacer::apply))
	}

	fun applyOn(path: String) {
		api.writeText(path, modifier.modify(api.readText(path)))
	}

}