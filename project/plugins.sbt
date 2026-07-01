logLevel := sbt.Level.Warn

addDependencyTreePlugin

addSbtPlugin("com.github.sbt"  % "sbt-ci-release"  % "1.11.2")

addSbtPlugin("com.eed3si9n" % "sbt-salad-days" % "0.2.0")

addSbtPlugin("ch.epfl.scala"   % "sbt-scalafix"    % "0.14.5")

addSbtPlugin("org.scalameta"   % "sbt-scalafmt"    % "2.5.6")

addSbtPlugin("org.scoverage"   % "sbt-scoverage"   % "2.4.4")

addSbtPlugin("org.typelevel" % "sbt-tpolecat" % "0.5.7")

val sbtDevOopsVersion = "3.5.1"
addSbtPlugin("io.kevinlee" % "sbt-devoops-scala"     % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-sbt-extra" % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-github"    % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" % "sbt-devoops-starter"   % sbtDevOopsVersion)
