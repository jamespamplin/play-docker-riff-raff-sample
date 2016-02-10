import com.typesafe.sbt.packager.docker._

name := """play-docker-riff-raff-sample"""

organization := "com.gu"

maintainer := "James Pamplin <james.pamplin@guardian.co.uk>"

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, SbtNativePackager, DockerPlugin, BuildInfoPlugin)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq()

routesGenerator := InjectedRoutesGenerator


// Docker packager config
dockerExposedPorts := Seq(9000)

defaultLinuxInstallLocation in Docker := "/opt/app"

dockerRepository := Some("702972749545.dkr.ecr.us-east-1.amazonaws.com")

dockerCommands := Seq(
  Cmd("FROM", "alpine:3.3"),
  Cmd("MAINTAINER", maintainer.value),
  Cmd("RUN", "apk add --update bash openjdk8-jre && rm /var/cache/apk/*"),
  Cmd("WORKDIR", (defaultLinuxInstallLocation in Docker).value),
  Cmd("EXPOSE", dockerExposedPorts.value.mkString(" ")),
  Cmd("ADD", "opt /opt"),
  ExecCmd("ENTRYPOINT", dockerEntrypoint.value: _*)
)


// Build info
buildInfoKeys := Seq[BuildInfoKey](
  name,
  BuildInfoKey.constant("gitCommitId", gitCommitId),
  BuildInfoKey.constant("buildNumber", sys.env.getOrElse("CIRCLE_BUILD_NUM", "DEV"))
)

buildInfoOptions += BuildInfoOption.ToMap

def gitCommitId = {
  import scala.util.Try

  sys.env.get("CIRCLE_SHA1")
    .orElse(Try("git rev-parse HEAD".!!.trim).toOption)
    .getOrElse("unknown")
}
