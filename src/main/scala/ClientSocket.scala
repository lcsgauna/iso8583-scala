import java.net._
import java.io.{InputStreamReader,IOException}

class ClientSocket{
  def run(header: Array[Byte], isoMessage: Array[Byte]) {
    try {

      val serverPort: Int = 123456
      val host = InetAddress.getByName("localhost")
      println("\n=============================================================")
      println(s" Trying connect to a server ${host} on port: ${serverPort}")
      val socket = new Socket(host, serverPort)
      println(s" Connected to ${socket.getInetAddress()}")
      println("=============================================================")

      val toServer = socket.getOutputStream
      val fromServer = new BufferedReader (new InputStreamReader(socket.getInputStream()))

      toServer.write(header)
      toServer.write(isoMessage)
      toServer.flush()

      println(s" Sending message server: \n ${header.mkString}${isoMessage.mkString}")
      print("==========================================================")

      val line = fromServer.read()
      print("\n Client received: " + line + " from Server\n")

      toServer.close()
      fromServer.close()
      socket.close()

    }catch {
      case ex: UnknownHostException => println(ex)
      case e: IOException => println(e)
    }
  }
}

