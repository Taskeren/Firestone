import cn.taskeren.firestone.core.JsonModifier
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestJsonModifier {

	val gson = GsonBuilder().create()

	@Test
	fun test1() {
		val beforeModMap = mapOf(
			"test-key" to "0",
			"test-object" to mapOf(
				"test-key" to "1"
			)
		)
		val beforeModJson = gson.toJson(beforeModMap)

		val afterModJson = JsonModifier().apply {
			addRule(JsonModifier.FindByPath(listOf("test-object", "test-key")) { JsonPrimitive("9") })
			addRule(JsonModifier.FindByPath(listOf("test-key")) { JsonPrimitive("10") })
		}.modify(beforeModJson)

		val afterModMap = mapOf(
			"test-key" to "10",
			"test-object" to mapOf(
				"test-key" to "9"
			)
		)

		assertEquals(gson.toJson(afterModMap), afterModJson)

	}

	@Test
	fun test2() {
		val beforeModJson = gson.toJson("6")

		val afterModJson = JsonModifier().apply {
			addRule(JsonModifier.FindByPath(listOf()) { JsonPrimitive(6) })
		}.modify(beforeModJson)

		val expectedAfterModJson = gson.toJson(6)

		assertEquals(expectedAfterModJson, afterModJson)

	}

	@Test
	fun toRemoved() {

	}

}