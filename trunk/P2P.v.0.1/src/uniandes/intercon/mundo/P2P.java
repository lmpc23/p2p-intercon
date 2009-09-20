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
    ThreadServidorUDP ts;
    ThreadClienteUDP tc;

    public P2P(String nlog, InterfazP2P gui)
    {
        login = nlog;
        interfaz = gui;
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

    public void responderHandshake() {
        try {
            tc.handshakingAns(login, ip, listaAplicacionesLocales);

        } catch (Exception ex) {
        }
    }

    public void agregarClienteRemoto(IClienteRemoto cr) {
        if (!comprobarRegistro(cr)) {
            listaClientesDisponibles.add(cr);
        }

    }

    private boolean comprobarRegistro(IClienteRemoto cr) {
        for (int i = 0; i < listaClientesDisponibles.size(); i++) {
            if (listaClientesDisponibles.get(i).toString().equals(cr.toString())) {
                return true;
            }
        }
        return false;
    }

    public void eliminarAplicacionRemota(String login, String ip, String nombreA) {

        for (int i = 0; i < listaClientesDisponibles.size(); i++) {
            IClienteRemoto c = listaClientesDisponibles.get(i);
            if (c.darNickname().equals(login) && c.darIP().equals(ip)) {
                c.darListaAplicaciones().remove(nombreA);
                interfaz.publicarNoticia(login+" ha dado de baja la aplicación: "+nombreA);
            }
        }
    }

    public void agregarAplicacionRemota(String login, String ip, String nombreA) {
        for (int i = 0; i < listaClientesDisponibles.size(); i++) {
            IClienteRemoto c = listaClientesDisponibles.get(i);
            if (c.darNickname().equals(login) && c.darIP().equals(ip)) {
                c.darListaAplicaciones().add(nombreA);
                interfaz.publicarNoticia(login+" ha agregado una nueva aplicación: "+nombreA);

            }
        }
    }

    public void desconexionUsuarioRemota(String login, String ip) {
        for (int i = 0; i < listaClientesDisponibles.size(); i++) {
            IClienteRemoto c = listaClientesDisponibles.get(i);
            listaClientesDisponibles.remove(c);
            interfaz.publicarNoticia(login+" se ha desconectado.");
        }
    }

    public void agregarAplicacion(String ap, String ruta) throws Exception {
        //código nueva Aplicacion
        listaAplicacionesLocales.add(new Aplicacion(ap, ruta));
        tc.agregarAplicacion(login, ip, ap);
    }

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

    public void desconexion() throws Exception {
        tc.desconexion(login, ip);
    }

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


