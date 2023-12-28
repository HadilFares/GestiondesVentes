/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestiondesventes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author 21653
 */
public class Connexionsql {
Connection con ;
PreparedStatement ps;
String pilote="com.mysql.cj.jdbc.Driver";
String cheminBD="jdbc:mysql://localhost:3306/mydb";  //localhost:3306/mydb
public Connexionsql()
{
    try {
        Class.forName(pilote);
        System.out.println("Pilote chargé avec succes");
    }catch (ClassNotFoundException ex)
    {
        System.out.println("impossible de charger le pilote associe!");
        
    }
    try {
        con=DriverManager.getConnection(cheminBD,"root","root");
         System.out.println("Connexion a la bd etablie avec succes");
    }catch(Exception ex){
        System.out.println("impossible de se connecter a la bd associee!"+ex);
    }
    
}
public Connection getcon(){
    return con;
}
    public static void main(String[] args) {
        // TODO code application logic here
        Connexionsql con =new Connexionsql();
    }
    
}
   
   
    

