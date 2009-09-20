/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uniandes.intercon.mundo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Pablo
 */
public class ThreadServidorTCP extends Thread{

    private final static int puerto = 1120;

    ServerSocket servidor;

   public void run()
   {
        try {
            servidor = new ServerSocket(puerto);
            procesarSolicitudes();


        } catch (Exception ex) {

        }
        



   }

    private void procesarSolicitudes() throws Exception {

        while(true)
        {
            Socket cliente = servidor.accept();

           BufferedReader in = new BufferedReader(new InputStreamReader ( cliente.getInputStream()));
           DataOutputStream out = new DataOutputStream(cliente.getOutputStream());

           String aplicacion = in.readLine();
           out.writeBytes("Mensaje");


        }
    }

}
