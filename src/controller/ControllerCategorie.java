
/*--------------------------------------------------*/
/* Structure de la page 
*1- Attribu de class
*2- Constructeur
*3- Methodes
    -Controller entre la vue et le model
    -Evenement
*/
/*--------------------------------------------------*/
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Categorie;
import model.CategorieDAO;
import view.CategorieVue;

/*-----------------------------------------------------------------*/
    //1 - Attribu de class
/*-----------------------------------------------------------------*/
/**
 *
 * @author Administrateur
 */
public class ControllerCategorie implements ActionListener, MouseListener{
    private CategorieDAO catDAO;
    private CategorieVue catVUE;
    
    private DefaultTableModel modelCat;



    
/*-----------------------------------------------------------------*/
    //2 - Constructeurs
/*-----------------------------------------------------------------*/
    public ControllerCategorie(){
        catDAO = new CategorieDAO();
        catVUE = new CategorieVue();

        
        init();    
        
        catVUE.getBtnAjouter().setEnabled(true);
        catVUE.getBtnModifier().setEnabled(false);
        catVUE.getBtnSupprimer().setEnabled(false);
        
        addListener();
        nextId();
        
        catVUE.setVisible(true);

        
    }   

    public ControllerCategorie(CategorieDAO catDAO, CategorieVue catVUE) {
        this.catDAO = catDAO;
        this.catVUE = catVUE;
    }

    public ControllerCategorie(CategorieVue catVUE) {
        this.catVUE = catVUE;
        this.catDAO = new CategorieDAO();
    }
    
/*-----------------------------------------------------------------*/
//1 - Methodes
/*-----------------------------------------------------------------*/
    /*controller entre la vue et le model*/
/*-------------------------------------------------------------------*/
    //cette methodes met a jour le champs idcat avec l'id max+1 de la base
    public void nextId(){
        catVUE.getTxtIdCat().setText(Integer.toString(maxId()+1));
        
    }
    public void init(){
        //création du modèle catégorie
        modelCat = new DefaultTableModel();
        //ajout de colonne
        modelCat.addColumn("Id Categorie");
        modelCat.addColumn("libelle");
        
        //inserer les lignes dans le modele cat
        List<Categorie> allCat = new ArrayList<>();
        allCat = this.catDAO.getAllCategorie();
        for(Categorie cat: allCat){
            modelCat.addRow(new Object[]{cat.getIdCat(),cat.getLibelle()});
        }
        
        catVUE.getjTable1().setModel(modelCat);
    }
    public void  addListener(){ 
        catVUE.getBtnAjouter().addActionListener(this);
        catVUE.getBtnModifier().addActionListener(this);
        catVUE.getBtnSupprimer().addActionListener(this);
        catVUE.getBtnReset().addActionListener(this);
        catVUE.getjTable2().addMouseListener(this);


    }
    
    /*Evenement*/
/*-------------------------------------------------------------------*/


    @Override
    public void actionPerformed(ActionEvent ae) {
        
        //pour l'ajout
        if (ae.getSource().equals(this.catVUE.getBtnAjouter())) {
            Categorie cat = new Categorie();
            cat.setIdCat(Integer.parseInt(this.catVUE.getTxtIdCat().getText()));
            cat.setLibelle(this.catVUE.getTxtLibelle().getText());
            
            catDAO.addCategorie(cat);
            
            JOptionPane.showMessageDialog(null,"Enregistrement ok");
            
            this.catVUE.getTxtLibelle().setText("");
            nextId();
            init();    
        }

        // pour la modification
        if (ae.getSource().equals(this.catVUE.getBtnModifier())) {
            Categorie catUp = new Categorie();
            catUp.setIdCat(Integer.parseInt(this.catVUE.getTxtIdCat().getText()));
            catUp.setLibelle(this.catVUE.getTxtLibelle().getText());
            catDAO.updateCategorie(catUp);

            JOptionPane.showMessageDialog(null, "Modification ok");
            
            //vider le champs
            this.catVUE.getTxtLibelle().setText("");
            nextId();
            init();

        }

        // pour la suppression
        if (ae.getSource().equals(this.catVUE.getBtnSupprimer())) {
            Categorie cat = new Categorie();
            // on mofifie 'id et le libellé
            cat.setIdCat(Integer.parseInt(this.catVUE.getTxtIdCat().getText()));
            cat.setLibelle(this.catVUE.getTxtLibelle().getText());
            
            catDAO.deleteCategorie(cat);
            
            JOptionPane.showMessageDialog(null,"Suppression ok");
            
            //vider le champs libelle
            this.catVUE.getTxtLibelle().setText("");
            nextId();
            init();
            
        }
        // pour reset la selection
        if (ae.getSource().equals(this.catVUE.getBtnReset)) {
            catVUE.getBtnAjouter().setEnabled(true);
            catVUE.getBtnModifier().setEnabled(false);
            catVUE.getBtnSupprimer().setEnabled(false);
            
            catVUE.getTxtIdCat().setText("");
            catVUE.getTxtLibelle().setText("");
            
            nextId();
        }  
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        int ligne  = this.catVUE.getjTable2().getSelectedRow();
        this.catVUE.getTxtIdCat().setText(modelCat.getValueAt(ligne, 0).toString());
        this.catVUE.getTxtLibelle().setText(modelCat.getValueAt(ligne,1).toString());
        
        catVUE.getBtnAjouter().setEnabled(false);
        catVUE.getBtnModifier().setEnabled(true);
        catVUE.getBtnSupprimer().setEnabled(true);
    }

    @Override
    public void mousePressed(MouseEvent me) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     /*cette méthodes retourne l'id max de la collection Catégorie*/
    public int maxId(){
        List<Categorie> listeCat = this.catDAO.getAllCategorie();
        List<Integer> listeId = new ArrayList<>();
        
        for(Categorie cat : listeCat){
            listeId.add(cat.getIdCat());
        }
        if (listeId.isEmpty()) {
            listeId.add(0);
            
        }
        return Collections.max(listeId);
    }
}
