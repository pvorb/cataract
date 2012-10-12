name := "cataract"

organization := "de.vorb"

version := "0.0.0-SNAPSHOT"

scalaVersion := "2.9.2"


// Dependencies
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.3"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test"
