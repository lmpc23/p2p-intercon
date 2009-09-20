/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Unused;

import java.net.*;
import java.io.*;

/**
 *
 * @author Julian
 */
public class DownloadServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        String clientSentence;
        ServerSocket receiver = new ServerSocket(6789);

        while (true)
        {
            Socket connection = receiver.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connection.getOutputStream());

            clientSentence = inFromClient.readLine();
            System.out.println(clientSentence);

            if (clientSentence.startsWith("/"))
            {
                System.out.println("ACA LLEGa");
                File archivo = new File("."+clientSentence);
                FileInputStream fis = new FileInputStream(archivo);
                BufferedInputStream in = new BufferedInputStream(fis);

                //Sends size to client, so the client is ready.

                System.out.println(archivo.length()+"ACA MUERE");
                outToClient.writeBytes(archivo.length()+"");

                //Waits for confirmation
                clientSentence = inFromClient.readLine();
                
                if(clientSentence.toUpperCase().equals("OK"))
                {

                    //Writes the file.
                    int i;
                    while ((i = in.read()) != -1)
                    {
                        outToClient.write(i);
                    }

                    outToClient.flush();
                    outToClient.close();
                    in.close();
                    connection.close();
                }
                
            }
            else
            {
                System.out.println("MUERE!!!");

            }

        }



        // TODO code application logic here
    }
}
