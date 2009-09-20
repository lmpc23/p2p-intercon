/**
 * 
 */
package uniandes.intercon.mundo;

import sun.net.*;
import java.net.*;
import java.util.ArrayList;

/**
 * @author Juan Pablo
 *
 */
public class ThreadServidorUDP extends Thread {

    private MulticastSocket s;
    private final static int port = 1119;
    private final static String group = "224.0.0.0";
    private P2P principal;

    public ThreadServidorUDP(P2P p) {
        principal = p;
    }

    public void run() {
        try {

//			 Create the socket and bind it to port 'port'.
            s = new MulticastSocket(port);


//			 join the multicast group
            s.joinGroup(InetAddress.getByName(group));

//			 Now the socket is set up and we are ready to receive packets


//			 Create a DatagramPacket and do a receive
            byte buf[] = new byte[1024];
            DatagramPacket pack = new DatagramPacket(buf, buf.length);

            recibirMensajes(pack);

            s.leaveGroup(InetAddress.getByName(group));
            s.close();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void recibirMensajes(DatagramPacket pack) throws Exception {


        while (true) {

            s.receive(pack);

            String valor = new String(pack.getData());
            valor = valor.substring(0, pack.getLength());
            String[] msj = valor.split(":");

            if (msj[0].equals("HOLA")) {
                principal.responderHandshake();
                String login = msj[1];
                String ip = msj[2];
                int apl = Integer.parseInt(msj[3]);
                ArrayList<String> ap = new ArrayList<String>();
                for (int i = 0; i < apl; i++) {
                    ap.add(msj[4 + i]);
                }
                principal.agregarClienteRemoto(new ClienteRemoto(login, ip, ap));
            } else if (msj[0].equals("HOLA_ANS")) {
                String login = msj[1];
                String ip = msj[2];
                int apl = Integer.parseInt(msj[3]);
                ArrayList<String> ap = new ArrayList<String>();
                for (int i = 0; i < apl; i++) {
                    ap.add(msj[4 + i]);
                }

                principal.agregarClienteRemoto(new ClienteRemoto(login, ip, ap));
            } else if (msj[0].equals("AGR_APLICACION")) {
                String login = msj[1];
                String ip = msj[2];
                String nombreA = msj[3];
                principal.agregarAplicacionRemota(login, ip, nombreA);
            } else if (msj[0].equals("BOR_APLICACION")) {
                String login = msj[1];
                String ip = msj[2];
                String nombreA = msj[3];
                principal.eliminarAplicacionRemota(login, ip, nombreA);
            } else if (msj[0].equals("ADIOS")) {
                String login = msj[1];
                String ip = msj[2];
                principal.desconexionUsuarioRemota(login, ip);
            }

        }

    }
}
