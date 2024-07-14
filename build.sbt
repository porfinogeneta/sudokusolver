val scala3Version = "3.4.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "sudokusolver",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "com.lihaoyi" %% "pprint" % "0.9.0",
      "org.typelevel" %% "cats-core" % "2.12.0"
    ),

    mainClass in (Compile, run) := Some("solver")
  )
