
package controlador;

import com.sun.media.jfxmedia.logging.Logger;
import  controlador.*;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import modelo.Conect;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.*;

public class c_usuario {
    public static V_usuario vista;
    
     public c_usuario(V_usuario usuario) {
        this.vista = usuario;
        vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
        this.vista.getBtnImprimir().addActionListener(e->imprimirReporte());
     }

    private void imprimirReporte() {
        Conect con = new Conect();
        try {
            JasperReport jas = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/reporte_1.jasper"));
            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jas, null, con.getCon());
            JasperViewer jv = new JasperViewer(jp, false);
            JOptionPane.showMessageDialog(null, "IMPRIMIENDO REPORTE");
            jv.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jv.setVisible(true);
        } catch (JRException e) {
            System.out.println("NO SE ENCONTRO REGISTROS" + e.getMessage());
            
        }
    }
     
     
}
