package uniandes.intercon.mundo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import uniandes.intercon.interfaz.InterfazP2P;

/**
 * 
 */
/**
 * Clase Principal del Mundo
 * @author Juan Pablo
 *
 */
public class P2P
{
    private InterfazP2P interfaz;
    private String login;
    private String ip;
    private ArrayList<IClienteRemoto> listaClientesDisponibles;
    private ArrayList<IAplicacion> listaAplicacionesLocales;
    /**
     * Thread del Servidor vía UDP
     */
    ThreadServidorUDP ts;
    /**
     * Thread del Cliente vía UDP
     */
    ThreadClienteUDP tc;


    /**
     * Constructor del mundo
     * @param nlog, login del usuario local
     */

    public P2P(String nlog, InterfazP2P gui)
    {

        login = nlog;
        interfaz = gui;
        try {
            //obtención del localhost
            ip = InetAddress.getLocalHost().getHostAddress().toString();
            listaClientesDisponibles = new ArrayList<IClienteRemoto>();
            listaAplicacionesLocales = new ArrayList<IAplicacion>();

            //inicialización de los Threads UDP cliente y Servidor
            ts = new ThreadServidorUDP(this);
            ts.start();
            tc = new ThreadClienteUDP();
            tc.start();

            //Handshaking para la red P2P
            tc.handshaking(login, ip, listaAplicacionesLocales);


        } catch (Exception ex) {
        }
    }

    /**
     * Método del mundo para dar Respuesta a todos los usuarios que entren a la red, existe para evitar un ciclo interminable de saludos
     */
    public void responderHandshake() {
        try {
            tc.handshakingAns(login, ip, listaAplicacionesLocales);

        } catch (Exception ex) {
        }
    }

    /**
     * Método que agrega a la lista de Clientes Disponibles un cliente, junto con la lista de sus aplicaciones (modelada en el objeto)
     * @param cr ClienteRemoto a agregar, se comprueba que no exista ya (login e IP no coincidan en alguno de la lista).
     */
    public void agregarClienteRemoto(IClienteRemoto cr) {
        if (!comprobarRegistro(cr)) {
            listaClientesDisponibles.add(cr);
        }

    }

    /**
     * Método para comprobar que no haya un cliente registrado de antemano, mirando IP y LOGIN
     * @param cr Cliente remoto
     * @return Falso o Verdadero
     */
    private boolean comprobarRegistro(IClienteRemoto cr) {
        for (int i = 0; i < listaClientesDisponibles.size(); i++) {
            if (listaClientesDisponibles.get(i).toString().equals(cr.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método que elimina una Aplicacion Remota de un cliente, dados los parámetros del mismo
     * @param login del cliente con la aplicacion
     * @param ip del cliente con la aplicación
     * @param nombreA nombre de la aplicación
     */
    public void eliminarAplicacionRemota(String login, String ip, String nombreA) {

        for (int i = 0; i < listaClientesDisponibles.size(); i++) {
            IClienteRemoto c = listaClientesDisponibles.get(i);
            if (c.darNickname().equals(login) && c.darIP().equals(ip)) {
                c.darListaAplicaciones().remove(nombreA);
                interfaz.publicarNoticia(login+" ha dado de baja la aplicación: "+nombreA);
            }
        }
    }

    /**
     * Método para agregar, tras una notificación, una aplicación remota de un cliente
     * @param login del cliente con la aplicación
     * @param ip  del cliente con la aplicación
     * @param nombreA nombre de la aplicación
     */
    public void agregarAplicacionRemota(String login, String ip, String nombreA) {
        for (int i = 0; i < listaClientesDisponibles.size(); i++) {
            IClienteRemoto c = listaClientesDisponibles.get(i);
            if (c.darNickname().equals(login) && c.darIP().equals(ip)) {
                c.darListaAplicaciones().add(nombreA);
                interfaz.publicarNoticia(login+" ha agregado una nueva aplicación: "+nombreA);

            }
        }
    }

    /**
     * Método que eliminar un usuario de la lista de Clientes disponibles dada su desconexión
     * @param login
     * @param ip
     */
    public void desconexionUsuarioRemota(String login, String ip) {
        for (int i = 0; i < listaClientesDisponibles.size(); i++) {
            IClienteRemoto c = listaClientesDisponibles.get(i);
            listaClientesDisponibles.remove(c);
            interfaz.publicarNoticia(login+" se ha desconectado.");
        }
    }

    /**
     * Método que agrega una aplicación a nivel local y lo notifica a los demás usuarios
     * @param ap
     * @param ruta
     * @throws java.lang.Exception
     */
    public void agregarAplicacion(String ap, String ruta) throws Exception {
        //código nueva Aplicacion
        listaAplicacionesLocales.add(new Aplicacion(ap, ruta));
        tc.agregarAplicacion(login, ip, ap);
    }

    /**
     * Método que elimina una aplicación a nivel local y lo notifica a los demás usuarios
     * @param ap
     * @throws java.lang.Exception
     */
    public void eliminarAplicacion(String ap) throws Exception {
        for (int i = 0; i < listaAplicacionesLocales.size(); i++) {
            IAplicacion a = listaAplicacionesLocales.get(i);
            if (a.darNombre().equals(ap)) {
                listaAplicacionesLocales.remove(a);
                tc.eliminarAplicacion(login, ip, ap);
                break;
            }
        }
    }


    /**
     * Método que notifica la desconexión del usuario
     * @throws java.lang.Exception
     */
    public void desconexion() throws Exception {
        tc.desconexion(login, ip);
    }

    /**
     * Método para ejecutar una aplicación a nivel local y que retorne una respuesta en String
     * @param ap, la aplicación a ejecutar
     * @param comando, comando para correr la aplicación, lleva "java, -jar, ruta, parámetros..."
     * @return Cadena resultado
     */
    public String ejecutar(String ap, String comando[]) {

        String valor = null;

        for (int i = 0; i < listaAplicacionesLocales.size(); i++) {
            IAplicacion a = listaAplicacionesLocales.get(i);
            if (a.equals(ap)) {
                Runtime r = Runtime.getRuntime();
                Process pr = null;

                try {
                    byte b[] = new byte[1024];
                    int res = 0;
                    pr = r.exec(comando);
                    System.out.println(pr.getInputStream().read(b));
                    valor = new String(b);
                    for (int j = 0; j < valor.length(); j++) {
                        if ((int) valor.charAt(j) == 0) {
                            res = i;
                            break;
                        }
                    }

                    valor = valor.substring(0, res);


                } catch (Exception e) {
                    System.out.println("Error ejecutando " + comando[0]);
                    System.out.println(e.getMessage());
                }

            }
        }
        return valor;
    }

    public int numApps() {
        return listaAplicacionesLocales.size();
    }

    public int busquedaLocal(String appname) throws Exception {
        for (int i = 0; i < listaAplicacionesLocales.size(); i++) {
            IAplicacion temp = listaAplicacionesLocales.get(i);
            if (temp.darNombre().equals(appname)) {
                return i;
            }
        }

        throw new Exception("La aplicación buscada, no se encuentra localmente");
    }

    public ArrayList getListaLocal()
    {
        return listaAplicacionesLocales;
    }

    /*
     * @param args
     
    public static void main(String[] args) throws IOException {
        P2P p = new P2P("usuario");

    }
    //Test*/
}


