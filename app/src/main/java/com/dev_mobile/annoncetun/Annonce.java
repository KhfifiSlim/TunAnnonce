package com.dev_mobile.annoncetun;

public class Annonce {
    private String id;
    private String marque;
    private String localisation;
    private Double prix;
    private String DatePublication;
    private String pimage;
    private String user;
    private String desc;
    private String numtel;
    private String categorie;
    private String favoris;

    public Annonce() {
    }



    public Annonce(String id, String marque, String localisation, Double prix, String DatePublication, String pimage , String user ,String desc,String numtel,String categorie,String favoris ) {
        this.id = id;
        this.marque= marque;
        this.localisation= localisation;
        this.prix= prix;
        this.DatePublication= DatePublication;
        this.pimage= pimage;
        this.user= user;

        this.desc=desc;
        this.numtel=numtel;
        this.categorie=categorie;
        this.favoris=favoris;
    }


    public String getId() {
        return id;
    }

    public String getMarque() {
        return marque;
    }

    public String getLocalisation() {
        return localisation;
    }

    public Double getPrix() {
        return prix;
    }

    public String getMiseEnCirculation() {
        return DatePublication;
    }

    public String getPimage() {
        return pimage;
    }

    public String getUser() {
        return user;
    }


    public String getDesc() {
        return desc;
    }

    public String getNumtel() {
        return numtel;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getFavoris() {
        return favoris;
    }



    public void setId(String id) {
        this.id = id;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public void setMiseEnCirculation(String DatePublication) {
        DatePublication = DatePublication;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setNumtel(String numtel) {
        this.numtel = numtel;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setFavoris(String favoris) {
        this.favoris = favoris;
    }

}
