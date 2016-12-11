package com.jro.restaurant.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.jro.restaurant.R;
import com.jro.restaurant.fragment.TableFragment;
import com.jro.restaurant.model.Table;
import com.jro.restaurant.model.Tables;

public class MainActivity extends AppCompatActivity implements TableFragment.OnTableSelectedListener{

    public static final String TAG = MainActivity.class.getCanonicalName();
    private final static int TABLE_DETAIL_RESULT = 0;
    public final static String EXTRA_TABLES = "EXTRA_TABLES";

    private Tables mTables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //--SAVE Data
        SharedPreferences preferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("downloadedDishes", false);
        editor.apply();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Accedemos a la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setLogo(R.mipmap.ic_launcher);

        // Le decimos a nuestra pantalla que esa es nuestra action bar
        setSupportActionBar(toolbar);

        // Cargamos a mano el fragment
        FragmentManager fm = getFragmentManager();

        //Creamos el modelo
        mTables = new Tables();

        // Preguntamos a ver si existe el hueco para table_list
        if (findViewById(R.id.fragment_table_list) != null) {

            if (fm.findFragmentById(R.id.fragment_table_list) == null) {
                fm.beginTransaction()
                        .add(R.id.fragment_table_list, TableFragment.newInstance(mTables))
                        .commit();
            }
        }

//        // Preguntamos a ver si existe el hueco para table_list
//        if (findViewById(R.id.activity_table_detail) != null) {
//
//            if (fm.findFragmentById(R.id.activity_table_detail) == null) {
//                fm.beginTransaction()
//                        .add(R.id.activity_table_detail, new TableDetailActivity())
//                        .commit();
//            }
//        }
    }

    @Override
    public void onTableSelected(int position) {
        Log.v(TAG, "***************** MainActivity.OnTableSelected *****************");

        // Vamos a mostrar la vista de detalle
        Intent intent = new Intent(this, TableDetailActivity.class);
        intent.putExtra(TableDetailActivity.EXTRA_POSITION, position);
        startActivityForResult(intent, TABLE_DETAIL_RESULT);
    }

}
