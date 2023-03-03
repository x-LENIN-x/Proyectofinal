
package controlador;

import com.sun.media.jfxmedia.logging.Logger;
import  controlador.*;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
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

public class C_usuario {
    public static V_usuario vista;
    public Usuario_bd bdusuario= new Usuario_bd();
    List<Usuario_mb> lista= bdusuario.mostrardatos();
    private Rol_bd bdrol = new Rol_bd();
    
     public C_usuario(V_usuario usuario) {
        this.vista = usuario;
        vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
        this.vista.getBtnGuardar().addActionListener(e->guardar());
        this.vista.getBtnNuevo().addActionListener(e->nuevo());
        this.vista.getBtnModificar().addActionListener(e->modificar());
        this.vista.getBtnEliminar().addActionListener(e-> eliminar());
        this.vista.getBtnImprimir().addActionListener(e->imprimirReporte());
        this.vista.getBtnBuscar().addActionListener(e->verificarUsuario());
        this.vista.getBtnRol().addActionListener(e->abrirRol());
         this.vista.getTabla().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionar();
              
            }  
        });
        lista();     
     }
     
     public void abrirRol(){
         V_rol v=new V_rol();
         C_rol c=new C_rol(v);
     }
     
     public void verificarUsuario(){
        String cedula = vista.getTxtCedula().getText();
         if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite una cedula porfavor");
            return;
         }
        boolean existe= bdusuario.verificarExistente(cedula);
         if (existe) {
             JOptionPane.showMessageDialog(null, "El usuario ya existe");
             int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea modificarlo?");
             if (respuesta == 0) {
                 habilitarOpciones();
             }
         }else{
             JOptionPane.showMessageDialog(null, "El usuario aun no existe,creelo");
             habilitarOpciones();
         }
     }
    
     
     public void deshabilitarOpciones(){
        vista.getCbEstado().setEnabled(false);
        vista.getCbRol().setEnabled(false);
        vista.getTxtPassword().setEditable(false);
        vista.getTxtUsuario().setEditable(false);
        vista.getBtnNuevo().setEnabled(false);
        vista.getBtnModificar().setEnabled(false);
        vista.getBtnEliminar().setEnabled(false);
        vista.getBtnGuardar().setEnabled(false);
        vista.getTabla().setEnabled(false);
     }
     
     public void habilitarOpciones(){
        vista.getCbEstado().setEnabled(true);
        vista.getCbRol().setEnabled(true);
        vista.getTxtPassword().setEditable(true);
        vista.getTxtUsuario().setEditable(true);
        vista.getBtnNuevo().setEnabled(true);
        vista.getBtnModificar().setEnabled(true);
        vista.getBtnEliminar().setEnabled(true);
        vista.getBtnGuardar().setEnabled(true);
        vista.getTabla().setEnabled(true);
     }
    
    public void lista(){
        DefaultTableModel modelo; //crear el modelo
        modelo = (DefaultTableModel) vista.getTabla().getModel(); //acepta la vista de la tabla
        List<Usuario_mb> lista= bdusuario.mostrardatos();  // carga mis datos
        int columnas = modelo.getColumnCount();
//****** Limpiar en decremento   ********
        for (int j = vista.getTabla().getRowCount()-1; j >= 0; j--){
            modelo.removeRow(j);
        }
//***********************
        for (int i = 0; i < lista.size(); i++) {
            modelo.addRow(new Object[columnas]);
            vista.getTabla().setValueAt(lista.get(i).getCedula(), i, 0);
            vista.getTabla().setValueAt(lista.get(i).getUsuario(), i, 1);
            vista.getTabla().setValueAt(lista.get(i).getPassword(), i, 2);
            int codigoRol = lista.get(i).getCodigo_rol();  
            List<Rol_mb> listatipocod = bdrol.mostrardatoscodigo(codigoRol);
            vista.getTabla().setValueAt(listatipocod.get(0).getNombre(), i, 3);
            vista.getTabla().setValueAt(lista.get(i).getEstado(), i, 4);
               }
        
       deshabilitarOpciones();
       
        vista.getCbRol().removeAllItems();
        List<Rol_mb> listatipo = bdrol.mostrardatos();
        for (int i = 0; i < listatipo.size(); i++) {
            vista.getCbRol().addItem(listatipo.get(i).getNombre());
        }
        
       }
     
     
      public void guardar(){
        
          if (vista.getTxtCedula().getText().isEmpty() ||  vista.getCbRol().getSelectedItem().toString().isEmpty() ||  vista.getCbEstado().getSelectedItem().toString().isEmpty() ||
                 vista.getTxtUsuario().getText().isEmpty() ||  vista.getTxtPassword().getText().isEmpty() ) { 
          JOptionPane.showMessageDialog(null, "No dejar campos vacios");
        }else {
            
            bdusuario.setCedula(this.vista.getTxtCedula().getText());
            bdusuario.setEstado(this.vista.getCbEstado().getSelectedItem().toString());
            bdusuario.setCodigo_rol(Integer.parseInt(this.vista.getCbRol().getSelectedItem().toString()));
            bdusuario.setPassword(this.vista.getTxtPassword().getText());
            bdusuario.setUsuario(this.vista.getTxtUsuario().getText());
        
           
         
            if (bdusuario.insertar()){
                 JOptionPane.showMessageDialog(null, "Guardado Correctamente");
                 lista();
                 nuevo();
             }else {
                 JOptionPane.showMessageDialog(null, "Error Al Guardar");
             }
            }
            
    }
      
      
    public void modificar(){
        int selec = vista.getTabla().getSelectedRow();
        
         if (selec != -1) {
            bdusuario.setCedula(this.vista.getTxtCedula().getText());
            bdusuario.setEstado(this.vista.getCbEstado().getSelectedItem().toString());
            bdusuario.setCodigo_rol(Integer.parseInt(this.vista.getCbRol().getSelectedItem().toString()));
            bdusuario.setPassword(this.vista.getTxtPassword().getText());
            bdusuario.setUsuario(this.vista.getTxtUsuario().getText());
        
           
                 
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
     public void nuevo(){
        vista.getTxtCedula().setText("");
        vista.getCbEstado().setSelectedIndex(0);
        vista.getCbRol().setSelectedIndex(0);
        vista.getTxtPassword().setText("");
        vista.getTxtUsuario().setText("");
    
        
        
    }
      public void seleccionar(){
        this.vista.getBtnGuardar().setEnabled(true);
        int selec = vista.getTabla().getSelectedRow();
         if (selec != -1) {
            bdusuario.setCedula(this.vista.getTxtCedula().getText());
            vista.getTxtCedula().setText(bdusuario.getCedula());       
            bdusuario.setEstado(this.vista.getCbEstado().getSelectedItem().toString());
            vista.getCbEstado().setSelectedItem(bdusuario.getEstado());
            bdusuario.setCodigo_rol(Integer.parseInt(this.vista.getCbRol().getSelectedItem().toString()));
            vista.getCbRol().setSelectedItem(bdusuario.getEstado());
            bdusuario.setPassword(this.vista.getTxtPassword().getText());
            vista.getTxtPassword().setText(bdusuario.getPassword());   
            bdusuario.setUsuario(this.vista.getTxtUsuario().getText());
            vista.getTxtUsuario().setText(bdusuario.getUsuario());   

         }
       
    }
    public void eliminar(){
        int selec =vista.getTabla().getSelectedRow();
        if (selec != -1 ){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de Eliminar?");
            if (respuesta == 0){
                if (bdusuario.eliminar(lista.get(selec).getCedula())){
                    JOptionPane.showMessageDialog(null, "Datos Eliminados");
                    lista();
                    nuevo();
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Seleccionar el registro a Eliminar");
        }
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
