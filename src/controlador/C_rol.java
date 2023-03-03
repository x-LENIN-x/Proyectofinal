package controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.*;
import modelo.*;

public class C_rol {

    public static V_rol vista;
    public Rol_bd bdrol = new Rol_bd();
    List<Rol_mb> lista = bdrol.mostrardatos();

    public C_rol(V_rol rol) {
        this.vista = rol;
        vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
        this.vista.getBtnGuardar().addActionListener(e->guardar());
        this.vista.getBtnNuevo().addActionListener(e->nuevo());
        this.vista.getBtnModificar().addActionListener(e->modificar());
        this.vista.getBtnEliminar().addActionListener(e-> eliminar());
         this.vista.getTabla().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionar();
              
            }  
        });
        lista();     
    }

    public void lista() {
        DefaultTableModel modelo; //crear el modelo
        modelo = (DefaultTableModel) vista.getTabla().getModel(); //acepta la vista de la tabla
        List<Rol_mb> lista = bdrol.mostrardatos();  // carga mis datos
        int columnas = modelo.getColumnCount();
//****** Limpiar en decremento   ********
        for (int j = vista.getTabla().getRowCount() - 1; j >= 0; j--) {
            modelo.removeRow(j);
        }
//***********************
        for (int i = 0; i < lista.size(); i++) {
            modelo.addRow(new Object[columnas]);
            vista.getTabla().setValueAt(lista.get(i).getCodigo(), i, 0);
            vista.getTabla().setValueAt(lista.get(i).getNombre(), i, 1);
            vista.getTabla().setValueAt(lista.get(i).getDescripcion(), i, 2);
            vista.getTabla().setValueAt(lista.get(i).getCodigo_permiso(), i, 3);

        }
        
    

    }

    public void guardar() {

        if (vista.getCbPermiso().getSelectedItem().toString().isEmpty()
                || vista.getTxtNombre().getText().isEmpty() || vista.getTxtDescripcion().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No dejar campos vacios");
        } else {
            int codigo = (int) (Math.random() * 1000 + 1);
            bdrol.setCodigo(codigo);
            bdrol.setNombre(this.vista.getTxtNombre().getText());
            bdrol.setDescripcion(this.vista.getTxtDescripcion().getText());
            
            bdrol.setCodigo_permiso(Integer.parseInt(this.vista.getCbPermiso().getSelectedItem().toString()));

            if (bdrol.insertar()) {
                JOptionPane.showMessageDialog(null, "Guardado Correctamente");
                lista();
                nuevo();
            } else {
                JOptionPane.showMessageDialog(null, "Error Al Guardar");
            }
        }

    }

    public void modificar() {
        int selec = vista.getTabla().getSelectedRow();

        if (selec != -1) {
            bdrol.setCodigo(Integer.parseInt(this.vista.getTxtCodigo().getText()));
            bdrol.setNombre(this.vista.getTxtNombre().getText());
            bdrol.setDescripcion(this.vista.getTxtDescripcion().getText());
            bdrol.setCodigo_permiso(Integer.parseInt(this.vista.getCbPermiso().getSelectedItem().toString()));

            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de Modificar?");
            if (respuesta == 0) {
                if (bdrol.modificar(lista.get(selec).getCodigo())) {
                    JOptionPane.showMessageDialog(null, "Datos Actualizados");
                    lista();
                    nuevo();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccionar el registro a Modificar");
        }
    }

    public void nuevo() {
        vista.getTxtCodigo().setText("");
        vista.getCbPermiso().setSelectedIndex(0);
        vista.getTxtNombre().setText("");
        vista.getTxtDescripcion().setText("");

    }

    public void seleccionar() {
        this.vista.getBtnGuardar().setEnabled(true);
        int selec = vista.getTabla().getSelectedRow();
        if (selec != -1) {
            bdrol.setCodigo(Integer.parseInt(this.vista.getTxtCodigo().getText()));
            vista.getTxtCodigo().setText(bdrol.getCodigo() + "");
            bdrol.setNombre(this.vista.getTxtNombre().getText());
            vista.getTxtNombre().setText(bdrol.getNombre());
            bdrol.setCodigo_permiso(Integer.parseInt(this.vista.getCbPermiso().getSelectedItem().toString()));
            vista.getCbPermiso().setSelectedItem(bdrol.getCodigo_permiso());
            bdrol.setDescripcion(this.vista.getTxtDescripcion().getText());
            vista.getTxtDescripcion().setText(bdrol.getDescripcion());
        }

    }

    public void eliminar() {
        int selec = vista.getTabla().getSelectedRow();
        if (selec != -1) {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de Eliminar?");
            if (respuesta == 0) {
                if (bdrol.eliminar((lista.get(selec).getCodigo()))) {
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
