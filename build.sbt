
scalaVersion := "2.12.1"

resolvers += Resolver.jcenterRepo
resolvers += Resolver.bintrayRepo("neelsmith","maven")

libraryDependencies ++=   Seq(
  "edu.holycross.shot" %% "chrongraph" % "0.1.1"
)
