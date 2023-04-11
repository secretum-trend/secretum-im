@file:Suppress("DEPRECATION")

package im.vector.app.features.debug.features

import com.airbnb.epoxy.ModelCollector
import kotlin.Suppress
import kotlin.Unit

public inline
    fun ModelCollector.booleanFeatureItem(modelInitializer: BooleanFeatureItemBuilder.() -> Unit):
    Unit {
  add(
  BooleanFeatureItem_().apply {
    modelInitializer()
  }
  )
}

public inline
    fun ModelCollector.enumFeatureItem(modelInitializer: EnumFeatureItemBuilder.() -> Unit): Unit {
  add(
  EnumFeatureItem_().apply {
    modelInitializer()
  }
  )
}
