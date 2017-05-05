/*

Reads all files named *.csv in time-datasets directory, and writes a
graph image to the dots directory.

*/


import scalax.collection.Graph
import scalax.collection.edge.LDiEdge
import scalax.collection.GraphPredef._

import edu.holycross.shot.chrongraph._

import java.io.File
import scala.io.Source


val catalogDir =  new File("eventcatalogs/")
val catalogList = catalogDir.listFiles.filter(_.isFile).toVector.filter(_.toString.contains(".csv"))
var catalog = scala.collection.immutable.Map[String,String]()

for (f <- catalogList) yield {
  println("Adding catalog data from " + f)
  val lns = Source.fromFile(f.toString).getLines.toVector.drop(1)
  for (l <- lns) {
    val pr = l.split(",")
    catalog += (pr(0) -> pr(1))
  }
}


val srcDir =  "time-datasets/"
val dir = new File(srcDir)
val filesList = dir.listFiles.filter(_.isFile).toVector.filter(_.toString.contains(".csv"))

for (f <- filesList) {
  val fileBase = f.toString.replaceAll(".csv","").replaceAll("time-datasets/","")
  println("Graphing data from " + fileBase)
  var csv = StringBuilder.newBuilder
  val txt = Source.fromFile(f.toString).getLines.toVector.drop(1)
  csv.append(txt.mkString("\n"))
  csv.append("\n")

  try {
    val chron = GraphFactory.fromCsv(csv.toString, catalog)
    DotWriter.writeDot(chron,"dots/" + fileBase)
  } catch {
    case e: Throwable => {
      println("could not make graph from " + f)
      println(e.getMessage())
    }
  }
}
