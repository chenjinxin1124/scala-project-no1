name := "scala-project"

val akkaV         = "2.5.23"
val akkaHttpV     = "10.1.9"
val alpakkaKafkaV = "1.0.5"
val slickV        = "3.3.2"
val catsV         = "2.0.0-M4"
val circeV        = "0.12.0-M4"
val prometheusV   = "0.6.0"
val quillV        = "3.4.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka"              %% "akka-slf4j"                 % akkaV
  , "com.typesafe.akka"            %% "akka-actor"                 % akkaV
  , "com.typesafe.akka"            %% "akka-stream"                % akkaV
  , "com.typesafe.akka"            %% "akka-cluster"               % akkaV
  , "com.typesafe.akka"            %% "akka-cluster-tools"         % akkaV
  , "com.typesafe.akka"            %% "akka-cluster-metrics"       % akkaV
  , "com.typesafe.akka"            %% "akka-remote"                % akkaV
  , "com.typesafe.akka"            %% "akka-persistence"           % akkaV
  , "com.typesafe.akka"            %% "akka-http-core"             % akkaHttpV
  , "com.typesafe.akka"            %% "akka-http"                  % akkaHttpV
  , "com.typesafe.akka"            %% "akka-stream-kafka"          % alpakkaKafkaV
  , "io.prometheus"                %  "simpleclient"               % prometheusV
  , "io.prometheus"                %  "simpleclient_hotspot"       % prometheusV
  , "io.prometheus"                %  "simpleclient_common"        % prometheusV
  , "io.prometheus"                %  "simpleclient_pushgateway"   % prometheusV
  , "ch.qos.logback"               %  "logback-classic"            % "1.2.3"
  , "org.slf4j"                    %  "log4j-over-slf4j"           % "1.7.25"
  , "org.typelevel"                %% "cats-core"                  % catsV
  , "org.typelevel"                %% "cats-free"                  % catsV
  , "io.circe"                     %% "circe-core"                 % circeV
  , "io.circe"                     %% "circe-generic"              % circeV
  , "io.circe"                     %% "circe-parser"               % circeV
  , "io.circe"                     %% "circe-generic-extras"       % circeV
  , "com.typesafe.slick"           %% "slick"                      % slickV
  , "com.typesafe.slick"           %% "slick-hikaricp"             % slickV
  , "org.json4s"                   %% "json4s-jackson"             % "3.5.2"
  , "com.typesafe.scala-logging"   %% "scala-logging"              % "3.7.2"
  , "org.mariadb.jdbc"             %  "mariadb-java-client"        % "1.3.7"
  // , "com.github.blemale"           %% "scaffeine"                  % "2.2.0"
  , "com.github.etaty"             %% "rediscala"                  % "1.8.0"
  , "redis.clients"                %  "jedis"                      % "2.9.0"
  // , "com.google.guava"             %  "guava"                      % "18.0"
  , "org.apache.thrift"            %  "libthrift"                  % "0.10.0"
  // , "io.netty"                     % "netty-tcnative"              % "2.0.14.Final" % "_linux_x86_64"//"${os.detected.classifier}"
  , "com.aerospike"                %  "aerospike-client"           % "4.2.3"
  , "org.postgresql"               %  "postgresql"                 % "42.2.6"
  // , "com.twitter"                  %% "chill-akka"                 % "0.9.3"
  , "com.pauldijou"                %% "jwt-circe"                  % "3.1.0"
  , "io.getquill"                  %% "quill-cassandra"            % quillV
  , "com.ag"                       %% "akka-node-discovery-consul" % "0.0.4"
).map(_.excludeAll(
  ExclusionRule("org.log4j")
  , ExclusionRule("org.slf4j", "slf4j-simple")
))

dependencyOverrides ++= Seq(
  "org.codehaus.plexus" % "plexus-utils" % "3.0.17"
  , "com.google.guava" % "guava" % "20.0"
  // , "io.netty" % "netty-tcnative-boringssl-static" % "2.0.14.Final"
)

enablePlugins(JavaAppPackaging)