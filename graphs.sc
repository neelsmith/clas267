/*

Reads all files named *.csv in time-datasets directory, and writes a
graph image to the dots directory.

*/


import scalax.collection.Graph
import scalax.collection.edge.LDiEdge
import scalax.collection.GraphPredef._

import edu.holycross.shot.chrongraph._

import java.io.File

val srcDir =  "time-datasets/"
val dir = new File(srcDir)
val filesList = dir.listFiles.filter(_.isFile).toVector

for (f <- filesList) {
  val fileBase = f.toString.replaceAll(".csv","").replaceAll("time-datasets/","")
  try {
   val chron = GraphSource(srcDir + fileBase + ".csv")
   if (chron.graph.size > 1){
    DotWriter.writeDot(chron,"dots/" + fileBase)
   } else {
     println("only read " + chron.graph.size + "  rows.")
   }
  } catch {
    case e: Throwable => {
      println("could not make graph.")
      println(e.getMessage())
    }
  }
}
