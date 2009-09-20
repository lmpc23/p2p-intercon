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
public class ThreadClienteUDP extends Thread {

	private MulticastSocket s;

    private final static int port = 1119;

    private final static String group = "224.0.0.0";
	
	public void run()
	{


		try {

            s = new MulticastSocket();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public void handshaking(String login, String ip, ArrayList<IAplicacion> aplicaciones) throws Exception
    {
        this.sleep(1000);
        String saludo = "HOLA:"+login+":"+ip+":"+aplicaciones.size();
        for(int i=0; i<aplicaciones.size(); i++)
        {
            saludo+=aplicaciones.get(i).darNombre();
        }

        byte[] buf = saludo.getBytes();
        DatagramPacket pack = new DatagramPacket(buf, buf.length,
		      					 InetAddress.getByName(group), port);
        s.send(pack);

    }

    public void handshakingAns(String login, String ip, ArrayList<IAplicacion> aplicaciones) throws Exception
    {
        this.sleep(1000);
        String saludo = "HOLA_ANS:"+login+":"+ip+":"+aplicaciones.size();
        for(int i=0; i<aplicaciones.size(); i++)
        {
            saludo+=aplicaciones.get(i).darNombre();
        }

        byte[] buf = saludo.getBytes();
        DatagramPacket pack = new DatagramPacket(buf, buf.length,
		      					 InetAddress.getByName(group), port);
        s.send(pack);

    }

    public void agregarAplicacion(String login, String ip, String nombreA) throws Exception
    {
        String mensaje = "AGR_APLICACION:"+login+":"+ip+":"+nombreA;
        byte[] buf = mensaje.getBytes();
        DatagramPacket pack = new DatagramPacket(buf, buf.length,
		      					 InetAddress.getByName(group), port);
        s.send(pack);
    }

    public void eliminarAplicacion(String login, String ip, String nombreA) throws Exception
    {
        String mensaje = "BOR_APLICACION:"+login+":"+ip+":"+nombreA;
        byte[] buf = mensaje.getBytes();
        DatagramPacket pack = new DatagramPacket(buf, buf.length,
		      					 InetAddress.getByName(group), port);
        s.send(pack);
    }

    public void desconexion(String login, String ip) throws Exception
    {
       String mensaje = "ADIOS:"+login+":"+ip;
        byte[] buf = mensaje.getBytes();
        DatagramPacket pack = new DatagramPacket(buf, buf.length,
		      					 InetAddress.getByName(group), port);
        s.send(pack);
    }




}
