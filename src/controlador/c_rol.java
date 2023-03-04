
package controlador;

import static controlador.c_usuario.vista;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Rol_mb;
import modelo.Usuario_mb;
import vista.*;
import modelo.*;
public class c_rol {
    public static v_rol vista;
    private Rol_bd bdrol = new Rol_bd();
     public c_rol(v_rol rol) {
        this.vista = rol;
        vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
        this.vista.getBtnGuardar().addActionListener(e -> guardar());
        this.vista.getBtnNuevo().addActionListener(e -> nuevo());
        this.vista.getBtnModificar().addActionListener(e -> modificar());
        this.vista.getBtnEliminar().addActionListener(e -> eliminar());
     }
     
     public void lista() {
        DefaultTableModel modelo; //crear el modelo
        modelo = (DefaultTableModel) vista.getTabla().getModel(); //acepta la vista de la tabla
        List<Rol_mb> lista = bdrol.mostrardatos();// carga mis datos
        int columnas = modelo.getColumnCount();
        for (int j = vista.getTabla().getRowCount() - 1; j >= 0; j--) {
            modelo.removeRow(j);
        }
        for (int i = 0; i < lista.size(); i++) {
            modelo.addRow(new Object[columnas]);
            vista.getTabla().setValueAt(lista.get(i).getCodigo(), i, 0);
            vista.getTabla().setValueAt(lista.get(i).getNombre(), i, 1);
             vista.getTabla().setValueAt(lista.get(i).getDescripcion(), i, 2);
            
            int codigorol = lista.get(i).getCodigo_rol();
            List<Rol_mb> listatipocod = bdrol.mostrardatoscodigo(codigorol);
            vista.getTabla().setValueAt(listatipocod.get(0).getNombre(), i, 2);


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
     
     
     
     
}
