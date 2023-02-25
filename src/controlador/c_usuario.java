
package controlador;

import  controlador.*;
import vista.*;

public class c_usuario {
    public static V_usuario vista;
    
     public c_usuario(V_usuario usuario) {
        this.vista = usuario;
        vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
     }
}
