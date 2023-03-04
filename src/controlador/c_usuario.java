package controlador;

import com.sun.media.jfxmedia.logging.Logger;
import controlador.*;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import modelo.Conect;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.*;
import modelo.*;

public class c_usuario {

    public static V_usuario vista;
    private Usuario_bd bdusuario = new Usuario_bd();
    private Rol_bd bdrol = new Rol_bd();
    List<Usuario_mb> lista = bdusuario.mostrardatos();

    public c_usuario(V_usuario usuario) {
        this.vista = usuario;
        vista.setVisible(true);
        
        this.vista.setLocationRelativeTo(null);
        this.vista.getBtnImprimir().addActionListener(e -> imprimirReporte());
        this.vista.getBtnRol().addActionListener(e -> cargarVentanaRol());
        this.vista.getBtnGuardar().addActionListener(e -> guardar());
        this.vista.getBtnNuevo().addActionListener(e -> nuevo());
        this.vista.getBtnModificar().addActionListener(e -> modificar());
        this.vista.getBtnEliminar().addActionListener(e -> eliminar());
        this.vista.getBtnBuscar().addActionListener(e -> buscarCedula());
        this.vista.getTabla().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionar();
            }
        });
        lista();
        
    }

    public void buscarCedula() {
        String cedula=vista.getTxtCedula().getText();
        if (cedula.isEmpty()) {
           JOptionPane.showMessageDialog(null, "Digite una cedula"); 
        }
        
        if (bdusuario.verificarExistente(cedula)) {
            JOptionPane.showMessageDialog(null, "Ya existe ese usuario"); 
             int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea Modificarlo?");
            if (respuesta == 0) {
                habilitarElementos();
            }
        }else{
            JOptionPane.showMessageDialog(null, "No existe ese usuario , creelo"); 
            habilitarElementos();
        }
    }

    private void deshabilitarElementos() {
        
        vista.getCbEstado().setEnabled(false);
        vista.getCbRol().setEnabled(false);
        vista.getTxtUsuario().setEditable(false);
        vista.getTxtPassword().setEnabled(false);
        this.vista.getBtnGuardar().setEnabled(false);
        this.vista.getBtnNuevo().setEnabled(false);
        this.vista.getBtnModificar().setEnabled(false);
        this.vista.getBtnEliminar().setEnabled(false);
    }

    private void habilitarElementos() {
       
        vista.getCbEstado().setEnabled(true);
        vista.getCbRol().setEnabled(true);
        vista.getTxtUsuario().setEditable(true);
        vista.getTxtPassword().setEnabled(true);
        this.vista.getBtnGuardar().setEnabled(true);
        this.vista.getBtnNuevo().setEnabled(true);
        this.vista.getBtnModificar().setEnabled(true);
        this.vista.getBtnEliminar().setEnabled(true);
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

    public void lista() {
        DefaultTableModel modelo; //crear el modelo
        modelo = (DefaultTableModel) vista.getTabla().getModel(); //acepta la vista de la tabla
        List<Usuario_mb> lista = bdusuario.mostrardatos();// carga mis datos
        int columnas = modelo.getColumnCount();
        for (int j = vista.getTabla().getRowCount() - 1; j >= 0; j--) {
            modelo.removeRow(j);
        }
        for (int i = 0; i < lista.size(); i++) {
            modelo.addRow(new Object[columnas]);
            vista.getTabla().setValueAt(lista.get(i).getCedula(), i, 0);
            vista.getTabla().setValueAt(lista.get(i).getUsuario(), i, 1);
            int codigorol = lista.get(i).getCodigo_rol();
            List<Rol_mb> listatipocod = bdrol.mostrardatoscodigo(codigorol);
            vista.getTabla().setValueAt(listatipocod.get(0).getNombre(), i, 2);
            vista.getTabla().setValueAt(lista.get(i).getPassword(), i, 3);
            vista.getTabla().setValueAt(lista.get(i).getEstado(), i, 4);

        }
        vista.getCbRol().removeAllItems();
        List<Rol_mb> listatipo = bdrol.mostrardatos();
        for (int i = 0; i < listatipo.size(); i++) {
            vista.getCbRol().addItem(listatipo.get(i).getNombre());
        }
        deshabilitarElementos();
    }

    public void guardar() {

        if (vista.getTxtCedula().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No dejar campos vacios");
        } else {

            List<Usuario_mb> lista = bdusuario.mostrardatos();

            bdusuario.setCedula(this.vista.getTxtCedula().getText());
            String rol = this.vista.getCbRol().getSelectedItem().toString();
            List<Rol_mb> listatipo = bdrol.mostrardatosnombre(rol);
            bdusuario.setCodigo_rol(listatipo.get(0).getCodigo());
            bdusuario.setEstado(this.vista.getCbEstado().getSelectedItem().toString());
            bdusuario.setPassword(this.vista.getTxtPassword().getText());
            if (bdusuario.insertar()) {
                JOptionPane.showMessageDialog(null, "Guardado correctamente");
                lista();
                nuevo();

            } else {
                JOptionPane.showMessageDialog(null, "Erorr al guardar");
            }

        }

    }

    public void nuevo() {

        vista.getTxtCedula().setText("");
        vista.getCbEstado().setSelectedIndex(0);
        vista.getCbRol().setSelectedIndex(0);
        vista.getTxtUsuario().setText("");
        vista.getTxtPassword().setText("");
    }

    public void seleccionar() {
        
        List<Usuario_mb> lista = bdusuario.mostrardatos();
        int selec = vista.getTabla().getSelectedRow();
       
        if (selec != -1) {

            bdusuario.setCedula(lista.get(selec).getCedula());
            vista.getTxtCedula().setText(bdusuario.getCedula());
            bdusuario.setUsuario(lista.get(selec).getUsuario());
            vista.getTxtUsuario().setText(bdusuario.getUsuario());

          
            vista.getCbRol().setSelectedItem(lista.get(0).getCodigo_rol());

            bdusuario.setEstado(lista.get(selec).getEstado());
            vista.getCbEstado().setSelectedItem(bdusuario.getEstado());

           

        }
    }
    public void modificar() {
        int selec = vista.getTabla().getSelectedRow();
        List<Usuario_mb> lista = bdusuario.mostrardatos();
        if (selec != -1) {
            bdusuario.setCedula(this.vista.getTxtCedula().getText());
            String rol = this.vista.getCbRol().getSelectedItem().toString();
            List<Rol_mb> listatipo = bdrol.mostrardatosnombre(rol);
            bdusuario.setCodigo_rol(listatipo.get(0).getCodigo());
            bdusuario.setEstado(this.vista.getCbEstado().getSelectedItem().toString());
            bdusuario.setPassword(this.vista.getTxtPassword().getText());
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de Modificar?");
            if (respuesta == 0) {
                if (bdusuario.modificar(lista.get(selec).getCedula())) {
                    JOptionPane.showMessageDialog(null, "Datos Actualizados");
                    lista();
                    nuevo();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccionar el registro a Modificar");
        }
    }

    public void eliminar() {
        int selec = vista.getTabla().getSelectedRow();
        List<Usuario_mb> lista = bdusuario.mostrardatos();
        if (selec != -1) {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de Eliminar?");
            if (respuesta == 0) {
                if (bdusuario.eliminar(lista.get(selec).getCedula())) {
                    JOptionPane.showMessageDialog(null, "Datos Eliminados");
                    lista();
                    nuevo();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccionar el registro a Eliminar");
        }
    }

    private void cargarVentanaRol() {
        v_rol v=new v_rol();
        c_rol c=new c_rol(v);
    }

}
