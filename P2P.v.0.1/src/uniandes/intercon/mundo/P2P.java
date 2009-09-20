package uniandes.intercon.mundo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 */
/**
 * @author Juan Pablo
 *
 */
public class P2P {

    private String login;
    private String ip;
    private ArrayList<IClienteRemoto> listaClientesDisponibles;
    private ArrayList<IAplicacion> listaAplicacionesLocales;
    ThreadServidorUDP ts;
    ThreadClienteUDP tc;

    public P2P(String nlog) {

        login = nlog;
        try {
            ip = InetAddress.getLocalHost().getHostAddress().toString();
            listaClientesDisponibles = new ArrayList<IClienteRemoto>();
            listaAplicacionesLocales = new ArrayList<IAplicacion>();

            ts = new ThreadServidorUDP(this);
            ts.start();
            tc = new ThreadClienteUDP();
            tc.start();


            tc.handshaking(login, ip, listaAplicacionesLocales);


        } catch (Exception ex) {
        }
    }

    public void responderHandshake()
    {
        try {
            tc.handshakingAns(login, ip, listaAplicacionesLocales);
            
        } catch (Exception ex) {

        }
    }

    public void agregarClienteRemoto(IClienteRemoto cr)
    {
        if(!comprobarRegistro(cr))
            listaClientesDisponibles.add(cr);

    }

  
      private boolean comprobarRegistro(IClienteRemoto cr) {
        for(int i=0; i<listaClientesDisponibles.size(); i++)
        {
            if(listaClientesDisponibles.get(i).toString().equals(cr.toString()))
                return true;
        }
        return false;
    }

      public void eliminarAplicacionRemota(String login, String ip, String nombreA){

        for(int i=0; i<listaClientesDisponibles.size(); i++)
        {
            IClienteRemoto c=listaClientesDisponibles.get(i);
            if(c.darNickname().equals(login) && c.darIP().equals(ip))
                c.darListaAplicaciones().remove(nombreA);
        }
      }

      public void agregarAplicacionRemota(String login, String ip, String nombreA){
       for(int i=0; i<listaClientesDisponibles.size(); i++)
        {
            IClienteRemoto c=listaClientesDisponibles.get(i);
            if(c.darNickname().equals(login) && c.darIP().equals(ip))
                c.darListaAplicaciones().add(nombreA);
        }
      }

      public void desconexionUsuarioRemota(String login, String ip){
             for(int i=0; i<listaClientesDisponibles.size(); i++)
        {
            IClienteRemoto c=listaClientesDisponibles.get(i);
            listaClientesDisponibles.remove(c);
        }
      }

      public void agregarAplicacion(String ap) throws Exception
      {
        //código nueva Aplicacion
        tc.agregarAplicacion(login, ip, ap);
      }

      public void eliminarAplicacion(String ap) throws Exception
      {
        for(int i=0; i<listaAplicacionesLocales.size(); i++)
        {
            IAplicacion a = listaAplicacionesLocales.get(i);
            if(a.darNombre().equals(ap))
            {
                listaAplicacionesLocales.remove(a);
                tc.eliminarAplicacion(login, ip, ap);
                break;
            }
        }
      }

      public void desconexion() throws Exception
      {
        tc.desconexion(login, ip);
      }

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        P2P p = new P2P("usuario");
         Runtime r = Runtime.getRuntime();
        Process pr = null;
        String comando[] = { "java","-jar", "J:\\AplicacionDePrueba\\dist\\AplicacionDePrueba.jar"};
        System.out.println(":::::::::::::::::::::::::");
     
        // Intenta ejecutar el comando que se le indica, en
        // este caso lanzar el bloc de notas
        try {
            byte b[] = new byte[1024];
            int res=0;
            pr = r.exec( comando );
            System.out.println(pr.getInputStream().read(b));
            String valor=new String(b);
            for(int i=0; i<valor.length(); i++)
            {
                if((int)valor.charAt(i)==0)
                {
                    res=i;
                    break;
                }
            }
  
            valor = valor.substring(0, res);
            System.out.println(valor);

        } catch( Exception e ) {
            System.out.println( "Error ejecutando "+comando[0] );
             System.out.println( e.getMessage() );
            }

    }



    //Test
}


