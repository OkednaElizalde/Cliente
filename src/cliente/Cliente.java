/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;
import javax.swing.*;
/**
 *
 * @author Nadia
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Interfaz obj=new Interfaz();
        obj.setVisible(true);
        /*String res=JOptionPane.showInputDialog(null, "Si quieres agregar un libro a la biblioteca presiona -A, si quieres buscar un libro presiona -B");
        switch (res){
            case "A": 
                obj = new Interfaz(true);
                obj.setVisible(true);
            break;
            case "B":
                obj = new Interfaz(false);
                obj.setVisible(true);
            break;
            
        }
        */ 
        
    }
    
}
