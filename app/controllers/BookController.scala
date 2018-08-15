package controllers

import dsl.BookRepository
import javax.inject.{Inject, Singleton}
import models.Book
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class BookController @Inject()(repo: BookRepository) extends Controller {
  def addBook(title: String, author: String, kind: String) = Action.async(parse.json) { _ =>

    repo.add(Book(99, title, author, kind))
      .map(x => Ok(Json.obj("respuesta" -> x)))
      .recover {
        case e: Exception => InternalServerError(Json.obj("error" -> e.getMessage))
      }
  }
}
