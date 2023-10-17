import cn.taskeren.firestone.kt.FirestoneKts

fun main() {

	val testScriptUrl = FirestoneKts::class.java.getResource("/test_main.kts")!!
	cn.taskeren.firestone.kt.main(arrayOf("url:$testScriptUrl", "--allow-remote-script"))

}