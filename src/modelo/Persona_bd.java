/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class Persona_bd extends Persona_mb {

    Conect conectar = new Conect();

    public Persona_bd(String cedula, String nombre, String apellido, String direccion, String email, Image foto) {
        super(cedula, nombre, apellido, direccion, email, foto);
    }

    public Persona_bd() {
    }

    public List<Persona_mb> mostrardatos() {
        try {
            List<Persona_mb> lista = new ArrayList<>();
            String sql = "SELECT * FROM persona";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {

                Persona_mb per = new Persona_mb();
                per.setCedula(rs.getString("cedula"));
                per.setNombre(rs.getString("nombre"));
                per.setApellido(rs.getString("apellido"));
                per.setDireccion(rs.getString("direccion"));
                per.setEmail(rs.getString("email"));
                byte[] is;
                is = rs.getBytes("foto");
                if (is != null) {
                    try {
                        is = Base64.getDecoder().decode(is);
//                      BufferedImage bi=Base64.decode( ImageIO.read(is));
                        per.setFoto(getImage(is, false));
                    } catch (Exception ex) {
                        per.setFoto(null);
                        Logger.getLogger(Persona_bd.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    per.setFoto(null);
                }
                lista.add(per);
            }
            rs.close();
            return lista;
        } catch (SQLException e) {
            Logger.getLogger(Persona_bd.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }//Fin mostrar datos

    private Image getImage(byte[] bytes, boolean isThumbnail) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("png");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        if (isThumbnail) {
            param.setSourceSubsampling(4, 4, 0, 0);
        }
        return reader.read(0, param);
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public boolean insertar() {
        ///para insertar la foto
        String ef = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            BufferedImage img = toBufferedImage(getFoto());
            ImageIO.write(img, "PNG", bos);
            byte[] imgb = bos.toByteArray();
            ef = Base64.getEncoder().encodeToString(imgb);
        } catch (IOException ex) {
            Logger.getLogger(Persona_bd.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "INSERT INTO persona(cedula,nombre,apellido,direccion,email,foto)"
                + "VALUES ('"
                + getCedula() + "','"
                + getNombre() + "','"
                + getApellido() + "','"
                + getDireccion() + "','"
                + getEmail() + "','"
                + ef + "')";

        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("Error");
            return false;
        }
    }//Fin de insertar

    public boolean modificar(String cedula) {
        String ef = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            BufferedImage img = toBufferedImage(getFoto());
            ImageIO.write(img, "PNG", bos);
            byte[] imgb = bos.toByteArray();
            ef = Base64.getEncoder().encodeToString(imgb);
        } catch (IOException ex) {
            Logger.getLogger(Persona_bd.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "Update persona set "
                + "cedula = '" + getCedula() + "', "
                + "nombre = '" + getNombre() + "', "
                + "apellido = '" + getApellido() + "', "
                + "direccion = '" + getDireccion() + "', "
                + "email = '" + getEmail() + "', "
                + "foto = '" + ef + "' "
                + " Where cedula = '" + getCedula() + "'";

        if (conectar.noQuery(sql) == null) {
            return true;
        } else {
            System.out.println("error al editar");
            return false;
        }
    }//Fin del modificar

    public boolean eliminar(String cedula) {
        String nsql = "Delete from persona Where cedula = '" + cedula + "'";
        if (conectar.noQuery(nsql) == null) {
            return true;
        } else {
            JOptionPane.showConfirmDialog(null, "Error al eliminar");
            return false;
        }
    }//Fin de eliminar

    public List<Persona_mb> obtenerdatos(String cedula) {
        try {
            List<Persona_mb> lista = new ArrayList<>();
            String sql = "SELECT * FROM persona Where cedula = '" + cedula + "'";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {

                Persona_mb per = new Persona_mb();
                per.setCedula(rs.getString("cedula"));
                per.setNombre(rs.getString("nombre"));
                per.setApellido(rs.getString("apellido"));
                per.setDireccion(rs.getString("direccion"));
                per.setEmail(rs.getString("email"));
                byte[] is;
                is = rs.getBytes("foto");
                if (is != null) {
                    try {
                        is = Base64.getDecoder().decode(is);
//                      BufferedImage bi=Base64.decode( ImageIO.read(is));
                        per.setFoto(getImage(is, false));
                    } catch (Exception ex) {
                        per.setFoto(null);
                        Logger.getLogger(Persona_bd.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    per.setFoto(null);
                }
                lista.add(per);
            }
            rs.close();
            return lista;
        } catch (SQLException e) {
            Logger.getLogger(Persona_bd.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

    }
    
        public boolean verificarExistente(String cedula) {

        try {
            List<Persona_mb> lista = new ArrayList<>();
            String sql = "SELECT * FROM persona where cedula= '" + cedula + "'";
            ResultSet rs = conectar.query(sql);
            while (rs.next()) {

                Persona_mb per = new Persona_mb();
                per.setCedula(rs.getString("cedula"));
                per.setNombre(rs.getString("nombre"));
                per.setApellido(rs.getString("apellido"));
                per.setDireccion(rs.getString("direccion"));
                per.setEmail(rs.getString("email"));

                lista.add(per);
            }
            rs.close();
            if (lista.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(Usuario_bd.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        }
}
