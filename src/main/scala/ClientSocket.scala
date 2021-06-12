import java.net._
import java.io._

class ClientSocket {

  def run(header:Array[Byte],message:Array[Byte] ){
    try {
      val serverPort: Int = 12345
      val host = InetAddress.getByName("localhost")
      println("Trying to connect to server " + host + " on port: " + serverPort)

      val socket = new Socket(host, serverPort)
      println("Connected to "+socket.getRemoteSocketAddress)
      val toServer = new PrintWriter(socket.getOutputStream, true)
      val fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream))

      toServer.print(header)
      toServer.print(message)
      toServer.flush()
      println(s"\n Sending message server: \n ${header.mkString}${message.mkString} \n ")

      val line = fromServer.readLine()
      println("\n Client received: " + line + " from Server")

      toServer.close()
      fromServer.close()
      socket.close()
    } catch {
      case ex: UnknownHostException => println(ex)
      case e: IOException => println(e)
    }
  }
}

