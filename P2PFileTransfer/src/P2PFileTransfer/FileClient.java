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


public class FileClient{
  public static void main (String [] args )
  {
      int flag = 0;

        try
        {
            int filesize;
            long start = System.currentTimeMillis();
            int bytesRead;
            int current = 0;
            // localhost for testing
            Socket sock = new Socket("127.0.0.1", 13267);
            System.out.println("Connecting...");
            // receive file
            
            InputStream is = sock.getInputStream();
            FileOutputStream fos = new FileOutputStream("source-copy.png");
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            DataInputStream dis = new DataInputStream(is);
            String sentence = dis.readUTF();
            System.out.println(sentence);
            filesize = Integer.parseInt(sentence);
            System.out.println(filesize);

            byte[] mybytearray = new byte[filesize+1];

            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;

            do
            {
                bytesRead = is.read(mybytearray, current, mybytearray.length - current);
                if (bytesRead >= 0)
                {
                    current += bytesRead;
                    flag = current;
                }
            } while (bytesRead > -1);
            bos.write(mybytearray, 0, current);
            bos.flush();
            File test = new File ("source-copy.png");
            System.out.println(test.length()+"");
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            bos.close();
            sock.close();
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            System.out.println(flag);

        }
  }
}
