import com.typesafe.sbt.packager.docker._

name := """play-docker-riff-raff-sample"""

organization := "com.gu"

maintainer := "James Pamplin <james.pamplin@guardian.co.uk>"

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, SbtNativePackager, DockerPlugin)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq()

routesGenerator := InjectedRoutesGenerator

dockerExposedPorts := Seq(9000)

defaultLinuxInstallLocation in Docker := "/opt/app"

dockerCommands := Seq(
  Cmd("FROM", "alpine:3.3"),
  Cmd("MAINTAINER", maintainer.value),
  Cmd("RUN", "apk add --update bash openjdk8-jre && rm /var/cache/apk/*"),
  Cmd("WORKDIR", (defaultLinuxInstallLocation in Docker).value),
  Cmd("EXPOSE", dockerExposedPorts.value.mkString(" ")),
  Cmd("ADD", "opt /opt"),
  ExecCmd("ENTRYPOINT", dockerEntrypoint.value: _*)
)
