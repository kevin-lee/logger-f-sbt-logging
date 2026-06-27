import ProjectInfo.*
import just.semver.SemVer
import kevinlee.sbt.SbtCommon.crossVersionProps

ThisBuild / scalaVersion := props.ProjectScalaVersion
ThisBuild / organization := "io.kevinlee"
ThisBuild / organizationName := "Kevin's Code"
ThisBuild / crossScalaVersions := props.CrossScalaVersions

ThisBuild / developers := List(
  Developer(
    props.GitHubUsername,
    "Kevin Lee",
    "kevin.code@kevinlee.io",
    url(s"https://github.com/${props.GitHubUsername}"),
  )
)
ThisBuild / homepage := url(s"https://github.com/${props.GitHubUsername}/${props.RepoName}").some
ThisBuild / scmInfo :=
  ScmInfo(
    browseUrl = url(s"https://github.com/${props.GitHubUsername}/${props.RepoName}"),
    connection = s"scm:git:git@github.com:${props.GitHubUsername}/${props.RepoName}.git",
  ).some

ThisBuild / licenses := props.licenses

ThisBuild / scalafixConfig := (
  if (scalaVersion.value.startsWith("3"))
    ((ThisBuild / baseDirectory).value / ".scalafix-scala3.conf").some
  else
    ((ThisBuild / baseDirectory).value / ".scalafix-scala2.conf").some
)

lazy val loggerFSbtLogging = (project in file("."))
  .enablePlugins(DevOopsGitHubReleasePlugin)
  .settings(
    name := prefixedProjectName("sbt-logging"),
    description := "Logger for F[_] - Logger with sbt logging",
    libraryDependencies := libraryDependenciesRemoveScala3Incompatible(
      scalaVersion.value,
      libraryDependencies.value,
    )
    /* GitHub Release { */,
    devOopsPackagedArtifacts := List(s"*/*/*/target/scala-*/${devOopsArtifactNamePrefix.value}*.jar"),
    /* } GitHub Release */
  )
  .settings(noPublish)
  .aggregate(
    sbtLogging,
  )

lazy val sbtLogging =
  module(ProjectName("sbt-logging"))
    .settings(
      description := "Logger for F[_] - Logger with sbt logging",
      libraryDependencies ++= crossVersionProps(
        List.empty,
        SemVer.parseUnsafe(scalaVersion.value),
      ) {
        case (SemVer.Major(2), SemVer.Minor(11), _) =>
          List(
            libs.sbtLoggingLib("1.2.4")
          )

        case (SemVer.Major(2), SemVer.Minor(12), _) =>
          List(
            libs.sbtLoggingLib("1.5.8")
          )

        case (SemVer.Major(2), SemVer.Minor(13), _) =>
          List(
            libs.sbtLoggingLib("1.11.7")
          )

        case (SemVer.Major(3), SemVer.Minor(_), _) =>
          List(
            libs.sbtLoggingLib("2.0.0")
          )
      },
      libraryDependencies ++= List(
        libs.loggerFCore
      ),
      libraryDependencies := libraryDependenciesRemoveScala3Incompatible(
        scalaVersion.value,
        libraryDependencies.value,
      ),
    )

lazy val props =
  new {

    final val GitHubUsername = "kevin-lee"
    final val RepoName       = "logger-f-sbt-logging"

    val ParentProjectName = "logger-f"

    val Scala3Versions = List("3.8.4")
    val Scala2Versions = List("2.13.18", "2.12.18")

//    final val ProjectScalaVersion = Scala3Versions.head
    final val ProjectScalaVersion = Scala2Versions.head

    lazy val licenses = List(License.MIT)

    val removeDottyIncompatible: ModuleID => Boolean =
      m =>
        m.name == "ammonite" ||
          m.name == "kind-projector" ||
          m.name == "better-monadic-for" ||
          m.name == "mdoc"

    val CrossScalaVersions = (Scala3Versions ++ Scala2Versions).distinct

    val CrossScalaVersionsForScalaJsAndNative = CrossScalaVersions.filterNot(_.startsWith("2.12"))

    final val IncludeTest = "compile->compile;test->test"

    val LoggerFVersion = "2.11.0"

  }

lazy val libs =
  new {

    def sbtLoggingLib(sbtLoggingVersion: String) = "org.scala-sbt" %% "util-logging" % sbtLoggingVersion

    lazy val loggerFCore = "io.kevinlee" %% "logger-f-core" % props.LoggerFVersion

  }

// scalafmt: off
def prefixedProjectName(name: String) = s"${props.ParentProjectName}${if (name.isEmpty) "" else s"-$name"}"
// scalafmt: on

def libraryDependenciesRemoveScala3Incompatible(
  scalaVersion: String,
  libraries: Seq[ModuleID],
): Seq[ModuleID] =
  (
    if (scalaVersion.startsWith("3."))
      libraries
        .filterNot(props.removeDottyIncompatible)
    else
      libraries
  )

def module(projectName: ProjectName): Project = {
  val prefixedName = prefixedProjectName(projectName.projectName)
  projectCommonSettings(prefixedName, projectName.some)
}

def projectCommonSettings(projectName: String, projectId: Option[ProjectName]): Project = {
  Project(projectId.fold(projectName)(_.projectName), file(s"modules/$projectName"))
    .settings(
      name := projectName,
      licenses := props.licenses,
      scalafixConfig := (
        if (scalaVersion.value.startsWith("3"))
          ((ThisBuild / baseDirectory).value / ".scalafix-scala3.conf").some
        else
          ((ThisBuild / baseDirectory).value / ".scalafix-scala2.conf").some
      ),
      /* Coveralls { */
      coverageHighlighting := (CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, 10)) | Some((2, 11)) =>
          false
        case _ =>
          true
      }),
      /* } Coveralls */
    )
}
