import play.api.libs.json._
import scala.collection.immutable.ListMap

object GenerateISOMessage extends App {
  val iso = new IsoUtils
  val clientSocket = new ClientSocket

  val json = Json.parse("{ message8583 in json here bro :D }").as[Map[String, String]]

  val jsonMap: Map[Int, String] = ListMap(json.map {case (k, v) => (k.toInt, v) }.toSeq.sortBy(_._1):_*)
  val jsonKeys = jsonMap.keys
  var values: String = "1"

  jsonKeys.last match {
    case keyLastValue if keyLastValue <= 64 => for (firstBitmap <- 2 to 64) values += iso.generateBitmap(jsonMap, firstBitmap)
    case keyLastValue if keyLastValue <= 128 => for (secondBitmap <- 2 to 128) values += iso.generateBitmap(jsonMap, secondBitmap)
    case keyLastValue if keyLastValue <= 192 => for (thirdBitmap <- 2  to 192) values += iso.generateBitmap(jsonMap, thirdBitmap)
  }

  val bitmap = values.replaceAll("(.{4})", "$1 ")

  val isoMessage:String = s"${jsonMap(0)}${iso.bin2Hex(bitmap)}${iso.selectValues(1,27,jsonMap)}${iso.selectValuesHex(28,45,jsonMap)}${iso.selectValuesSizeAndHex(46,128,jsonMap)} "

  val messageBytes = iso.convert2Bytes(isoMessage)
  val headerBytes = iso.generateHeader2Bytes(messageBytes)

  clientSocket.run(headerBytes,messageBytes)

}
