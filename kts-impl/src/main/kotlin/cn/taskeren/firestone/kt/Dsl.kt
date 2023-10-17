@file:Suppress("FunctionName")

package cn.taskeren.firestone.kt

import cn.taskeren.firestone.core.JsonModifier
import cn.taskeren.firestone.core.Modifier
import cn.taskeren.firestone.core.PlainTextModifier
import java.nio.file.Path
import kotlin.io.path.readText
import kotlin.io.path.writeText

fun Modifier.applyOn(path: Path) = path.let {
	val src = it.readText()
	val dst = modify(src)
	it.writeText(dst)
}

fun JsonModifier(block: JsonModifier.() -> Unit) =
	JsonModifier().apply(block)

fun TextModifier(block: PlainTextModifier.() -> Unit) =
	PlainTextModifier().apply(block)