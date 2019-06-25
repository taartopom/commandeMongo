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
import javax.swing.table.DefaultTableModel;
import model.Categorie;
import model.CategorieDAO;
import model.Produit;
import model.ProduitDAO;
import view.ProduitVue;

/*-----------------------------------------------------------------*/
    //1 - Attribu de class
/*-----------------------------------------------------------------*/
/**
 *
 * @author Formation
 */
public class ControllerProduit implements ActionListener, MouseListener{
    private ProduitDAO prodDAO;
    private ProduitVue prodVUE;
    private CategorieDAO catDAO;
    private DefaultTableModel modelProd;

/*-----------------------------------------------------------------*/
    //2 - Constructeurs
/*-----------------------------------------------------------------*/
    public ControllerProduit() {
        prodDAO = new ProduitDAO();
        prodVUE = new ProduitVue();
        catDAO = new CategorieDAO();     
       
        
        ajoutCategorie();
        initModelProd();
        addListerner();
        nextId();
        prodVUE.setVisible(true);
    }
    
    
/*-----------------------------------------------------------------*/
    //2 - Méthodes
/*-----------------------------------------------------------------*/
    // ajouter les ecouteurs sur les boutons
    public void addListerner(){
        this.prodVUE.getBtnAjouterProd().addActionListener(this);
        this.prodVUE.getBtnSupprimerProd().addActionListener(this);
        this.prodVUE.getBtnModifierProd().addActionListener(this);
        this.prodVUE.getBtnResetProd().addActionListener(this);
        this.prodVUE.getTableListeProd().addMouseListener(this);
    }
    
    /**
     * Cette Méthode permet de charger le combobox avec la liste des 
     * catégories de produit
     */
    public void ajoutCategorie(){
        List<Categorie> listeCat = this.catDAO.getAllCategorie();      
        for(Categorie cat : listeCat){
            this.prodVUE.getComboCat().addItem(cat.getIdCat() +" "+ cat.getLibelle());
        }
    }
    
    /**
     * Cette méthode récupère l'idCat dans la chaine de caractère formée de 
     * idcat et du libelle
     * @param chaine
     * @return 
     */
    public int findIdCat(String chaine){
        String [] tabIdCat = chaine.split(" ");  
        return Integer.parseInt(tabIdCat[0]);
    }
    /**
     * 
     */
    public void initModelProd(){
        modelProd = new DefaultTableModel();
        
        //création du modele catégorie
        //Ajout des Colonnes du modele Catégorie
        modelProd.addColumn("ID Prod");
        modelProd.addColumn("Nom");
        modelProd.addColumn("Description");
        modelProd.addColumn("Prix");
        modelProd.addColumn("Qte");
        modelProd.addColumn("Cat");
        //inserer les lignes dans le modele cat
        List<Produit> allProd = new ArrayList<>();
        allProd = this.prodDAO.getAllProduit();
        
        for (Produit prod : allProd) {
            modelProd.addRow(new Object[]{
            prod.getIdProd(),
            prod.getNomProd(),
            prod.getDescriptionProd(),
            prod.getPrixProd(),
            prod.getQteProd(),
            prod.getCatProd().getIdCat()});
        }
        this.prodVUE.getTableListeProd().setModel(modelProd);     
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // pour ajouter
        if(e.getSource().equals(this.prodVUE.getBtnAjouterProd())){
            Produit prod = new Produit();
            prod.setIdProd(Integer.parseInt(this.prodVUE.getTxtIdProd().getText()));
            prod.setNomProd(this.prodVUE.getTxtNomProd().getText());
            prod.setDescriptionProd(this.prodVUE.getTxtDescriptionProd().getText());
            prod.setPrixProd(Double.parseDouble(this.prodVUE.getTxtPrixProd().getText()));           
            prod.setQteProd(Integer.parseInt(this.prodVUE.getTxtPrixProd().getText())); 
            
            String chaine = this.prodVUE.getComboCat().getSelectedItem().toString();
            // creation d'une catgérorie en fonction de l'id du combobox
            Categorie cat = this.catDAO.getOneCategorie(findIdCat(chaine));
            //ajout de la catégorie
            prod.setCatProd(cat);
            
            
            this.prodDAO.addProduit(prod);
            initModelProd();
        }
        // pour supprimer
        if(e.getSource().equals(this.prodVUE.getBtnSupprimerProd())){
            Produit prod = new Produit();
            prod.setIdProd(Integer.parseInt(this.prodVUE.getTxtIdProd().getText()));

            this.prodDAO.addProduit(prod);
            initModelProd();
        }
        // pour l'update
        if(e.getSource().equals(this.prodVUE.getBtnModifierProd())) {
            Produit prod = new Produit();
            prod.setIdProd(Integer.parseInt(this.prodVUE.getTxtIdProd().getText()));
            prod.setNomProd(this.prodVUE.getTxtNomProd().getText());
            prod.setDescriptionProd(this.prodVUE.getTxtDescriptionProd().getText());
            prod.setPrixProd(Double.parseDouble(this.prodVUE.getTxtPrixProd().getText()));           
            prod.setQteProd(Integer.parseInt(this.prodVUE.getTxtPrixProd().getText()));
            prodDAO.updateProduit(prod);

            //vider le champs
            //this.prodVUE.getTxtLibelle().setText("");
            nextId();
            initModelProd();
        }
    }
        
    /*cette méthodes retourne l'id max de la collection Produit*/
        public int maxId(){
        List<Produit> listeProd = this.prodDAO.getAllProduit();
        List<Integer> listeId = new ArrayList<>();
        
        for(Produit prod : listeProd){
            listeId.add(prod.getIdProd());
        }
        if (listeId.isEmpty()) {
            listeId.add(0);
            
        }
        return Collections.max(listeId);
    }
        //cette methodes met a jour le champs idcat avec l'id max+1 de la base
    public void nextId(){
        this.prodVUE.getTxtIdProd().setText(Integer.toString(maxId()+1));
    }
/*-----------------------------------------------------------------*/
    //3 - Evenement
/*-----------------------------------------------------------------*/
    @Override
    public void mouseClicked(MouseEvent me) {
        int ligne  = this.prodVUE.getTableListeProd().getSelectedRow();
        this.prodVUE.getTxtIdProd().setText(modelProd.getValueAt(ligne, 0).toString());
        this.prodVUE.getTxtNomProd().setText(modelProd.getValueAt(ligne,1).toString());
        this.prodVUE.getTxtDescriptionProd().setText(modelProd.getValueAt(ligne,2).toString());
        this.prodVUE.getTxtPrixProd().setText(modelProd.getValueAt(ligne,3).toString());
        this.prodVUE.getTxtQteProd().setText(modelProd.getValueAt(ligne,4).toString());
        
        prodVUE.getBtnAjouterProd().setEnabled(false);
        prodVUE.getBtnModifierProd().setEnabled(true);
        prodVUE.getBtnSupprimerProd().setEnabled(true);
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
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
