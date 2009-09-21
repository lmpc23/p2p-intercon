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

    public String ruta;

    public String instrucciones;

    public int numParametros;


    public Aplicacion(String n, String r, String ins, int npar)
    {
        nombre=n;
        ruta = r;
        instrucciones=ins;
        numParametros = npar;
    }

    public String darNombre() {
       return nombre ;
    }



    public Object darResultados(String[] parametros) {
        return null;
    }

    public String darRuta() {
        return ruta;
    }


    @Override
    public String toString()
    {
        return nombre;
    }

    public String darInstrucciones() {
       return instrucciones;
    }

    public int darNumeroParámetros() {
        return numParametros;
    }

}
