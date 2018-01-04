/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageviewer.controller;

import imageviewer.view.gui;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class ImageViewer {

    static List<ImageIcon> imagenes;
    static File directorio;
    static String nombreImagen;
    static ImageIcon imagen;
    static Image img;
    static Image img2;
    static double prop;
    static int newAl, newAn;

    static String[] extens = new String[]{"png","jpg","jpeg"};
    
    public static void main(String[] args) throws IOException {
        pideDirectorio();
        getImg(directorio);
        new gui().setVisible(true);
        // TODO code application logic here
    }
    
    public static File getDirectorio(){
        return directorio;
    }
     public static List<ImageIcon> getImagenes(){
        return imagenes;
     }
    static FilenameFilter filtro = (final File dir1, final String name) -> {
        for (final String ext : extens) {
            if (name.endsWith("." + ext)) {
                return (true);
            }
        }
        return (false);
};
    
    public static List<ImageIcon> getImg(File directorio) throws IOException{
        imagenes = new ArrayList<>();
        for(File fil : directorio.listFiles(filtro)){
            BufferedImage bimg = ImageIO.read(fil);
            nombreImagen = fil.getName();
            imagen = new ImageIcon(directorio+"\\"+nombreImagen);
            boolean mayor= false;
            if(imagen.getIconHeight()> gui.getHe()){
                mayor=true;
                prop = (double) gui.getHe()/(double) imagen.getIconHeight();
                newAn = (int) (imagen.getIconWidth()*prop);
                newAl = (int) (imagen.getIconHeight()*prop);
                if(newAn > gui.getWi()){
                    prop = (double) gui.getWi()/(double) imagen.getIconWidth();
                    newAn = (int) (imagen.getIconWidth()*prop);
                    newAl = (int) (imagen.getIconHeight()*prop);
                    
                
                }
            }else if((imagen.getIconWidth() > gui.getWi())){
                mayor=true;
                prop = (double) gui.getWi()/(double) imagen.getIconWidth();
                newAn = (int) (imagen.getIconWidth()*prop);
                newAl = (int) (imagen.getIconHeight()*prop);
            }
            if(mayor){
                img2=imagen.getImage();
                img2 = img2.getScaledInstance(newAn, newAl, img2.SCALE_SMOOTH);
                imagen = new ImageIcon(img2);
            }
            imagenes.add(imagen);        
        }
        
        return imagenes;
    }
    
    public static void pideDirectorio(){
        directorio = new File(JOptionPane.showInputDialog(null, "Introduzca el directorio", "Nuevo directorio", JOptionPane.PLAIN_MESSAGE));
        if (!directorio.isDirectory()){
                JOptionPane.showMessageDialog(null, "El directorio no es valido", "Error", JOptionPane.ERROR_MESSAGE);
                pideDirectorio();
        }
    
    }
    
}
