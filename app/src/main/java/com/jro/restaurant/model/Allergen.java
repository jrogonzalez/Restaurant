package com.jro.restaurant.model;

import java.io.Serializable;

/**
 * Created by jro on 26/11/2016.
 */

public class Allergen  implements Serializable {

    private String mName;

    // Constructor
    public Allergen(String name) {
        mName = name;
    }

    // Getter & Setter
    public String getName() {
        return mName;
    }

}
