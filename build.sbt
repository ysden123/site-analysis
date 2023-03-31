ThisBuild / scalaVersion := "3.2.2"
ThisBuild / version := "0.1.0"
ThisBuild / organization := "com.stulsoft"
ThisBuild / organizationName := "stulsoft"

lazy val root = (project in file("."))
  .settings(
    name := "site-analysis",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.4.6",
    libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "3.0.0",

    libraryDependencies += "org.scalatest" %% "scalatest-flatspec" % "3.2.15" % Test
  )