import cn.taskeren.firestone.core.findByPath
import cn.taskeren.firestone.core.matchAnyAndReplace
import cn.taskeren.firestone.core.replaceString
import cn.taskeren.firestone.kt.JsonModifier
import cn.taskeren.firestone.kt.TextModifier
import cn.taskeren.firestone.kt.applyOn
import kotlin.io.path.*

println("Test Main")

val temp = Path("temp_file")
if(!temp.exists()) temp.createFile()

temp.writeText(
	"""{
		"a": {
			"b": "test"
		}
	}""".trimIndent()
)

JsonModifier {
	findByPath("a", "b") {
		replaceString { "$it + AAA" }
	}
}.applyOn(temp)

println(temp.readText())

temp.writeText(
	"""# this is a test eula file
	eula=false
	""".trimIndent()
)

TextModifier {
	matchAnyAndReplace("eula=false") {
		{ _ -> "eula=true" }
	}
}.applyOn(temp)

println(temp.readText())