package com.jro.restaurant.model;

import java.io.Serializable;

/**
 * Created by jro on 26/11/2016.
 */

public class Table implements Serializable{


    private Integer mNumber;
    private Integer mNumPerson;
    private Integer mNumDish;
    private Dishes mDishes;
    private Double mBill;
    private String mObservations;


    // Constructor
    public Table(Integer number, Integer numPerson) {
        mDishes = null;
        mNumber = number;
        mNumPerson = numPerson;
        mNumDish = 0;
        mBill = 0.0D;
        mObservations = "";
    }


    // Getter & Setter


    public Dishes getDishes() { return mDishes; }

    public void setDishes(Dishes dishes) { mDishes = dishes; }

    public Integer getNumber() {
        return mNumber;
    }

    public void setNumber(Integer number) {
        mNumber = number;
    }

    public Integer getNumPerson() {
        return mNumPerson;
    }

    public void setNumPerson(Integer numPerson) {
        mNumPerson = numPerson;
    }

    public Integer getNumDish() {
        return mNumDish;
    }

    public void setNumDish(Integer numDish) {
        mNumDish = numDish;
    }

    public Double getBill() {
        return mBill;
    }

    public void setBill(Double bill) {
        mBill = bill;
    }

    public String getObservations() {
        return mObservations;
    }

    public void setObservations(String observations) {
        mObservations = observations;
    }

    // Methods
    public void addDish(Dish dish){
        if (mDishes == null){
            mDishes = new Dishes();
        }
        mDishes.addDish(dish);

        this.mNumDish++;
        this.mBill = this.mBill + dish.getPrice();
    }

    public void removeDish(Dish dish){
        this.mNumDish--;
        this.mBill = this.mBill - dish.getPrice();
    }

    public String getDishesName(){
        String salida ="";
        if (mDishes != null && mDishes.getDishes() != null && mDishes.getDishes().size() >0 ){
            Dish[] arrayDish = mDishes.getDishes().toArray(new Dish[0]);
            for (int i = 0; i < arrayDish.length ; i++) {
                if (salida.equals("")) {
                    salida = salida + arrayDish[i].getName();
                }else{
                    salida = salida + ", " + arrayDish[i].getName();
                }
            }
        }
    return salida;
    }

    @Override
    public String toString() {
        return "MESA NUMERO "+ getNumber();
    }
}
