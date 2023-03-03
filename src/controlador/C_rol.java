
package controlador;

import vista.*;

public class C_rol {
        public static v_rol vista;
    
     public C_rol(v_rol rol) {
        this.vista = rol;
        vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
     }
     
     
}
