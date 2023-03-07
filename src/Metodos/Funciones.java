/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;
 import java.util.UUID;

/**
 *
 * @author PC
 */
public class Funciones {
   

    public static int generateUniqueId() {
        UUID uuid = UUID.randomUUID();
        return Math.abs(uuid.hashCode());
    }

}
