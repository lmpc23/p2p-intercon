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
public interface IClienteRemoto {

    public String darNickname();

    public String darIP();

    public ArrayList<String> darListaAplicaciones();


}
