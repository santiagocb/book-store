name := """book-store"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc4",
  "com.typesafe.play" %% "play-slick" % "2.0.2",
  "org.specs2" %% "specs2-junit" % "3.6.6",
  filters
)

