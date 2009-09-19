/**
 * 
 */
package uniandes.intercon.mundo;

import sun.net.*;
import java.net.*;

/**
 * @author Juan Pablo
 *
 */
public class ThreadCliente extends Thread {

	
	
	public void run()
	{
		try {
			enviarMensajes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void enviarMensajes() throws Exception
	{
			System.out.println("here");
			
			System.out.println("acá");
		      // Which port should we send to
		      int port = 1119;
		      // Which address
		      String group = "224.0.0.0";
		      // Which ttl

		      // Create the socket but we don't bind it as we are only going to send data
		      MulticastSocket s = new MulticastSocket();
		      // Note that we don't have to join the multicast group if we are only
		      // sending data and not receiving

		      // Fill the buffer with some data
		      byte buf[] = new byte[10];
		      for (int i=0; i<buf.length; i++) buf[i] = (byte)i;
		      

		      // Create a DatagramPacket 
		      DatagramPacket pack = new DatagramPacket(buf, buf.length,
		      					 InetAddress.getByName(group), port);
		      // Do a send. Note that send takes a byte for the ttl and not an int.
		
		      this.sleep(1000);
		   
			    System.out.println(":::::Voy a Enviar");
		      s.send(pack);
		      System.out.println(":::::Envié");

			      // And when we have finished sending data close the socket
		      s.close();
		
	}
}
