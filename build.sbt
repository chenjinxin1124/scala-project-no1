name := "scala-project"
version := "0.1"
scalaVersion := "2.13.0"
lazy val akkaVersion = "2.6.0-M4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.9"
  , "com.typesafe.akka" %% "akka-stream" % "2.5.23" // or whatever the latest version is
  , "com.typesafe.akka" %% "akka-actor" % "2.5.23"
  , "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.9"
)

enablePlugins(JavaAppPackaging)