/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrateur
 */
public class CommandeDAO  implements CommandeInterface{
    private DB db;
    private DBCollection collectionCmd;

    public CommandeDAO() {
        this.db = Connexion.getConnexion();
        this.collectionCmd = this.db.getCollection("commande");
    }
    

/*------------------------------------------------------------------------*/
    //les methodes
/*------------------------------------------------------------------------*/
    /*private int idCmd;
    private String nomClient;
    private String dateCmd;
    private List<BasicDBObject> listeProd;*/
    @Override
    public List<Commande> getAllCommande() {
        return null;
    }

    @Override
    public Commande getOneCommande(int idCmd) {
     return null;   
    }
    
    @Override
    public void ajouterCommande(Commande cmd) {
    //création d'un document 

        BasicDBObject docCmd = new BasicDBObject("_id", cmd.getIdCmd());
        docCmd.append("nomClient", cmd.getNomClient());
        docCmd.append("date",cmd.getDateCmd());
        docCmd.append("contenir",cmd.getListeProd());
        
        //ajout du document dans la collection catégorie
        this.collectionCmd.insert(docCmd);

        JOptionPane.showMessageDialog(null,"Opération effectuée avec succes");

    
    }

    @Override
    public void supprimerCommande(Commande cmd) {
    }
    
    @Override
    public void actualiserCommande(Commande cmd, Produit prod) {
       // List<BasicDBObject> listProdCmd new ArrayList<>;
       
        //creation de l'ancienne commande en reccuperant l'id
        BasicDBObject docCmdOld = new BasicDBObject("_id", cmd.getIdCmd());
        
        //recherche de la commande
        DBObject obj = this.collectionCmd.findOne(docCmdOld);
        // recuperation de la liste des produits de la commande existante
        BasicDBList listprod = (BasicDBList) obj.get("contenir");
        

        //ajout du nouveau produit à la listprod
        BasicDBObject newProd = new BasicDBObject("_id",prod.getIdProd())
            .append("nomProd", prod.getNomProd())
            .append("qteCmd",prod.getQteProd())
            .append("categorie", new BasicDBObject("_id", prod.getCatProd().getIdCat())
                    .append("libelle",prod.getCatProd().getLibelle()) );
        listprod.add(newProd);
        
        //création de la nouvelle commande
        BasicDBObject docCmdNew = new BasicDBObject("_id", cmd.getIdCmd())
            .append("nomClient", cmd.getNomClient())
            .append("date",cmd.getDateCmd())
            .append("contenir",listprod);
        
        //actualisation de la commande
        this.collectionCmd.update(docCmdOld,docCmdNew);

        JOptionPane.showMessageDialog(null,"Opération effectuée avec succes");    }




    @Override
    public void supprimerProdCommande(Commande cmd, Produit prod) {
       
        //creation de l'ancienne commande en reccuperant l'id
        BasicDBObject docCmdOld = new BasicDBObject("_id", cmd.getIdCmd());
        
        //recherche de la commande
        DBObject obj = this.collectionCmd.findOne(docCmdOld);
        // recuperation de la liste des produits de la commande existante
        BasicDBList listprod = (BasicDBList) obj.get("contenir");
            for (int i= 0; i<listprod.size();i++){
                DBObject objProd = (DBObject)listprod.get(i);
                if (objProd.get("_id").equals(prod.getIdProd())){
                    listprod.remove(i);
                    
                }
            }
        //création de la nouvelle commande
        BasicDBObject docCmdNew = new BasicDBObject("_id", cmd.getIdCmd())
            .append("nomClient", cmd.getNomClient())
            .append("date",cmd.getDateCmd())
            .append("contenir",listprod);
        
        //actualisation de la commande
        this.collectionCmd.update(docCmdOld,docCmdNew);

        JOptionPane.showMessageDialog(null,"Opération effectuée avec succes");    
    }    
}

