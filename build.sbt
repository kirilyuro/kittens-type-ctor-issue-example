name := "kittens-type-ctor-issue-example"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= List(
  "org.typelevel" %% "cats-core" % "2.1.1",
  "org.typelevel" %% "kittens" % "2.1.0",
  "io.scalaland" %% "catnip" % "1.1.1"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross sbt.CrossVersion.patch)
