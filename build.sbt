name := "ELOBackend"

version := "1.0"

scalaVersion := "2.11.8"


libraryDependencies ++= {
  val akkaV         = "2.4.2"
  val logbackV      = "1.1.6"
  val scalaLoggingV = "2.1.2"
  val scalaTestV    = "2.2.5"
  Seq(
    "ch.qos.logback"              %  "logback-classic"                    % logbackV,
    "com.typesafe.scala-logging"  %% "scala-logging-slf4j"                % scalaLoggingV,
    "com.typesafe.akka"           %% "akka-actor"                         % akkaV,
    "com.typesafe.akka"           %% "akka-slf4j"                         % akkaV,
    "com.typesafe.akka"           %% "akka-stream"                        % akkaV,
    "com.typesafe.akka"           %% "akka-http-experimental"             % akkaV,
    "com.typesafe.akka"           %% "akka-http-spray-json-experimental"  % akkaV,
    "com.typesafe.akka"           %% "akka-http-testkit"                  % akkaV,
    "org.scalatest"               %% "scalatest"                          % scalaTestV % "test",
    "com.github.fommil"           %% "spray-json-shapeless"               % "1.2.0"
  )
}