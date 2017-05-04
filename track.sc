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

var csv = StringBuilder.newBuilder
val csvDir = new File("time-datasets/")
val filesList = csvDir.listFiles.filter(_.isFile).toVector.filter(_.toString.contains(".csv"))
for (f <- filesList) {
  val txt = Source.fromFile(f.toString).getLines.toVector.drop(1)
  csv.append(txt.mkString("\n"))
  csv.append("\n")
}


val chron = GraphFactory.fromCsv(csv.toString, catalog)

def ptol1(evtString: String, fileName: String) = {
 val nabo = chron.findEvtById("urn:cite2:chron:epoch:ptol1")
 val evt = chron.findEvtById(evtString)
 println("Plotting path from " + evt + " to epoch of Nabonassar")
 DotWriter.writeDot(chron,evt,nabo,fileName)
}


def ptol2(evtString: String, fileName: String) = {
  val nabo = chron.findEvtById("urn:cite2:chron:epoch:ptol2")
  val evt = chron.findEvtById(evtString)
  println("Plotting path from " + evt + " to epoch of Philip")
  DotWriter.writeDot(chron,evt,nabo,fileName)
}
