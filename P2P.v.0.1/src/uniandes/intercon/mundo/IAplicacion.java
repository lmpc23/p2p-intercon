/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uniandes.intercon.mundo;

/**
 *
 * @author Juan Pablo
 */
public interface IAplicacion {

    public String darNombre();

    public Object darResultados(String parametros[]);

    public String darRuta();

    public String darInstrucciones();

    public int darNumeroParámetros();

    @Override
    public String toString();

}
