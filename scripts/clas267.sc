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



val naboString = "urn:cite2:chron:epoch:ptol1"

def getCatalog = {
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
  catalog
}


def synchronisms(catalog: Map[String,String]): ChronologicalGraph = {
  var csv = StringBuilder.newBuilder
  val csvDir = new File("time-datasets/")
  val filesList = csvDir.listFiles.filter(_.isFile).toVector.filter(_.toString.contains(".csv"))
  for (f <- filesList) {
    val txt = Source.fromFile(f.toString).getLines.toVector.drop(1)
    csv.append(txt.mkString("\n"))
    csv.append("\n")
  }

  val chron = GraphFactory.fromCsv(csv.toString, catalog)
  chron
}


def pathToEpoch(evtString: String) = {
  val catalog = getCatalog
  val chron = synchronisms(catalog)
  val interval = chron.sumInterval(evtString,naboString, true)


  val nabo = chron.findEvtById(naboString)
  val evt = chron.findEvtById(evtString)


  println("\n\n")
  println("Interval from " + evt + " to " + nabo)
  println("Total: " )
  for (k <- interval.keySet) {
    println(k + ": " + interval(k))
  }
}

def ptol1(evtString: String, fileName: String) = {
  val catalog = getCatalog
  val chron = synchronisms(catalog)

 val nabo = chron.findEvtById(naboString)
 val evt = chron.findEvtById(evtString)

 println("Plotting path from " + evt + " to " + nabo)
 DotWriter.writeDot(chron,evt,nabo,fileName)

 pathToEpoch(evtString)

}



def pt2pt(evt1String: String, evt2String: String, fileName: String) = {
  val catalog = getCatalog
  val chron = synchronisms(catalog)

   val evt1 = chron.findEvtById(evt1String)
   val evt2 = chron.findEvtById(evt2String)

   println("Plotting path from " + evt1 + " to " + evt2 + " using file " + fileName)
   DotWriter.writeDot(chron,evt1,evt2,fileName)
}

def single(projFile: String) = {
  val catalog = getCatalog

  val csv = Source.fromFile("time-datasets/" + projFile + ".csv").getLines.toVector.drop(1)
  val project = GraphFactory.fromCsv(csv.mkString("\n"), catalog)
  DotWriter.writeDot(project,"dots/" + projFile)
}
