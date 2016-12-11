package com.jro.restaurant.model;

import java.io.Serializable;

/**
 * Created by jro on 26/11/2016.
 */

public class Dish  implements Serializable {

    private String mName;
    private String[] mIngredientes;
    private int nPhoto;
    private Allergen[] mAllergen;
    private Double mPrice;
    private String mObservations;


    // Constructor
    public Dish(String name, String[] ingredients ,int nPhoto, Allergen[] allergen, Double price, String observations) {
        mName = name;
        mIngredientes = ingredients;
        this.nPhoto = nPhoto;
        mAllergen = allergen;
        mPrice = price;
        mObservations = observations;
    }

    // Convinience Constructor
    public Dish(String name, int nPhoto, Double price) {
        mName = name;
        mIngredientes = new String[0];
        this.nPhoto = nPhoto;
        mAllergen = new Allergen[0];
        mPrice = price;
        mObservations = "";
    }

    // Getter & Setter
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String[] getIngredientes() { return mIngredientes; }

    public void setIngredientes(String[] ingredientes) {
        mIngredientes = ingredientes;
    }

    public int getnPhoto() {
        return nPhoto;
    }

    public void setnPhoto(int nPhoto) {
        this.nPhoto = nPhoto;
    }

    public Allergen[] getAllergen() { return mAllergen; }

    public void setAllergen(Allergen[] allergen) {
        mAllergen = allergen;
    }

    public Double getPrice() {
        return mPrice;
    }

    public String getPriceDescription() {
        return mPrice.toString() + "€";
    }

    public void setPrice(Double price) {
        mPrice = price;
    }

    public String getObservations() {
        return mObservations;
    }

    public void setObservations(String observations) {
        mObservations = observations;
    }

    public String getIngredientesDescription() {

        String salida = "";
        for (String elem:mIngredientes) {
            if (salida.equals("")) {
                salida = salida + elem;
            }else{
                salida = salida + ", "+ elem;
            }
        }
        return salida;

    }

    public String getAllergenDescription() {
        String salida = "";
        for (Allergen elem:mAllergen) {
            if (salida.equals("")) {
                salida = salida + elem.getName();
            }else{
                salida = salida + ", "+ elem.getName();
            }

        }

        return salida;
    }
}
