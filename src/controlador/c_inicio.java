package controlador;

import java.util.List;
import vista.*;
import modelo.*;

public class c_inicio {

    public static v_incio vista;
    usuario_bd bdusuario = new usuario_bd();

    public c_inicio(v_incio inicio) {
        this.vista = inicio;
        vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
        this.vista.getBtn_ingresar().addActionListener(e -> ingresar());
        this.vista.getBtn_salir().addActionListener(e -> salir());
    }

    public void ingresar() {
        List<usuario_mb> listau = bdusuario.mostrardatos();
        for (int i = 0; i < listau.size(); i++) {
            if (vista.getTxt_usuario().getText().equals(listau.get(i).getNombre()) && vista.getTxt_clave().getText().equals(listau.get(i).getPassword())) {
                v_menu m = new v_menu();
                c_menu c = new c_menu(m);
                this.vista.dispose();
            }
        }
       
    }

    private void salir() {
        System.exit(0);
    }
}
