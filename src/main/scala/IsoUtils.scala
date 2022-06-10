import java.nio.ByteBuffer

class IsoUtils {

  def string2Hex(str: String): String = str.toList.map(_.toInt.toHexString).mkString.toUpperCase
  //def hex2string(hex: String): String = hex.sliding(2, 2).toArray.map(Integer.parseInt(_, 16).toChar).mkString.toUpperCase
  //def hex2Bin(hex: String): String = BigInt(hex, 16).toString(2).mkString.toUpperCase
  def bin2Hex(bin: String): String = BigInt(bin, 2).toString(16).mkString.toUpperCase
  def generateIsoMessage2Bytes(message:String):Array[Byte]= BigInt(message,16).toByteArray
  def convert2Bytes (messagePack:String):Array[Byte] = BigInt(messagePack,16).toByteArray
  def formatterNumbers(value:String):String = "0"*(4-value.length)+value
  def formatterLastNumbers(value:String)= "0"*(2-value.length)+value

  def generateBitmap(json: Map[Int, String], dataElement: Int): String =
    dataElement match {
      case value if json.contains(value) => "1"
      case value if !json.contains(value) => "0"
    }

  def selectValues(index: Int, last: Int, json: Map[Int, String]): String = {
    var values: String = ""
    for (value <- index to last if json.contains(value)) values += json(k)
    values
  }

  def selectValuesHex(index:Int,last:Int,json:Map[Int,String]):String={
    var values = ""
    for(value <- index to last if json.contains(value)) values += json(value)
    values
  }

  def selectValuesSizeAndHex(index: Int, last: Int, json: Map[Int, String]): String = {
    var values: String = ""
    for (value <- index to last) {
      if (json.contains(value)) {
        if (json(value) != json.values.last) values += s"${formatterNumbers(json(value).length.toString)}${string2Hex(json(value))}"
        else values += s"${formatterLastNumbers(json(value).length.toString)}${string2Hex(json(value))}"
      }
    }
    values
  }

  def  generateHeader2Bytes(array: Array[Byte]): Array[Byte] ={
    ByteBuffer.allocate(2).putShort(array.length.toShort).array()
  }

}
