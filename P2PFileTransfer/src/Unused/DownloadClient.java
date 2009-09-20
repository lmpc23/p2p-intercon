/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Unused;

import java.io.*;
import java.net.*;

/**
 *
 * @author Julian
 */
public class DownloadClient
{

    public static void main(String[] args)
    {
        try
        {
            long filesize;
            long start = System.currentTimeMillis();
            int bytesRead;
            int current = 0;
            // localhost for testing
            
            Socket clientSocket = new Socket("localhost", 6789);

            System.out.println("Connecting...");

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            outToServer.writeBytes("/test/testTest.java");
            //System.out.println("Message Sent...");

            String filesizeS = inFromServer.readLine();
            System.out.println(filesizeS+"MUEEEEEEERE");
            /*filesize = Long.parseLong(filesizeS);
            System.out.println(filesize+"");
            int sizeAprox = (int)filesize;
            System.out.println(sizeAprox+"");

            // receive file
            byte[] mybytearray = new byte[sizeAprox];

            outToServer.writeBytes("OK");

            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) {
                    current += bytesRead;
                }
            } while (bytesRead > -1);

            FileOutputStream fos = new FileOutputStream("test.java");
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            bos.write(mybytearray, 0, current);
            bos.flush();
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            bos.close();
            clientSocket.close();

        */} catch (Exception e) {
            System.out.println(e.getMessage()+"MUERE!!");
            e.printStackTrace();
        }
    }

}
