package cn.taskeren.firestone.js.common

import org.slf4j.LoggerFactory

interface Console {

	fun log(vararg messages: Any?)

	fun info(vararg messages: Any?)

	fun warn(vararg messages: Any?)

	fun error(vararg messages: Any?)

	fun waitEnter()

	fun prompt(message: Any?): String

	companion object : Console {

		private val logger = LoggerFactory.getLogger("Firestone")

		override fun log(vararg messages: Any?) {
			logger.info(messages.joinToString(separator = ", "))
		}

		override fun info(vararg messages: Any?) {
			logger.info(messages.joinToString(separator = ", "))
		}

		override fun warn(vararg messages: Any?) {
			logger.warn(messages.joinToString(separator = ", "))
		}

		override fun error(vararg messages: Any?) {
			logger.error(messages.joinToString(separator = ", "))
		}

		override fun waitEnter() {
			prompt("> ")
		}

		override fun prompt(message: Any?): String {
			if(message != null) print(message)
			return readln()
		}
	}

}