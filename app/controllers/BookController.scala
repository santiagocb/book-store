package controllers

import dsl.BookRepository
import javax.inject.{Inject, Singleton}
import models.{Book, BookForm}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class BookController @Inject()(repo: BookRepository) extends Controller {

  val bookForm: Form[BookForm] = Form {
    mapping(
      "title" -> nonEmptyText,
      "author" -> nonEmptyText,
      "kind" -> nonEmptyText
    )(BookForm.apply)(BookForm.unapply)
  }

  def index = Action { implicit request =>
    Ok(views.html.index(bookForm))
  }

  def addBook = Action.async { implicit request =>
    bookForm.bindFromRequest.fold(
      _ => {
        Future.successful(Ok("failed"))
      },
      book => {
        repo.add(Book(99, book.title, book.author, book.kind))
          .map(ok => Ok(ok))
      }
    )
  }

  def book = Action { request =>
    request.
  }
}
