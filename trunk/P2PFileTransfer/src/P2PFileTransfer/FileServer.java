/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package P2PFileTransfer;

/**
 *
 * @author Julian
 */
import java.net.*;
import java.io.*;

public class FileServer {
  public static void main (String [] args ) throws IOException {
    // create socket
    ServerSocket servsock = new ServerSocket(13267);
    while (true) {
      System.out.println("Waiting...");

      Socket sock = servsock.accept();
      System.out.println("Accepted connection : " + sock);

      // sendfile
      File myFile = new File ("./data/logo1.png");
      byte [] mybytearray  = new byte [(int)myFile.length()];
      FileInputStream fis = new FileInputStream(myFile);
      BufferedInputStream bis = new BufferedInputStream(fis);
      bis.read(mybytearray,0,mybytearray.length);
      OutputStream os = sock.getOutputStream();
      System.out.println("Sending...");
      String sentence = ""+(int)myFile.length();
      DataOutputStream dos = new DataOutputStream(os);
      dos.writeUTF(sentence);
      os.write(mybytearray,0,mybytearray.length);
      os.flush();
      sock.close();
      }
    }
}

