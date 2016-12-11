package com.jro.restaurant.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by jro on 08/12/2016.
 */

public class Dishes implements Serializable{
    private LinkedList<Dish> mDishes;

    public Dishes(Dish[] dishes){
        if (dishes != null) {
            mDishes = new LinkedList(Arrays.asList(dishes));
        }
    }

    public Dishes(){
        if (mDishes == null) {
            mDishes = new LinkedList();
        }
    }

    public LinkedList<Dish> getDishes() {
        return mDishes;
    }

    public Dish getDish(int position) {
        return mDishes.get(position);
    }

    public void addDish(Dish dish) {
        mDishes.add(dish);
    }

    public void removeDish(Dish dish) {
        mDishes.remove(dish);
    }

    public int getCount() {
        return mDishes.size();
    }

}
