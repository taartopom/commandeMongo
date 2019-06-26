/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gescommongo;


import com.mongodb.BasicDBObject;
import controller.ControllerCommande;
import controller.ControllerProduit;
import java.util.List;
import model.Categorie;
import model.CategorieDAO;
import model.Commande;
import model.CommandeDAO;
import model.Connexion;
import model.Produit;
import model.ProduitDAO;
import view.CategorieVue;

/**
 *
 * @author Formation
 */
public class GescomMongo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
   //     Connexion.getConnexion();
       // Categorie cat = new Categorie(2,"CD"); //création de l'objet catégorie
        //CategorieDAO catDao = new CategorieDAO();//création de l'objet DaoCat 
        
        //insertion du l'objet cat à la collection categorie;
  //      catDao.addCategorie(cat);
        ///catDao.deleteCategorie(cat);
        //System.out.println(catDao.getOneCategorie(2));
        
        //CategorieVue catVue = new CategorieVue();
        
        //Produit produit = new Produit(0, "chocolat", "au lait", 1, 1000 ,(new Categorie(2, "gros")));
        
        //ProduitDAO prodDAO = new ProduitDAO();
        //prodDAO.updateProduit(produit);
        
        //prodDAO.deleteProduit(produit);
        
        //ProduitDAO prodDAO = new ProduitDAO();
       // List<Produit> list = prodDAO.getAllProduit();
       // list = prodDAO.getAllProduit();
        
       // System.out.println(prodDAO.getAllProduit());
       /* for (Produit prod : list) {
            System.out.println("_id : " + prod.getIdProd()+ "nomProd" + prod.getNomProd());
      }*/
       
       // System.out.println(prodDAO.getOneProduit(1));
       
       //
       //ControllerProduit cProd = new ControllerProduit();
       
       //CommandeDAO cmdDAO = new CommandeDAO();
      //Commande cmd = new Commande(1, "sandra","20/08/1990");
       //Commande cmd2 = new Commande(3, "xavier","25/05/1989");
       
       //Produit produit = new Produit(0, "chocolat", "au lait", 1, 1000 ,(new Categorie(2, "gros")));
       
       //Commande cmd2 = new Commande(4, "quentin","14/05/1993");

        //
        
        //Commande cmd = new Commande(6, "Celine","20/08/1980");
                /*cmd.getListeProd()
               .add(new BasicDBObject("_id",1)  
               .append("nomProd","chocolat")
               .append("qteCmd",10)
               .append("categorie", new BasicDBObject("_id", 1)
                    .append("libelle", "Livre"))*/
                
       //Produit produit = new Produit(5, "fraise", "au lait", 1, 1000 ,(new Categorie(2, "gros")));
        //Produit produit = new Produit(4, "caramel", "au beurre salé", 1, 1000 ,(new Categorie(2, "gros")));
        //Produit produit = new Produit(3, "vanille", "des iles", 1, 1000 ,(new Categorie(2, "gros")));
        //Produit produit = new Produit(6, "chantilly", "pour les glace de celine", 1, 600 ,(new Categorie(2, "gros")));

       //cmdDAO.actualiserCommande(cmd,produit);
       //cmdDAO.supprimerProdCommande(cmd, produit);
       
       
       //Commande cmd = new Commande(9, "sylvain","20/08/1980");
       //cmdDAO.ajouterCommande(cmd);
       //cmdDAO.supprimerCommande(cmd);
       
        //CommandeDAO cmdDAO = new CommandeDAO();
        //Commande cmd = new Commande(4, "quentin","14/05/1993");
       // Produit prod = new Produit(4, "chaocolat", "au lait", 1, 69 ,(new Categorie(2, "gros")));        
                
       // cmdDAO.modifierProdCmd(cmd, prod);
       
       
       /* System.out.println(cmdDAO.getAllCommande());
        List<Commande> listeCmd = cmdDAO.getAllCommande();
        
       for (Commande cmd : listeCmd){
            System.out.println(cmd);
            
        }*/
       
        //System.out.println(cmdDAO.getOneCommande(4));+++++111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
        
        

        ControllerCommande cCmd = new ControllerCommande();
}
}
