package controllers

import dsl.BookRepository
import javax.inject.{Inject, Singleton}
import models.{Book, BookForm}
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class BookController @Inject()(repo: BookRepository, val messagesApi: MessagesApi) extends Controller with I18nSupport{

  val bookForm: Form[BookForm] = Form {
    mapping(
      "isbn" -> nonEmptyText,
      "title" -> nonEmptyText,
      "author" -> nonEmptyText,
      "kind" -> nonEmptyText
    )(BookForm.apply)(BookForm.unapply)
  }

  def index = Action.async {
    _ => repo.getBooks.map(books => Ok(views.html.books.index(books)))
  }

  def create = Action {
    implicit request => Ok(views.html.books.create(bookForm))
  }

  def addBook = Action.async { implicit request =>
    bookForm.bindFromRequest.fold(
      _ => {
        Future.successful(Ok("failed"))
      },
      book => {
        repo.add(Book(book.isbn, book.title, book.author, book.kind))
          .map(_ => Redirect(routes.BookController.index()))
      }
    )
  }

  def showBook(id: Integer) = TODO
  def editBook(id: Integer) = TODO

}
