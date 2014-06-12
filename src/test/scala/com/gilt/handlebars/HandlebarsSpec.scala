package com.gilt.handlebars

import org.scalatest.{ FunSpec, Matchers }
import com.gilt.handlebars.binding.dynamic._

class HandlebarsSpec extends FunSpec with Matchers {
  describe("Issue #36 - Handlebars creation with empty string") {
    val hbs = Handlebars("")
  }
}
