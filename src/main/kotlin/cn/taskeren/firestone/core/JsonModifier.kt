package cn.taskeren.firestone.core

import com.google.gson.*

class JsonModifier : Modifier {

	sealed interface Rule

	/**
	 * @sample findByPath
	 */
	data class FindByPath(
		val paths: List<String>,
		val replacer: (JsonElement) -> JsonElement,
	) : Rule

	private val rules = mutableListOf<Rule>()

	fun addRule(rule: Rule) {
		rules += rule
	}

	override fun modify(wholeDocument: String): String {
		var json = gson.fromJson(wholeDocument, JsonElement::class.java)

		rules.forEach { rule ->
			if(rule is FindByPath) {
				val paths = rule.paths.iterator()
				var parentJson: JsonElement? = null
				var targetJson = json
				var key: String? = null
				var keyIndex: Int = -1

				// find the parent and the primitive
				while(paths.hasNext()) {
					key = paths.next()
					if(targetJson is JsonObject) {
						if(targetJson.has(key)) {
							parentJson = targetJson
							targetJson = targetJson.get(key) ?: error("Cannot find key $key")
						}
					} else if(targetJson is JsonArray) {
						keyIndex = key.toIntOrNull() ?: error("Expect number for Json Array $key")
						parentJson = targetJson
						targetJson = targetJson.get(keyIndex) ?: error("Cannot find key $key")
					}
				}

				// change the element in the parent
				if(key == null) { // directly modify the original json
					json = rule.replacer(json)
				} else { // modify the element inside
					// the target must be primitive
					if(targetJson !is JsonPrimitive) error("Expect primitive $key")

					if(parentJson is JsonObject) {
						parentJson.add(key, rule.replacer(parentJson.get(key)))
					} else if(parentJson is JsonArray) {
						if(keyIndex != -1) {
							parentJson.set(keyIndex, rule.replacer(parentJson.get(keyIndex)))
						}
					} else { // if parent is null
						json = rule.replacer(json)
					}
				}
			}
		}

		return gson.toJson(json)
	}

	companion object {
		private val gson = Gson()
	}
}


/**
 * # Find By Paths
 *
 * Adds a rule that property matches [paths] will be replaced by [replacer].
 *
 * ## Path
 *
 * Path is a list of [String] leads to the property.
 *
 * For example:
 *
 * ```json
 * {
 *   "me": {
 *     "name": "William",
 *     "age": 25
 *   },
 *   "friends": [
 *   	{
 *        "name": "Robert",
 *        "age": 30
 *   	}
 *   ]
 * }
 * ```
 *
 * | Path | Property Value |
 * | ---- | --: |
 * | `["me", "name"]` | `"William"` |
 * | `["me", "age"]` | `25` |
 * | `["friends", "0", "name"]` | `"Robert"` |
 *
 * *Array index is also supported, you can use int in string type to use it, like Robert's name.
 *
 * ## Replacer
 *
 * Replacer is a function accepts the original value and returns the new value to replace.
 *
 * There are some convenience functions for you to use:
 *
 * - [replaceBoolean]
 * - [replaceString]
 * - [replaceNumber]
 * - etc.
 *
 * @param paths the path to the property
 * @param replacer the element replacer
 */
fun JsonModifier.findByPath(
	vararg paths: String,
	replacer: () -> ((JsonElement) -> JsonElement),
) = addRule(JsonModifier.FindByPath(paths.toList(), replacer()))

fun replaceBoolean(block: (Boolean) -> Boolean): (JsonElement) -> JsonElement {
	return { JsonPrimitive(block(it.asBoolean)) }
}

fun replaceString(block: (String) -> String): (JsonElement) -> JsonElement {
	return { JsonPrimitive(block(it.asString)) }
}

fun replaceNumber(block: (Number) -> Number): (JsonElement) -> JsonElement {
	return { JsonPrimitive(block(it.asNumber)) }
}