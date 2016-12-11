package com.jro.restaurant.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jro.restaurant.R;
import com.jro.restaurant.model.Table;
import com.jro.restaurant.model.Tables;

/**
 * Created by jro on 02/12/2016.
 */

public class TableFragment extends Fragment {

    public static final String TAG = TableFragment.class.getCanonicalName();
    public static final int LOADING_VIEW_INDEX = 0;
    public static final int TABLE_VIEW_INDEX = 1;
    private final static String ARG_TABLES = "arg_tables";

    private OnTableSelectedListener mOnTableSelectedListener;

    private Tables mTables;

    public static TableFragment newInstance(Tables tables) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_TABLES, tables);

        TableFragment tableFragment = new TableFragment();
        tableFragment.setArguments(arguments);

        return tableFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTables = (Tables) getArguments().getSerializable(ARG_TABLES);
        }

        //with this activate the menu
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);

        Log.v(TAG, "Hemos entrado en el TableFragment");

        View root = inflater.inflate(R.layout.fragment_table_list, container, false);

        //Acedemos a la lista
        ListView listTable = (ListView) root.findViewById(R.id.table_list);

        // Creamos un adaptador para poner en común el modelo con la lista
        ArrayAdapter<Table> adapter = new ArrayAdapter<Table>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mTables.getTables()
        );

        // Le asignamos el adaptador a la lista
        listTable.setAdapter(adapter);


        // Le asigno un listener a la lista para enterarme de cuándo se ha pulsado una fila
        listTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Avisamos al listener que el usuario ha pulsado una fila
                if (mOnTableSelectedListener != null) {
                    mOnTableSelectedListener.onTableSelected(position);
                }
            }
        });

        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof  OnTableSelectedListener) {
            mOnTableSelectedListener = (OnTableSelectedListener) getActivity();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getActivity() instanceof  OnTableSelectedListener) {
            mOnTableSelectedListener = (OnTableSelectedListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnTableSelectedListener = null;
    }

    public interface OnTableSelectedListener {
        void onTableSelected(int position);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

//        inflater.inflate(R.menu.menu_settings, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

























//    @Override
//    public void onTableClick(int position, Table table, View view) {
//        // Vamos a mostrar la vista de detalle
//        Intent intent = new Intent(getActivity(), TableDetailActivity.class);
//        intent.putExtra(TableDetailActivity.EXTRA_FORECAST, table);
//
//        startActivity(intent,
//                ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        getActivity(), // Contexto
//                        view, // Vista origen común
//                        getString(R.string.transition_to_detail) // El nombre dentro de la vista destino
//                ).toBundle());
//    }

}
