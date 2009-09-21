/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uniandes.intercon.mundo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Juan Pablo
 */
public class ThreadClienteTCP extends Thread {

    private static final int puerto = 1120;
    private Socket cliente;
    private String host;
    private String ap;
    private P2P principal;
    DataOutputStream out;
    BufferedReader in;

    public ThreadClienteTCP(String h, String a, P2P p) {
        host = h;
        ap = a;
        principal = p;

    }

    public void run() {
        try {
            cliente = new Socket(host, puerto);
            out = new DataOutputStream(cliente.getOutputStream());
            in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

        } catch (Exception e) {
            System.out.println("ACA!!");
            e.printStackTrace();
        }
    }


    public String darLineamientos() throws Exception 
    {
        cliente = new Socket(host, puerto);
        System.out.println(">>" + out);
        System.out.println(">>" + in);
        out = new DataOutputStream(cliente.getOutputStream());
        //in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        DataInputStream dis = new DataInputStream(cliente.getInputStream());
        System.out.println("<<" + out);
        System.out.println("<<" + in);
        String mensaje = "APLICACION_LIN:" + ap;
        String answer = "";
        out.writeUTF(mensaje);
        answer = dis.readUTF();
        System.out.println(answer);
        out.close();
        dis.close();
        cliente.close();
        return answer;
    }

    public String darNumeroParametros() throws Exception 
    {
        cliente = new Socket(host, puerto);
        out = new DataOutputStream(cliente.getOutputStream());
        DataInputStream dis = new DataInputStream(cliente.getInputStream());

        String mensaje = "APLICACION_NUMPAR:" + ap;
        String answer = "";
        out.writeBytes(mensaje);
        answer = dis.readUTF();
        out.close();
        dis.close();
        cliente.close();
        return answer;
    }

    public String darRespuesta(String[] parametros) throws Exception 
    {
        cliente = new Socket(host, puerto);
        out = new DataOutputStream(cliente.getOutputStream());
        DataInputStream dis = new DataInputStream(cliente.getInputStream());

        String mensaje = "APLICACION_PARAMS:" + ap;
        for (int i = 0; i < parametros.length; i++) {
            mensaje += ":" + parametros[i];
        }
        String answer = "";
        out.writeBytes(mensaje);
        answer = dis.readUTF();
        out.close();
        dis.close();
        cliente.close();
        return answer;
    }
}
