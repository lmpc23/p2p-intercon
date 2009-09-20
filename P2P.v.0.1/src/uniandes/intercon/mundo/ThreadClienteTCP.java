/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uniandes.intercon.mundo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Juan Pablo
 */
public class ThreadClienteTCP extends Thread{

    private static final int puerto = 1120;

    private Socket cliente;

    public ThreadClienteTCP(String host)
    {
        try{
            cliente = new Socket(host, puerto);
            DataOutputStream out = new DataOutputStream(cliente.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            //Aquí se hace el procesamiento de las peticiones

            cliente.close();
        }
        catch(Exception e)
        {

        }



    }

}
