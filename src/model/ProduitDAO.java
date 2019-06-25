/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrateur
 */
public class ProduitDAO implements ProduitInterface{
        
    private DB db;
    private DBCollection collectionProd;

    public ProduitDAO() {
        this.db = Connexion.getConnexion();
        this.collectionProd = this.db.getCollection("produit");
    }

    @Override
    public List<Produit> getAllProduit() {
       
        List<Produit> listeprod = new ArrayList<>();
         DBCursor cursor = this.collectionProd.find();
         
         while(cursor.hasNext()){
             DBObject obj = cursor.next();
             
             Produit prod =  new Produit();
             
             prod.setIdProd((int)obj.get("_id"));
             prod.setDescriptionProd(obj.get("description").toString());
             prod.setNomProd(obj.get("nom").toString());
             prod.setPrixProd((double)obj.get("prix"));
             prod.setQteProd((int)obj.get("qte"));
             
             
             //création du document pour récuperer la catégorie dans le document produit
             DBObject objCat = (DBObject)obj.get("idCat");
             
             //maj de la cat dans l'objet produit
             prod.setCatProd(new Categorie((int) objCat.get("_id"),objCat.get("libelle").toString()));
             
             //ajout du produit à la liste
             listeprod.add(prod);
         }
         return listeprod;
    }

    @Override
    public void addProduit(Produit prod) {
               //création d'un document 
        BasicDBObject docProd = new BasicDBObject();
        docProd.append("_id",prod.getIdProd());
        docProd.append("nom", prod.getNomProd());
        docProd.append("description",prod.getDescriptionProd());
        docProd.append("prix",prod.getPrixProd());
        docProd.append("qte",prod.getQteProd());
        docProd.append("idCat",new BasicDBObject( 
                        "_id",prod.getCatProd().getIdCat())
                        .append("libelle",prod.getCatProd().getLibelle()));
        
        //ajout du document dans la collection catégorie
        this.collectionProd.insert(docProd);

        JOptionPane.showMessageDialog(null,"Opération effectuée avec succes");

    }

    @Override
    public void deleteProduit(Produit prod) {
        BasicDBObject docProd = new BasicDBObject();
        docProd.append("_id",prod.getIdProd());

        this.collectionProd.remove(docProd);     
        
        JOptionPane.showMessageDialog(null,"Opération effectuée avec succes");
    }

    @Override
    public void updateProduit(Produit prod) {

        //création du document avec les valeur à mettre à jour
        BasicDBObject docProdNew = new BasicDBObject();
        docProdNew.append("_id",prod.getIdProd());
        docProdNew.append("nom", prod.getNomProd());
        docProdNew.append("decription", prod.getDescriptionProd());
        docProdNew.append("prix", prod.getPrixProd());
        docProdNew.append("qte", prod.getQteProd());
        docProdNew.append("idCat",new BasicDBObject( 
                        "_id",prod.getCatProd().getIdCat())
                        .append("libelle",prod.getCatProd().getLibelle()));
        
        //création du document à l'id de la cat qui permet de rechercher dans la collection
        BasicDBObject docProdOld = new BasicDBObject();
        docProdOld.append("_id",prod.getIdProd());
        this.collectionProd.update(docProdOld, docProdNew);
     
        JOptionPane.showMessageDialog(null,"Opération effectuée avec succes");
      
    
}

    @Override
    public Produit getOneProduit(int idProd) {
        BasicDBObject id = new BasicDBObject("_id",idProd);
        
        DBObject obj = this.collectionProd.findOne(id);

             
        Produit prod =  new Produit();
             
        prod.setIdProd((int)obj.get("_id"));
        prod.setDescriptionProd(obj.get("description").toString());
        prod.setNomProd(obj.get("nom").toString());
        prod.setPrixProd((double)obj.get("prix"));
        prod.setQteProd((int)obj.get("qte"));
             
             
        //création du document pour récuperer la catégorie dans le document produit
        DBObject objCat = (DBObject)obj.get("idCat");
             
        //maj de la cat dans l'objet produit
        prod.setCatProd(new Categorie((int) objCat.get("_id"),objCat.get("libelle").toString()));
             
        //ajout du produit à la liste
         return prod;
    }
    
}
