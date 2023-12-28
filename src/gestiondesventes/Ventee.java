/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gestiondesventes;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class Ventee extends javax.swing.JFrame {

    /**
     * Creates new form Ventee
     */
      Connexionsql bd;
    Connection con ;
       Statement st;
	ResultSet rst;
            PreparedStatement ps;
  public Ventee() {
      bd=new Connexionsql();
           con=bd.getcon();
        initComponents();
		try{
                    ps=con.prepareStatement("select NomProduit from produit");
		    ResultSet rs = ps.executeQuery();
                    rs=ps.executeQuery();
                   
			while(rs.next()){
			jComboBox1.addItem(rs.getString("NomProduit"));
	
			}
		}
		catch(Exception ex){
			  System.out.println("");
		}
                
                
                 

   btnajouter.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        String a, b, c;

        a = idv.getText();
        b = jComboBox1.getSelectedItem().toString();
        c = qte.getText();

        // Ajout vente
        String insertQuery = "INSERT INTO vente(idv, NomProduit, quantite, datev) VALUES (?, ?, ?, NOW())";
        // Reduction automatique du nombre d'un produit lors d'une vente
        String updateQuery = "UPDATE produit SET quantite = quantite - ? WHERE NomProduit = ?";

        try {
            PreparedStatement insertStatement = con.prepareStatement(insertQuery);
            insertStatement.setString(1, a);
            insertStatement.setString(2, b);
            insertStatement.setString(3, c);
            insertStatement.executeUpdate();

            PreparedStatement updateStatement = con.prepareStatement(updateQuery);
            updateStatement.setString(1, c);
            updateStatement.setString(2, b);
            updateStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Vente enregistrée!");
            dispose();
            Ventee sl = new Ventee();
            sl.setVisible(true);
        } catch (Exception ex) {
            System.out.println("Impossible d'exécuter la requête: " + ex);
        }
    }
});
   
   
   btnproduits.addMouseListener(new MouseAdapter(){
       
        public void mouseClicked(MouseEvent e){
            
        dispose();
	produit pr=new produit();
	pr.setVisible(true);
        }
       
        });
    
   btnajouterrequettes.addMouseListener(new MouseAdapter(){
       
        public void mouseClicked(MouseEvent e){
            
        dispose();
	MontantTotal pr=new MontantTotal();
	pr.setVisible(true);
        }
       
        });
    
   
   btnok.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        try {
            ps = con.prepareStatement("SELECT vente.idv, SUM(vente.quantite * produit.PrixProduit) AS montant_total\n" +
"FROM vente\n" +
"JOIN produit ON vente.NomProduit = produit.NomProduit\n" +
"WHERE vente.idv = ?\n" +
"GROUP BY vente.idv\n" +
"ORDER BY vente.idv;");
            ps.setString(1, idvtotal.getText());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String mtotal = rs.getString("montant_total");
                lbtotal.setText("TOTAL = " + mtotal + " $");
            } else {
                lbtotal.setText("No data found for idv = " + idvtotal.getText());
            }
        }catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
});

   
   
    btnsupprimer.addMouseListener(new MouseAdapter(){
       
        public void mouseClicked(MouseEvent e){
            try {
                
                ps=con.prepareStatement("  delete from vente where idv= ? and NomProduit=? ;");
                ps.setString(1,idv.getText());
                  ps.setString(2,  jComboBox1.getSelectedItem().toString());
              
                if(JOptionPane.showConfirmDialog(null,"Voulez-vous supprimer ?",null,JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
		ps.executeUpdate();
		JOptionPane.showMessageDialog(null,"Suppr�ssion r�ussie !");
		  dispose();
		Ventee pr=new Ventee();
		pr.setVisible(true);			
                }
               
            } catch (Exception ex) {
                  JOptionPane.showMessageDialog(null," Echec suppr�ssion !",null,JOptionPane.ERROR_MESSAGE);
            }
        }
       
        });
    
    
    try {
    ps = con.prepareStatement("SELECT vente.idv, produit.NomProduit, vente.quantite, produit.PrixProduit, vente.quantite * produit.PrixProduit AS montant, vente.datev " +
                             "FROM produit INNER JOIN vente ON produit.NomProduit = vente.NomProduit " +
                             "ORDER BY vente.idv DESC");
    
    ResultSet rs = ps.executeQuery();
    ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();

    // Clear existing table data
    DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
    dtm.setRowCount(0);

    int cols = rsmd.getColumnCount();
    Vector<String> colNames = new Vector<>();
    for (int i = 0; i < cols; i++) {
        colNames.add(rsmd.getColumnName(i + 1));
    }
    dtm.setColumnIdentifiers(colNames);

    // Add column names to the first row of the table
    dtm.addRow(colNames);

    while (rs.next()) {
        Vector<String> rowData = new Vector<>();
        rowData.add(rs.getString("idv"));
        rowData.add(rs.getString("NomProduit"));
        rowData.add(rs.getString("quantite"));
        rowData.add(rs.getString("PrixProduit"));
        rowData.add(rs.getString("montant"));
        rowData.add(rs.getString("datev"));

        dtm.addRow(rowData);
    }
} catch (Exception ex) {
    System.out.println("Unable to execute the query: " + ex);
}

                
    
    
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        idv = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        qte = new javax.swing.JTextField();
        btnajouter = new javax.swing.JButton();
        btnsupprimer = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        idvtotal = new javax.swing.JTextField();
        btnok = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnproduits = new javax.swing.JButton();
        btnajouterrequettes = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        lbtotal = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Enregistrement d'une vente ");

        jLabel2.setText("Id vente ");

        jLabel3.setText("Nom produit");

        jLabel4.setText("Quantite");

        btnajouter.setText("Enregistrer");
        btnajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnajouterActionPerformed(evt);
            }
        });

        btnsupprimer.setText("Supprimer");

        jLabel5.setText("Montant total des produits d'une vente ");

        jLabel6.setText("id vente");

        btnok.setText("ok");

        btnproduits.setText("Produits");

        btnajouterrequettes.setText("Requettes Globales");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "id vente ", "Nom Produit", "Quantite", "Prix unitaire", "Montant", "Date "
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(qte, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(53, 53, 53)
                                .addComponent(idv, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(idvtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5))))
                .addGap(18, 18, 18)
                .addComponent(btnok, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnajouter)
                        .addGap(50, 50, 50)
                        .addComponent(btnsupprimer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                        .addComponent(btnproduits)
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnajouterrequettes)
                            .addComponent(lbtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(66, 66, 66))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(idv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(idvtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnok))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel7)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(qte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnajouter)
                    .addComponent(btnsupprimer)
                    .addComponent(btnproduits)
                    .addComponent(btnajouterrequettes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnajouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnajouterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnajouterActionPerformed

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
            java.util.logging.Logger.getLogger(Ventee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnajouter;
    private javax.swing.JButton btnajouterrequettes;
    private javax.swing.JButton btnok;
    private javax.swing.JButton btnproduits;
    private javax.swing.JButton btnsupprimer;
    private javax.swing.JTextField idv;
    private javax.swing.JTextField idvtotal;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lbtotal;
    private javax.swing.JTextField qte;
    // End of variables declaration//GEN-END:variables
}
