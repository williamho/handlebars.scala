package com.gilt.handlebars

import com.gilt.handlebars.parser.{ ProgramHelper, HandlebarsGrammar, Program }
import com.gilt.handlebars.helper.Helper
import java.io.File
import scala.io.Source
import com.gilt.handlebars.partial.PartialHelper
import com.gilt.handlebars.logging.Loggable
import com.gilt.handlebars.binding.BindingFactory

trait HandlebarsBuilder[T] {
  def program: Program
  def partials: Map[String, Handlebars[T]]
  def helpers: Map[String, Helper[T]]

  def withPartials(items: Map[String, Handlebars[T]]): HandlebarsBuilder[T]
  def withHelpers(items: Map[String, Helper[T]]): HandlebarsBuilder[T]

  def build: Handlebars[T]
}

case class DefaultHandlebarsBuilder[T](
  program: Program,
  partials: Map[String, Handlebars[T]] = Map.empty[String, Handlebars[T]],
  helpers: Map[String, Helper[T]] = Map.empty[String, Helper[T]])(implicit contextFactory: BindingFactory[T])
    extends HandlebarsBuilder[T] {
  
  def withPartials(items: Map[String, Handlebars[T]]) = copy(partials = partials ++ items)

  def withHelpers(items: Map[String, Helper[T]]) = copy(helpers = helpers ++ items)

  def build: Handlebars[T] = new HandlebarsImpl(program, partials, helpers ++ Helper.defaultHelpers.asInstanceOf[Map[String,Helper[T]]])
}

object DefaultHandlebarsBuilder extends ProgramHelper with Loggable {
  def apply[T](template: String)(implicit contextFactory: BindingFactory[T]): DefaultHandlebarsBuilder[T] = {
    DefaultHandlebarsBuilder(programFromString(template))
  }

  def apply[T](file: File)(implicit contextFactory: BindingFactory[T]): DefaultHandlebarsBuilder[T] = {
    val partials = PartialHelper.getTemplates(file)
    new DefaultHandlebarsBuilder(programFromFile(file), partials)
  }
}