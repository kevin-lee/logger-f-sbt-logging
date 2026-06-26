logLevel := sbt.Level.Warn

addDependencyTreePlugin

addSbtPlugin("com.github.sbt"  % "sbt-ci-release"  % "1.11.2")
addSbtPlugin("ch.epfl.scala"   % "sbt-scalafix"    % "0.14.5")
addSbtPlugin("org.scalameta"   % "sbt-scalafmt"    % "2.5.6")
addSbtPlugin("org.scoverage"   % "sbt-scoverage"   % "2.4.4")

val sbtDevOopsVersion = "3.3.2"
addSbtPlugin("io.kevinlee" % "sbt-devoops-scala"     % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-sbt-extra" % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-github"    % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-starter"   % sbtDevOopsVersion)
