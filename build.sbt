import java.nio.charset.StandardCharsets

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.0"


Global / onLoad := {
  val scalaVersionValue = (root / scalaVersion).value
  val outputFile        = baseDirectory.value / "scala-metadata.js"
  IO.writeLines(
    outputFile,
    s"""
       |const scalaVersion = "$scalaVersionValue"
       |
       |exports.scalaMetadata = {
       |  scalaVersion: scalaVersion
       |}
       |""".stripMargin.split("\n").toList,
    StandardCharsets.UTF_8
  )

  (Global / onLoad).value
}

val laminarVersion = "17.0.0"

lazy val root = (project in file(".")).enablePlugins(ScalaJSPlugin)
  .settings(
    name := "IndigoWeb",
    libraryDependencies ++= Seq(
      "io.indigoengine" %%% "indigo"            % "0.17.0",
      "io.indigoengine" %%% "indigo-extras"     % "0.17.0",
      "io.indigoengine" %%% "indigo-json-circe" % "0.17.0",
      "com.raquo" %%% "laminar" % laminarVersion
      ),
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.ESModule) },
    scalaJSUseMainModuleInitializer := true
  )
