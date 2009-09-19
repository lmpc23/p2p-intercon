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
public class ThreadServidor extends Thread {

	MulticastSocket s;
	
	public void run()
	{
		try {
			int port = 1119;
//			 Which address
			String group = "224.0.0.0";


//			 Create the socket and bind it to port 'port'.
			s = new MulticastSocket(port);


//			 join the multicast group
			s.joinGroup(InetAddress.getByName(group));

//			 Now the socket is set up and we are ready to receive packets


//			 Create a DatagramPacket and do a receive
			byte buf[] = new byte[1024];
			DatagramPacket pack = new DatagramPacket(buf, buf.length);
			System.out.println("ja!");
			
			recibirMensajes(pack);
				
			s.leaveGroup(InetAddress.getByName(group));
			s.close();
				

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void recibirMensajes(DatagramPacket pack) throws Exception
	{

		
		while(true){
		System.out.println(">>>>recibo");
		s.receive(pack);
		System.out.println(">>>>recibí algo");

//		 Finally, let us do something useful with the data we just received,
//		 like print it on stdout :-)
		System.out.println("Received data from: " + pack.getAddress().toString() +
				    ":" + pack.getPort() + " with length: " +
				    pack.getLength());
		System.out.write(pack.getData(),0,pack.getLength());
		System.out.println();

		}

	}
	
}
