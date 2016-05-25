/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *insert into Java.Tabla1(nombre,edad) values ("hugo", 19),("lluis",30),("pere",15);
 * @author hugo
 */
public class CBD {
    
    //declarar Objetos coenxion
    Connection miConexion;
    PreparedStatement  consulta;
    ResultSet datos;
    
    //clase crear obtenerConexion
    public Connection getConnection(String BBDD, String usuario, String contraseña)
    {
        try {
            //llamar a al clase drive de jdbc
            Class.forName("com.mysql.jdbc.Driver");
            //despes // iria dirrecion si es web pues web y la BBDD especifica
            String servidor = "jdbc:mysql://localhost:3306/"+BBDD;
            
            miConexion = (Connection) DriverManager.getConnection(servidor, usuario, contraseña);
        } catch (ClassNotFoundException e  ) {
            JOptionPane.showMessageDialog(null,"No se encontroDriver");
                miConexion=null;
        }catch ( SQLException ex){
                JOptionPane.showMessageDialog(null,"No se pudo conectar BBDD"+ex);
                miConexion=null;
             }
        return miConexion;
    }
    
    
    
    public void insertar()
    {
        try {
            //conexion BBDD especifica
            miConexion= (Connection) this.getConnection("Java","root","antonia");
            String insertTableSQL = "insert into Tabla1(nombre,edad) values"
		+ "(?,?)";
            // enviar consulta
            consulta=miConexion.prepareStatement(insertTableSQL);
            consulta.setInt(2, 11);
            consulta.setString(1, "mkyong");
            
            //ejecutar consulta y guardar consulta executeUpdate();
            //datos= consulta.executeQuery();
            consulta.executeUpdate();
            //mostrar los datos siguiente en siguiente
         
        }catch ( SQLException ex){
                JOptionPane.showMessageDialog(null,"No se pudo hacer insertar " +ex);
        }finally{
            //para descoenctar todo
            this.desconectar();
            consulta();
        }
    }
    
    
    
    
    
    public void consulta()
    {
        try {
            //conexion BBDD especifica
            miConexion= (Connection) this.getConnection("Java","root","antonia");
            // enviar consulta
            consulta=miConexion.prepareStatement("SELECT nombre,edad FROM Java.Tabla1");
            //ejecutar consulta y guardar consulta
            datos= consulta.executeQuery();
            //mostrar los datos siguiente en siguiente
            while(datos.next())
            {
                System.out.println("nombre: "+ datos.getString("nombre")+"  --- edad: "+datos.getInt("edad"));
            }  
        }catch ( SQLException ex){
                JOptionPane.showMessageDialog(null,"No se pudo hacer consulta " +ex);
        }finally{
            //para descoenctar todo
            this.desconectar();
        }
    }
    
    public void desconectar()
    {
        try {
            this.miConexion.close();
            this.consulta.close();
            this.datos= null;
            
        } catch ( SQLException ex){
                JOptionPane.showMessageDialog(null,"No se pudo cerrar");
        }
    }
}
