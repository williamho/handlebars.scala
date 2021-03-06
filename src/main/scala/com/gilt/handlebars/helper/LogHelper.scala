package com.gilt.handlebars.helper

import com.gilt.handlebars.logging.Loggable
import com.gilt.handlebars.binding.Binding
import com.gilt.handlebars.binding.BindingFactory

class LogHelper[T] extends Helper[T] with Loggable {
  def apply(binding: Binding[T], options: HelperOptions[T])(implicit c: BindingFactory[T]): String = {
    info(options.argument(0).render)
    ""
  }
}
