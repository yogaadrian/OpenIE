/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.ac.itb.gui.config;

import id.ac.itb.gui.alert.Alert;
import id.ac.itb.openie.dataprocessor.IFeatureHandler;
import id.ac.itb.util.UnzipUtility;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;


public class ConfigFeatureDialog extends JFrame {

    ArrayList<Pair< JCheckBox,JLabel>> configComponents = new ArrayList();
    private ArrayList<IFeatureHandler> allFeatures= new ArrayList();
    private PluginManager pluginManager;
    ArrayList<IFeatureHandler> currentFeatures;

    public ConfigFeatureDialog(ArrayList<IFeatureHandler> currentFeatures) {
        pluginManager = new DefaultPluginManager();
        for (Object iHandler: pluginManager.getExtensions(IFeatureHandler.class)){
            IFeatureHandler fh= (IFeatureHandler) iHandler;
            allFeatures.add(fh);
        }
        this.currentFeatures = currentFeatures;
        initComponents();
    }
    
    public boolean isCurrentFeature(IFeatureHandler feature){
        for(IFeatureHandler currentfeature:currentFeatures){
            if(feature.getFeatureName().equals(currentfeature.getFeatureName()))
                return true;
        }
        return false;
    }

    private void loadPlugin() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
Alert loading = new Alert("loading");
            loading.setVisible(true);
            SwingWorker worker = new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    try {
                        String target = System.getProperty("pf4j.pluginsDir", "plugins") + File.separator + selectedFile.getName();
                        File targetZip = new File(target);
                        String UnzipTarget = target.replaceFirst("[.][^.]+$", "");
                        Files.copy(selectedFile.toPath(), targetZip.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        
                        UnzipUtility unzipUtility = new UnzipUtility();
                        unzipUtility.unzip(target, System.getProperty("pf4j.pluginsDir", "plugins"));
                        targetZip.delete();
                        
                        try {
//                    Alert loading = new Alert("load plugin into system");
//                    loading.setVisible(true);

                            String[] cmd = new String[3];
                            cmd[0]="cmd.exe";
                            cmd[1]="/C";
                            cmd[2]="ant";
                            Runtime rt = Runtime.getRuntime();

                            Process pr = rt.exec(cmd);

                            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

                            String line=null;

                            System.out.println("Rebuilding app using ant.");

                            while((line=input.readLine()) != null) {
                                System.out.println(line);
                            }
                            int exitVal = pr.waitFor();
                            
                            //loading.dispose();
                            //alert.dispose();
                            if (exitVal == 0) {
                            //                        new Alert("Plugins loaded successfully.").setVisible(true);
                            loading.dispose();
                             new Alert("Plugins loaded successfully.").setVisible(true);
                            } else {
                                throw new Error("Error loading plugin.");
                            }
                        } catch(Exception e) {
                            System.out.println(e.toString());
                            new Alert(e.getMessage()).setVisible(true);
                        }
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
                
            };
            worker.execute();
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
        addButton = new JButton();
        okButton = new JButton();
        cancelButton = new JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        
        jLabel1.setText("Feature");
        jLabel4.setText("Description");
        
        for (IFeatureHandler feature : allFeatures) {


            JCheckBox cb = new JCheckBox();
            JLabel jLabel = new JLabel();
            cb.setText(feature.getFeatureName());
            jLabel.setText("<html>"+feature.getDescription()+"</html>");
            
            cb.setSelected(isCurrentFeature(feature));
            cb.setMaximumSize(new java.awt.Dimension(232, 2147483647));

            configComponents.add(Pair.of(cb, jLabel));
        }

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        addButton.setText("Add more feature");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        
        okButton.setText("Save");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        
        GroupLayout.ParallelGroup horizontalPrlGroupA = jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);

        for (Pair<JCheckBox,JLabel> value : configComponents) {
            horizontalPrlGroupA = horizontalPrlGroupA.addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(value.getLeft(), javax.swing.GroupLayout.PREFERRED_SIZE,150,javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)               
                    .addComponent(value.getRight(), javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE));
        }
       
        jPanel1Layout.setHorizontalGroup(horizontalPrlGroupA);
        


        GroupLayout.SequentialGroup verticalSqGroup = jPanel1Layout.createSequentialGroup();

        for (Pair<JCheckBox,JLabel> value : configComponents) {
            verticalSqGroup = verticalSqGroup.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(value.getLeft())
                    .addComponent(value.getRight(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
        }
        jPanel1Layout.setVerticalGroup(verticalSqGroup);
        jPanel1.revalidate();

        jScrollPane1.setViewportView(jPanel1);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 403, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addGap(18, 18, 18)
                        .addComponent(okButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 750, Short.MAX_VALUE)
                        .addGap(0, 18, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel1)
                .addGap(140, 140, 140)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton)
                    .addComponent(addButton))
                .addGap(25, 25, 25))
        );

//ini
        pack();
        setLocationRelativeTo(null);
        setTitle("List Feature");
    }// </editor-fold>//GEN-END:initComponents

        // TODO add your handling code here:
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        currentFeatures.clear();
        for (int i =0 ; i<configComponents.size();i++) {
            JCheckBox key = configComponents.get(i).getLeft();
            if(key.isSelected()){
                currentFeatures.add(allFeatures.get(i));
            }
        }

        this.dispose();

    }//GEN-LAST:event_okButtonActionPerformed

        private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
       loadPlugin();
        this.dispose();

    }//GEN-LAST:event_okButtonActionPerformed
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e){ }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new ConfigFeatureDialog(new HashMap<>()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton cancelButton;
    private JButton okButton;
    private JButton addButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel1;

    // End of variables declaration//GEN-END:variables
}
