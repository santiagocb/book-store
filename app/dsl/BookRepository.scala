package dsl

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import models.Book

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BookRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext){
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._       //Trae la db a esta clase, para permitir hacer los métodos de la db
  import driver.api._     //Trae el Slick DSL acá, permite definir la tabla y otros queries

  private class BookTableDef(tag: Tag) extends Table[Book](tag, "book") {
    def isbn = column[Int]("isbn", O.PrimaryKey, O.AutoInc)
    def title = column[String]("title")
    def author = column[String]("author")
    def kind = column[String]("king")

    def * = (isbn, title, author, kind) <> ((Book.apply _).tupled, Book.unapply)
  }

  /*Este objeto TableQuery mapeará la tabla,
  todos los queries se harán a través de este objeto
   */

  private val books = TableQuery[BookTableDef]

  def add(book: Book): Future[String] = {
    dbConfig.db.run(books += book).map(res => "well done").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }



}


//val books = TableQuery[BookT]
