import play.api.libs.json._
import scala.collection.immutable.ListMap

object GenerateISOMessage extends App {
  val iso = new IsoUtils
  val client = new ClientSocket

  val json = Json.parse("{ message8583 here bro :) }").as[Map[String, String]]

  val jsonMap: Map[Int, String] = ListMap(json.map {case (k, v) => (k.toInt, v) }.toSeq.sortBy(_._1):_*)
  val jsonKeys = jsonMap.keys
  var values: String = "1"

  jsonKeys.last match {
    case keyLastValue if keyLastValue <= 64 => for (firstBitmap <- 2 to 64) values += iso.generateBitmap(jsonMap, firstBitmap)
    case keyLastValue if keyLastValue <= 128 => for (secondBitmap <- 2 to 128) values += iso.generateBitmap(jsonMap, secondBitmap)
    case keyLastValue if keyLastValue <= 192 => for (thirdBitmap <- 2  to 192) values += iso.generateBitmap(jsonMap, thirdBitmap)
  }

  val bitmap = values.replaceAll("(.{4})", "$1 ")

  val msg:String = s"${jsonMap(0)}${iso.bin2Hex(values)}${iso.selectValues(3,25,jsonMap)}${iso.string2Hex(iso.selectValues(40,125,jsonMap))} "

}
