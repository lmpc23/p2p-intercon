/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uniandes.intercon.mundo;

/**
 *
 * @author Juan Pablo
 */
public class Aplicacion implements IAplicacion{

    public String nombre;


    public Aplicacion(String n)
    {
        nombre=n;
    }

    public String darNombre() {
       return nombre ;
    }



    public Object darResultados(String[] parametros) {
        return null;
    }

}
