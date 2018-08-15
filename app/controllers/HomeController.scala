package controllers

import javax.inject._
import models.BookForm
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  val bookForm: Form[BookForm] = Form {
    mapping(
      "title" -> nonEmptyText,
      "author" -> nonEmptyText,
      "kind"-> nonEmptyText
    )(BookForm.apply)(BookForm.unapply)
  }

  def index = Action { implicit request =>
    Ok(views.html.index(bookForm))
  }

}
