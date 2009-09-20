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
public class ServerExample {

    public static void main(String argv[]) throws Exception
    {
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);
        while (true)
        {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));


            DataOutputStream outToClient =
                    new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);

        }
    }
}
