ThisBuild / scalaVersion := "3.2.2"
ThisBuild / version := "0.0.1"
ThisBuild / organization := "com.stulsoft"
ThisBuild / organizationName := "stulsoft"

lazy val root = (project in file("."))
  .settings(
    name := "site-analysis",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.4.6",
    libraryDependencies += "org.apache.httpcomponents.client5" % "httpclient5" % "5.2.1",

    libraryDependencies += "org.scalatest" %% "scalatest-flatspec" % "3.2.15" % Test
  )