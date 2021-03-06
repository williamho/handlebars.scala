package com.gilt.handlebars.helper

import com.gilt.handlebars.logging.Loggable
import com.gilt.handlebars.binding.Binding
import com.gilt.handlebars.binding.BindingFactory

/**
 * with Helper
 *
 * Usage:
 *
 * {{with withContext}} {{!body}} {{/with}}
 *
 * Any mustache will be with respect to withContext not the context in which the helper was called.
 */
class WithHelper[T] extends Helper[T] with Loggable {
  def apply(binding: Binding[T], options: HelperOptions[T])(implicit c: BindingFactory[T]): String = {
    val arg = options.argument(0) 
    if (!arg.isDefined) {
      warn("No context provided for with helper")
      ""
    } else {
      options.visit(arg)
    }
  }
}
