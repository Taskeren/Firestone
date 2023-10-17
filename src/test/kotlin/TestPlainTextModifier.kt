import cn.taskeren.firestone.core.PlainTextModifier
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestPlainTextModifier {

	@Test
	fun testCase1() {
		val text = """
			Hi!
			My name is Taskeren.
		""".trimIndent()
		val modified = PlainTextModifier().apply {
			addRule(PlainTextModifier.Rule({ it.contains("Taskeren") }, { it.replace("Taskeren", "Superconductor") }))
		}.modify(text)
		assertEquals("Hi!\nMy name is Superconductor.", modified)
	}

}