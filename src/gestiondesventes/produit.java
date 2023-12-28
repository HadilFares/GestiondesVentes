/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gestiondesventes;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 21653
 */
public class produit extends javax.swing.JFrame {
    Connexionsql bd;
    Connection con ;
    PreparedStatement ps;
    /**
     * Creates new form produit
     */
    public produit() {
         bd=new Connexionsql();
           con=bd.getcon();
        initComponents();
        try{
        ps=con.prepareStatement("select * from produit;");
                    ResultSet rs = ps.executeQuery();
                    rs=ps.executeQuery();
                   
	                   
	                ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
	                    
	                    // Clear existing table data
	            DefaultTableModel   dtm = (DefaultTableModel) jTable1.getModel();
	                    dtm.setRowCount(0);

	                    int cols = rsmd.getColumnCount();
	                    Vector<String> colNames = new Vector<>();
	                    for (int i = 0; i < cols; i++) {
	                        colNames.add(rsmd.getColumnName(i + 1));
	                    }
	                    dtm.setColumnIdentifiers(colNames);

	                    // Add column names to the first row of the table
	                    dtm.addRow(colNames);
                              
	                    while (rs.next()){
                             jComboBox1.addItem(rs.getString("NomProduit"));

	                        Vector<String> rowData = new Vector<>();
	                        rowData.add(rs.getString("NomProduit"));
	                        rowData.add(rs.getString("PrixProduit"));
	                        rowData.add(rs.getString("quantite"));
	                        
	                        dtm.addRow(rowData);
	                    } 
                    
	                 
                    
                    




        }catch (Exception ex){
                   System.out.println();
                }
    
        
        Btnenregistrer.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
               try {
               ps= con.prepareStatement("insert into produit(NomProduit,prixProduit) values(?,?);");
                ps.setString(1,textfinomprod.getText());
                 ps.setString(2,jTextFilprix.getText());
                 // ps.setString(3,jTextFiquant.getText());
                   jComboBox1.addItem(textfinomprod.getText());
                        
                 
                  ps.executeUpdate();
                  System.out.println("requete executée avec succes");
                  int n=Integer.parseInt(textfinomprod.getText());
                  n++;
                textfinomprod.setText(""+n);
                JOptionPane.showMessageDialog(null,
                "inserer avec succés",
                "Success ",JOptionPane.INFORMATION_MESSAGE);
             
               
               }catch (Exception ex){
                   System.out.println("impossible d' executée la requete: "+ex);
                }
            }
               
       });
      
    btnsupprimer.addMouseListener(new MouseAdapter(){
       
        public void mouseClicked(MouseEvent e){
            try {
                ps=con.prepareStatement("delete from produit where NomProduit= ?;");
                ps.setString(1,textfinomprod.getText());
                if(JOptionPane.showConfirmDialog(null,"Voulez-vous supprimer ?",null,JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
		ps.executeUpdate();
		JOptionPane.showMessageDialog(null,"Suppr�ssion r�ussie !");
		  dispose();
						   produit pr=new produit();
						   pr.setVisible(true);			
                }
               
            } catch (Exception ex) {
                   System.out.println("impossible d'executer la requete:  "+ex);
            }
        }
       
        });

vente.addMouseListener(new MouseAdapter(){
       
        public void mouseClicked(MouseEvent e){
            
        dispose();
	Ventee pr=new Ventee();
	pr.setVisible(true);
        }
       
        });
    
       btnretirer.addMouseListener(new MouseAdapter(){
       
        public void mouseClicked(MouseEvent e){
            try {
                
                
                 
                ps=con.prepareStatement("update produit set quantite=quantite - ?  where NomProduit= ?");
               ps.setString(1, jTextFiquant.getText());
               ps.setString(2,  jComboBox1.getSelectedItem().toString());
                if(JOptionPane.showConfirmDialog(null,"Voulez-vous supprimer ?",null,JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
		ps.executeUpdate();
		JOptionPane.showMessageDialog(null,"Suppr�ssion r�ussie !");
		  dispose();
						   produit pr=new produit();
						   pr.setVisible(true);			
                }
               
            } catch (Exception ex) {
                   System.out.println("impossible d'executer la requete:  "+ex);
            }
        }
       
        });
    
btnajouter.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        try {
            // Update the quantite column in the database
            ps = con.prepareStatement("update produit set quantite = ? where NomProduit = ?;");
            ps.setString(1, jTextFiquant.getText());
            ps.setString(2, jComboBox1.getSelectedItem().toString());
            ps.executeUpdate();
            
            // Fetch updated data from the database
            ps = con.prepareStatement("select * from produit;");
            ResultSet rs = ps.executeQuery();

            ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

            int cols = rsmd.getColumnCount();
            Vector<String> colNames = new Vector<>();
            for (int i = 0; i < cols; i++) {
                colNames.add(rsmd.getColumnName(i + 1));
            }
            dtm.setColumnIdentifiers(colNames);
            dtm.addRow(colNames);

            // Populate jTable1 with the updated data
            while (rs.next()) {
                jComboBox1.addItem(rs.getString("NomProduit"));

                Vector<String> rowData = new Vector<>();
                rowData.add(rs.getString("NomProduit"));
                rowData.add(rs.getString("PrixProduit"));
                rowData.add(rs.getString("quantite"));

                dtm.addRow(rowData);
            }

            System.out.println("Update executed successfully.");
            JOptionPane.showMessageDialog(null, "Updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            System.out.println("Unable to execute the query: " + ex);
        }
    }
});


      }
          
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtitle = new javax.swing.JLabel();
        jtextprod = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textfinomprod = new javax.swing.JTextField();
        jTextFilprix = new javax.swing.JTextField();
        Btnenregistrer = new javax.swing.JButton();
        btnsupprimer = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jtextquant = new javax.swing.JLabel();
        nomprodquan = new javax.swing.JLabel();
        jtextnombre = new javax.swing.JLabel();
        jTextFiquant = new javax.swing.JTextField();
        btnajouter = new javax.swing.JButton();
        btnretirer = new javax.swing.JButton();
        btnvente = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        vente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 255, 0));

        jtitle.setText("Enregistrement des produits du supermarché  ");

        jtextprod.setText("Nom produit ");

        jLabel2.setText("Prix produit");

        Btnenregistrer.setLabel("Enregistrer");
        Btnenregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnenregistrerActionPerformed(evt);
            }
        });

        btnsupprimer.setLabel("Supprimer");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nom produit ", "prix produit($)", "quantite disponible"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jtextquant.setText("Ajouter la quantite d'un produit ");

        nomprodquan.setText("Nom produit ");

        jtextnombre.setText("Nombre");

        btnajouter.setText("Ajouter ");

        btnretirer.setText("retirer");

        btnvente.setText("Vente");

        vente.setText("Vente");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Btnenregistrer)
                .addGap(50, 50, 50)
                .addComponent(btnsupprimer)
                .addGap(295, 295, 295))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtextquant, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFilprix, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jtextprod)
                                        .addGap(67, 67, 67)
                                        .addComponent(textfinomprod, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 327, Short.MAX_VALUE)
                                .addComponent(vente))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(btnajouter)
                                .addGap(71, 71, 71)
                                .addComponent(btnretirer)
                                .addGap(75, 75, 75)
                                .addComponent(btnvente))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nomprodquan, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtextnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(68, 68, 68)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox1, 0, 96, Short.MAX_VALUE)
                                    .addComponent(jTextFiquant))))))
                .addGap(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jtitle)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtextprod)
                            .addComponent(textfinomprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(vente)))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFilprix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnsupprimer)
                    .addComponent(Btnenregistrer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtextquant)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nomprodquan)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtextnombre)
                        .addContainerGap(98, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFiquant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnvente)
                            .addComponent(btnajouter)
                            .addComponent(btnretirer))
                        .addGap(43, 43, 43))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnenregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnenregistrerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnenregistrerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(produit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(produit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(produit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(produit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new produit().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btnenregistrer;
    private javax.swing.JButton btnajouter;
    private javax.swing.JButton btnretirer;
    private javax.swing.JButton btnsupprimer;
    private javax.swing.JButton btnvente;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFilprix;
    private javax.swing.JTextField jTextFiquant;
    private javax.swing.JLabel jtextnombre;
    private javax.swing.JLabel jtextprod;
    private javax.swing.JLabel jtextquant;
    private javax.swing.JLabel jtitle;
    private javax.swing.JLabel nomprodquan;
    private javax.swing.JTextField textfinomprod;
    private javax.swing.JButton vente;
    // End of variables declaration//GEN-END:variables
}
