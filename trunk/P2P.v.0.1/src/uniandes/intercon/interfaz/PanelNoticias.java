/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uniandes.intercon.interfaz;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import uniandes.intercon.mundo.Noticia;

/**
 *
 * @author Julian
 */
public class PanelNoticias extends JPanel implements Observer
{
    private Noticia noticia;
    private JTextArea textNoticias;
    private JScrollPane scrollPane;

    public PanelNoticias(Noticia noticia)
    {
        this.noticia = noticia;
        textNoticias = new JTextArea("TEST");
        scrollPane = new javax.swing.JScrollPane();
        textNoticias.setColumns(20);
        textNoticias.setRows(5);
        textNoticias.setEditable(false);
        scrollPane.setViewportView(textNoticias);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(scrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, scrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
        );
    }

    public void update(Observable o, Object arg)
    {
        String news = ( String )arg;
        Date hoy = new Date(System.currentTimeMillis());
        textNoticias.setText( "["+hoy.toString()+"] "+ news +"\n"+textNoticias.getText() );
        
    }

}
