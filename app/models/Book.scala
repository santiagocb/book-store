package models

case class Book(isbn: String, title: String, author: String, kind: String)

case class BookForm(isbn: String, title: String, author: String, kind: String)