/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uniandes.intercon.mundo;

import java.io.BufferedReader;
import java.io.DataInputStream;
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

    P2P principal;

    public ThreadServidorTCP(P2P p)
    {
        principal=p;
    }

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

           //BufferedReader in = new BufferedReader(new DataInputStream(cliente.getInputStream()));
           DataInputStream dis = new DataInputStream(cliente.getInputStream());
           DataOutputStream out = new DataOutputStream(cliente.getOutputStream());

           String msj = dis.readUTF();
           String[] val = msj.split(":");
           if(val[0].equals("APLICACION_LIN"))
           {
                IAplicacion a = principal.darAplicacion(val[1]);
                System.out.println(val[1]);
                out.writeUTF(a.darInstrucciones());
           }
           else if(val[0].equals("APLICACION_NUMPAR"))
           {
                IAplicacion a = principal.darAplicacion(val[1]);
                out.writeUTF(String.valueOf(a.darNumeroParámetros()));
           }
           else if(val[0].equals("APLICACION_PARAMS"))
           {
                String[] params = new String[val.length-2];
                for(int i=0; i<params.length; i++)
                {
                    params[i]=val[2+0];
                }

                String res = principal.ejecutar(val[1], params);
                out.writeUTF(res);
           }



        }
    }

}
