package models

case class Book(isbn: Int, title: String, author: String, kind: String)

case class BookForm(title: String, author: String, kind: String)