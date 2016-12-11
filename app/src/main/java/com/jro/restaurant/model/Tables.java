package com.jro.restaurant.model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by jro on 04/12/2016.
 */

public class Tables implements Serializable{
    private LinkedList<Table> mTables;

    public Tables(){
        mTables = new LinkedList<>();
        for (int i = 0; i<9; i++) {
            Table table = new Table(i+1, 3);
            mTables.add(table);
        }
    }

    public LinkedList<Table> getTables() {
        return mTables;
    }

    public Table getTable(int position) {
        return mTables.get(position);
    }

    public int getCount() {
        return mTables.size();
    }
}
