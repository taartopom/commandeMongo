/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author Administrateur
 */

/*------------------------------------------------------------------------*/
    //signature des méthodes
/*------------------------------------------------------------------------*/
public interface CommandeInterface {
    public void ajouterCommande(Commande cmd);
    public void supprimerCommande(Commande cmd);
    public void actualiserCommande (Commande cmd, Produit prod);
    public List<Commande> getAllCommande();
    public Commande getOneCommande(int idCmd);
    

}
