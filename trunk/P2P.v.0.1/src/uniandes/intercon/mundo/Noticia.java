/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uniandes.intercon.mundo;

import java.util.Observable;

/**
 *
 * @author Julian
 */
public class Noticia extends Observable
{
    private String noticia;

    public Noticia()
    {
        noticia = "";
    }
    
    public String getNoticia() {
        return noticia;
    }

    public void setNoticia(String noticia) 
    {
        this.noticia = noticia;
        setChanged( );
        notifyObservers( new String( noticia ) );
        
    }



}
