package cn.taskeren.firestone.kt

class ArgParser(private val args: Array<String>) {

	fun getArg(longKey: String, shortKey: String? = null): Arg {
		return getArg0(longKey, shortKey, keyRequired = false, valueRequired = false)
	}

	fun getArgValue(longKey: String, shortKey: String? = null): ArgWithValue {
		return getArg0(longKey, shortKey, keyRequired = true, valueRequired = true) as ArgWithValue
	}

	private fun getArg0(
		longKey: String,
		shortKey: String? = null,
		keyRequired: Boolean = true,
		valueRequired: Boolean = false,
	): Arg {
		if(valueRequired) require(keyRequired)

		val longKeyIndex = args.indexOf("--$longKey")
		val shortKeyIndex = shortKey?.let { args.indexOf("-$it") }

		if(longKeyIndex != -1 && shortKeyIndex != -1) {
			error("Invalid arguments: --$longKey and -$shortKey cannot be used at the same time!")
		}

		val keyIndex = longKeyIndex.takeUnless { it == -1 } ?: shortKeyIndex.takeUnless { it == -1 }

		if(keyRequired && keyIndex == null) {
			error("Invalid arguments: --$longKey or -$shortKey is not found!")
		}

		return if(valueRequired) {
			val valueIndex = keyIndex!! + 1
			if(valueIndex !in args.indices) {
				error("Invalid arguments: value for --$longKey is required!")
			}
			ArgWithValue(longKey, args[valueIndex])
		} else {
			ArgWithoutValue(longKey, keyIndex != null)
		}
	}

	sealed class Arg(
		val key: String,
		val keyFound: Boolean,
		val value: String?,
	)

	class ArgWithoutValue(key: String, keyFound: Boolean) : Arg(key, keyFound, null)
	class ArgWithValue(key: String, value: String) : Arg(key, true, value)

}