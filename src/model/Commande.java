/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mongodb.BasicDBObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrateur
 */
public class Commande {
    private int idCmd;
    private String nomClient;
    private String dateCmd;
    private List<BasicDBObject> listeProd;
/*------------------------------------------------------------------------*/
    //Constructeurs
/*------------------------------------------------------------------------*/


    public Commande(int idCmd, String nomClient, String dateCmd) {
        this.idCmd = idCmd;
        this.nomClient = nomClient;
        this.dateCmd = dateCmd;
        this.listeProd = new ArrayList<>();
    }

    public Commande(int idCmd, String nomClient) {
        this.idCmd = idCmd;
        this.nomClient = nomClient;
    }

    public Commande() {
    }
/*------------------------------------------------------------------------*/
    //getter
/*------------------------------------------------------------------------*/

    public int getIdCmd() {
        return idCmd;
    }

    public String getNomClient() {
        return nomClient;
    }

    public String getDateCmd() {
        return dateCmd;
    }

    public List<BasicDBObject> getListeProd() {
        return listeProd;
    }
/*------------------------------------------------------------------------*/
    //setter
/*------------------------------------------------------------------------*/

    public void setIdCmd(int idCmd) {
        this.idCmd = idCmd;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public void setDateCmd(String dateCmd) {
        this.dateCmd = dateCmd;
    }

    public void setListeProd(List<BasicDBObject> listeProd) {
        this.listeProd = listeProd;
    }
/*------------------------------------------------------------------------*/
    //tostring
/*------------------------------------------------------------------------*/

    @Override
    public String toString() {
        return "Commande{" + "idCmd=" + idCmd + ", nomClient=" + nomClient + ", dateCmd=" + dateCmd + ", listeProd=" + listeProd + '}';
    }

}
