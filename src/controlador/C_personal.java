/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.*;
import modelo.*;

/**
 *
 * @author PC
 */
public class C_personal {

    public static V_personal vista;
    private Persona_bd bdpersona = new Persona_bd();
    List<Persona_mb> lista = bdpersona.mostrardatos();

    public C_personal(V_personal per) {
        this.vista = per;
        vista.setVisible(true);

        this.vista.setLocationRelativeTo(null);
        this.vista.getBtnGuardar().addActionListener(e -> guardar());
        this.vista.getBtnNuevo().addActionListener(e -> nuevo());
        this.vista.getBtnModificar().addActionListener(e -> modificar());
        this.vista.getBtnEliminar().addActionListener(e -> eliminar());
        this.vista.getBtnFoto().addActionListener(e -> obtieneImagen());
        this.vista.getBtnSalir().addActionListener(e -> salir());
        this.vista.getBtnUsuario().addActionListener(e -> vistaUsuario());
        this.vista.getTabla().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionar();
            }
        });
        lista();
    }
    
    public void vistaUsuario(){
        V_usuario v=new V_usuario();
        C_usuario c=new C_usuario(v);
    }
    
    public void salir(){
        vista.setVisible(false);
    }

    public void lista() {
        DefaultTableModel modelo; //crear el modelo
        modelo = (DefaultTableModel) vista.getTabla().getModel(); //acepta la vista de la tabla
        int columnas = modelo.getColumnCount();
//****** Limpiar en decremento   ********
        for (int j = vista.getTabla().getRowCount() - 1; j >= 0; j--) {
            modelo.removeRow(j);
        }
//***********************
        List<Persona_mb> lista = bdpersona.mostrardatos();
        for (int i = 0; i < lista.size(); i++) {
            modelo.addRow(new Object[columnas]);
            vista.getTabla().setValueAt(lista.get(i).getCedula(), i, 0);
            vista.getTabla().setValueAt(lista.get(i).getNombre(), i, 1);
            vista.getTabla().setValueAt(lista.get(i).getApellido(), i, 2);
            vista.getTabla().setValueAt(lista.get(i).getDireccion(), i, 3);
            vista.getTabla().setValueAt(lista.get(i).getEmail(), i, 4);

        }

    }

    //metodo para obtener la imagen
    private void obtieneImagen() {
        vista.getLblFoto().setIcon(null);
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int estado = j.showOpenDialog(null);
        if (estado == JFileChooser.APPROVE_OPTION) {
            try {
                Image icono = ImageIO.read(j.getSelectedFile()).getScaledInstance(vista.getLblFoto().getWidth(), vista.getLblFoto().getHeight(), Image.SCALE_DEFAULT);
                vista.getLblFoto().setIcon(new ImageIcon(icono));
                vista.getLblFoto().updateUI();
            } catch (IOException ex) {
                Logger.getLogger(C_personal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void guardar() {
        String cedula = vista.getTxtCedula().getText();
        List<Persona_mb> listauto = bdpersona.obtenerdatos(cedula);
        if (vista.getTxtCedula().getText().isEmpty()
                || vista.getTxtNombre().getText().isEmpty() || vista.getTxtApellido().getText().isEmpty() || vista.getTxtEmail().getText().isEmpty() || vista.getTxtDireccion().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No dejar campos vacios");
        } else {
            if (listauto.isEmpty()) {
                bdpersona.setCedula(this.vista.getTxtCedula().getText());
                bdpersona.setNombre(this.vista.getTxtNombre().getText());
                bdpersona.setApellido(this.vista.getTxtApellido().getText());
                bdpersona.setDireccion(this.vista.getTxtDireccion().getText());
                bdpersona.setEmail(this.vista.getTxtEmail().getText());

                ImageIcon ic = (ImageIcon) vista.getLblFoto().getIcon();
                bdpersona.setFoto(ic.getImage());
                if (bdpersona.insertar()) {
                    JOptionPane.showMessageDialog(null, "Guardado Correctamente");
                    lista();
                    nuevo();
                } else {
                    JOptionPane.showMessageDialog(null, "Error Al Guardar");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El registro ya existe");
            }
        }
    }

    public void modificar() {
        int selec = vista.getTabla().getSelectedRow();
        List<Persona_mb> lista = bdpersona.mostrardatos();
        if (selec != -1) {
            bdpersona.setCedula(this.vista.getTxtCedula().getText());
            bdpersona.setNombre(this.vista.getTxtNombre().getText());
            bdpersona.setApellido(this.vista.getTxtApellido().getText());
            bdpersona.setDireccion(this.vista.getTxtDireccion().getText());
            bdpersona.setEmail(this.vista.getTxtEmail().getText());

            ImageIcon ic = (ImageIcon) vista.getLblFoto().getIcon();
            bdpersona.setFoto(ic.getImage());
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de Modificar?");
            if (respuesta == 0) {
                if (bdpersona.modificar(lista.get(selec).getCedula())) {
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
        vista.getTxtCedula().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtApellido().setText("");
        vista.getTxtEmail().setText("");
        vista.getTxtDireccion().setText("");
        vista.getLblFoto().setIcon(null);

    }

    public void seleccionar() {
        this.vista.getBtnGuardar().setEnabled(true);
        List<Persona_mb> lista = bdpersona.mostrardatos();
        int selec = vista.getTabla().getSelectedRow();
        Image img = lista.get(selec).getFoto();
        if (img != null) {
            Image newimg = img.getScaledInstance(vista.getLblFoto().getWidth(), vista.getLblFoto().getHeight(), java.awt.Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(newimg);
            vista.getLblFoto().setIcon(icon);
        } else {
            vista.getLblFoto().setIcon(null);
        }
        if (selec != -1) {
            bdpersona.setCedula(lista.get(selec).getCedula());
            vista.getTxtCedula().setText(bdpersona.getCedula());
            bdpersona.setNombre(lista.get(selec).getNombre());
            vista.getTxtNombre().setText(bdpersona.getNombre());
            bdpersona.setApellido(lista.get(selec).getApellido());
            vista.getTxtApellido().setText(bdpersona.getApellido());
            bdpersona.setDireccion(lista.get(selec).getDireccion());
            vista.getTxtDireccion().setText(bdpersona.getDireccion());
            bdpersona.setEmail(lista.get(selec).getEmail());
            vista.getTxtEmail().setText(bdpersona.getEmail());

        }

    }

    public void eliminar() {
        int selec = vista.getTabla().getSelectedRow();
        if (selec != -1) {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de Eliminar?");
            if (respuesta == 0) {
                if (bdpersona.eliminar(lista.get(selec).getCedula())) {
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
