/*--------------------------------------------------*/
/* Structure de la page 
*1- Attribu de class
*2- Constructeur
*3- Methodes
    -Controller entre la vue et le model
    -Evenement
/*--------------------------------------------------*/



package controller;

import com.mongodb.BasicDBObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;
import model.Categorie;
import model.Commande;
import model.CommandeDAO;
import model.Produit;
import view.CommandeVue;
import view.LigneCommande;

/*-----------------------------------------------------------------*/
    //1 - Attribu de class
/*-----------------------------------------------------------------*/
/**
 *
 * @author Administrateur
 */
public class ControllerCommande implements ActionListener,MouseInputListener{
    
    private CommandeVue cmdVUE;
    private CommandeDAO cmdDAO;
    private DefaultTableModel modelCmd, modelLigneCmd;
    private LigneCommande ligneCmdVue;
    


/*-----------------------------------------------------------------*/
    //2 - Constructeurs
/*-----------------------------------------------------------------*/
    public ControllerCommande() {
        this.cmdVUE = new CommandeVue();
        this.cmdDAO = new CommandeDAO();
        this.ligneCmdVue = new LigneCommande();
        
        this.cmdVUE.getTxtDateCmd().setText(getdateJour());
        initModelProd();
        
        initBtnDefault();
        
        nextId();
        addListener();
        this.cmdVUE.setVisible(true);
        this.ligneCmdVue.setVisible(true);
    }

    public ControllerCommande(CommandeVue cmdVUE, CommandeDAO cmdDAO) {
        this.cmdVUE = cmdVUE;
        this.cmdDAO = cmdDAO;
    }
    public ControllerCommande(LigneCommande ligneCmdVue) {
        this.ligneCmdVue = ligneCmdVue;
    }

    
    
 
    
    
/*-----------------------------------------------------------------*/
    //3 - Méthodes
/*-----------------------------------------------------------------*/
    public void initBtnDefault(){
        cmdVUE.getBtnAddCmd().setEnabled(true);
        cmdVUE.getBtnAddProdCmd().setEnabled(false);
        cmdVUE.getBtnDeleteCmd().setEnabled(false);
    }
    public void initBtnUpdate(){
        cmdVUE.getBtnAddCmd().setEnabled(false);
        cmdVUE.getBtnAddProdCmd().setEnabled(true);
        cmdVUE.getBtnDeleteCmd().setEnabled(true);
    }

    public String getdateJour(){
        Date now = new Date();
          SimpleDateFormat formater = null;
          formater = new SimpleDateFormat("dd/mm/Y");
          
          return formater.format(now);
    }
    public void initModelProd(){
        modelCmd = new DefaultTableModel();
        
        //création du modele catégorie
        //Ajout des Colonnes du modele Catégorie
        modelCmd.addColumn("ID Commande");
        modelCmd.addColumn("Nom client");
        modelCmd.addColumn("Date Commande");

        //inserer les lignes dans le modele cat
        List<Commande> allCmd = new ArrayList<>();
        allCmd = this.cmdDAO.getAllCommande();
        
        for (Commande cmd : allCmd) {
            modelCmd.addRow(new Object[]{cmd.getIdCmd(),
            cmd.getNomClient(),
            cmd.getDateCmd(),

            });
        this.cmdVUE.getTabCmd().setModel(modelCmd);     
        }
    }

    
    
    public void nextId(){
        cmdVUE.getTktIdCmd().setText(Integer.toString(maxId()+1));
        
    }
    
    public int maxId(){
        List<Commande> listeCmd = this.cmdDAO.getAllCommande();
        List<Integer> listeId = new ArrayList<>();
        
        for(Commande cmd : listeCmd){
            listeId.add(cmd.getIdCmd());
        }
        if (listeId.isEmpty()) {
            listeId.add(0);
            
        }
        return Collections.max(listeId);
    }

/*-----------------------------------------------------------------*/
    //3 - Evenement
/*-----------------------------------------------------------------*/
    
    
    
    public void  addListener(){ 
        cmdVUE.getBtnResetCmd().addActionListener(this);
        cmdVUE.getBtnAddCmd().addActionListener(this);
        cmdVUE.getBtnAddProdCmd().addActionListener(this);
        cmdVUE.getBtnDeleteCmd().addActionListener(this);
        cmdVUE.getTabCmd().addMouseListener(this);


    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        
       //pour la suppression d'une commande
        if (ae.getSource().equals(this.cmdVUE.getBtnDeleteCmd())) {
           this.cmdDAO.supprimerCommande(new Commande(Integer.parseInt(this.cmdVUE.getTktIdCmd().getText()),null));
            
           JOptionPane.showMessageDialog(null,"Suppression ok");
           initModelProd();
        }

        //Pour l'ajout d'une commande
        if (ae.getSource().equals(this.cmdVUE.getBtnAddCmd())) {
            Commande cmd = new Commande();
            cmd.setIdCmd(Integer.parseInt(this.cmdVUE.getTktIdCmd().getText()));
            cmd.setNomClient(this.cmdVUE.getTktNomClient().getText());
            cmd.setDateCmd(this.cmdVUE.getTxtDateCmd().getText());
            
            this.cmdDAO.ajouterCommande(cmd);
            JOptionPane.showMessageDialog(null,"Création de commande ok");
            initModelProd();
            nextId();
        }
        //Pour l'ajout d
        
        // pour reset la selection
        if (ae.getSource().equals(this.cmdVUE.getBtnResetCmd())) {
            cmdVUE.getBtnAddCmd().setEnabled(true);
            cmdVUE.getBtnAddProdCmd().setEnabled(false);
            cmdVUE.getBtnDeleteCmd().setEnabled(false);
            
            cmdVUE.getTktNomClient().setText("");
            cmdVUE.getTxtDateCmd().setText(getdateJour());
            
            nextId();
        }  
        
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        int ligne  = this.cmdVUE.getTabCmd().getSelectedRow();
        this.cmdVUE.getTktIdCmd().setText(modelCmd.getValueAt(ligne, 0).toString());
        this.cmdVUE.getTktNomClient().setText(modelCmd.getValueAt(ligne,1).toString());
        this.cmdVUE.getTxtDateCmd().setText(modelCmd.getValueAt(ligne,2).toString());
        
        initBtnUpdate();
        
        this.ligneCmdVue.getTktLigneCmd().setText(modelCmd.getValueAt(ligne, 0).toString());
        this.ligneCmdVue.getTktNomClientLigneCmd().setText(modelCmd.getValueAt(ligne, 1).toString());
        this.ligneCmdVue.getTktDateLigneCmd().setText(modelCmd.getValueAt(ligne, 2).toString());
        



// Modele ligne commande
       modelLigneCmd = new DefaultTableModel();
        
        //création du modele catégorie
        //Ajout des Colonnes du modele Catégorie
        modelLigneCmd.addColumn("ID prod");
        modelLigneCmd.addColumn("Nom prod");
        modelLigneCmd.addColumn("qte commande");
        modelLigneCmd.addColumn("categorie");

        //inserer les lignes dans le modele cat
        int idCmd = Integer.parseInt(modelCmd.getValueAt(ligne,0).toString());
        Commande cmd = this.cmdDAO.getOneCommande(idCmd);
        
        for (BasicDBObject obj : cmd.getListeProd()) {
            modelLigneCmd.addRow(new Object[]{obj.get("_id"),
               obj.get("nomProd"),obj.get("qteCmd")});
        this.ligneCmdVue.getTabPanier().setModel(modelLigneCmd);     
        }
    
    
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    

}
