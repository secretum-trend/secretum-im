@file:Suppress("DEPRECATION")

package im.vector.app.features.debug.sas

import com.airbnb.epoxy.ModelCollector
import kotlin.Suppress
import kotlin.Unit

public inline fun ModelCollector.sasEmojiItem(modelInitializer: SasEmojiItemBuilder.() -> Unit):
    Unit {
  add(
  SasEmojiItem_().apply {
    modelInitializer()
  }
  )
}
