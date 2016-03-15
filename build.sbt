name := """metrics-test"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test,
  //"com.kenshoo" %% "metrics-play" % "2.3.0_0.1.8"
  "com.kenshoo" %% "metrics-play" % "2.4.0_0.3.0",
  "com.codahale.metrics" % "metrics-graphite" % "3.0.2"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
