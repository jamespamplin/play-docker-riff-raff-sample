// The Play plugin
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.6")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.6")

addSbtPlugin("com.gu" % "sbt-riffraff-artifact" % "0.8.3")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.5.0")
