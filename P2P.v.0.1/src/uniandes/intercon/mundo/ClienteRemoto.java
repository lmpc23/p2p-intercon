/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uniandes.intercon.mundo;

import java.util.ArrayList;





/**
 *
 * @author Juan Pablo
 */
public class ClienteRemoto implements IClienteRemoto{

    private String nickname;

    private String ip;

    private ArrayList<String> listaAplicaciones;


    public ClienteRemoto(String nick, String nip, ArrayList<String> lista)
    {
        nickname=nick;
        ip=nip;
        listaAplicaciones=lista;
    }


    public String darNickname() {
       return nickname;
    }

    public String darIP() {
        return ip;
    }

    public void agregarAplicacion(String apl)
    {
        listaAplicaciones.add(apl);
    }

    public ArrayList<String> darListaAplicaciones() {
        return listaAplicaciones;
    }

    public String toString()
    {
        return nickname+":"+ip;
    }

  




}
