package com.jro.restaurant.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jro.restaurant.R;
import com.jro.restaurant.adapter.DishAdapter;
import com.jro.restaurant.model.Allergen;
import com.jro.restaurant.model.Dish;
import com.jro.restaurant.model.Dishes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jro on 08/12/2016.
 */

public class DishFragment extends BaseVolleyFragment {

    private final static String TAG = DishFragment.class.getCanonicalName();
    private OnDishSelectedListener mOnDishSelectedListener;
    private OnDataLoadedListener mOnDataLoadedListener;

    private Dishes mDishes = null;
    private ListView mListDishes;
    private ArrayAdapter adapter;

    private Boolean downloadedDishes = false;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //with this activate the menu
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.v(TAG, "Hemos entrado en el DishFragment");

        View root = inflater.inflate(R.layout.fragment_dishes_list, container, false);

        //Acedemos a la lista
        mListDishes = (ListView) root.findViewById(R.id.dishes_list);

        // Le asigno un listener a la lista para enterarme de cuándo se ha pulsado una fila
        mListDishes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Avisamos al listener que el usuario ha pulsado una fila
                if (mOnDishSelectedListener != null) {
                    mOnDishSelectedListener.onDishSelected(mDishes.getDish(position), position);
                }
            }
        });

        preferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = preferences.edit();

        downloadedDishes = preferences.getBoolean("downloadedDishes", false);
        if (!downloadedDishes) {
            Log.v(TAG, "********************** DOWNLOAD FROM REMOTE **********************");
            makeRequest();

        }else{
            Log.v(TAG, "********************** DOWNLOAD FROM LOCAL **********************");
            String jsonString = loadDataFromFile(getActivity());
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonString);
                mDishes = new Dishes(createDishes(jsonObject.getJSONArray("results")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Creamos un adaptador para poner en común el modelo con la lista
            adapter = new DishAdapter(getActivity(),mDishes.getDishes());

            // Le asignamos el adaptador a la lista
            mListDishes.setAdapter(adapter);

            // Avisamos al listener que el usuario ha pulsado una fila
            if (mOnDataLoadedListener != null) {
                mOnDataLoadedListener.onDataLoaded();
            }
        }

        return root;
    }


    private void makeRequest(){
        String url = "http://www.mocky.io/v2/584c88831200002a29372b30";

        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if ("OK".equals(jsonObject.get("status"))){
                        saveDataToFile(getActivity(), jsonObject);
                        mDishes = new Dishes(createDishes(jsonObject.getJSONArray("results")));
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Creamos un adaptador para poner en común el modelo con la lista
                adapter = new DishAdapter(getActivity(),mDishes.getDishes());

                // Le asignamos el adaptador a la lista
                mListDishes.setAdapter(adapter);

                editor.putBoolean("downloadedDishes", true);
                editor.apply();

                // Avisamos al listener que el usuario ha pulsado una fila
                if (mOnDataLoadedListener != null) {
                    mOnDataLoadedListener.onDataLoaded();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onConnectionFailed(volleyError.toString());
            }
        });
        addToQueue(request);
    }

    @NonNull
    private Dish[] createDishes(JSONArray jsonArray) throws JSONException {
        List<Dish> dishList = new ArrayList<Dish>();

        for (int i = 0; i <jsonArray.length()-1 ; i++) {
            String name = jsonArray.getJSONObject(i).getString("name");

            JSONArray ingredientsJSON = jsonArray.getJSONObject(i).getJSONArray("ingredients");
            String[] ingredients = createIngredients(ingredientsJSON);

            String photo = jsonArray.getJSONObject(i).getString("photo");
            //Parse imageText to drawable
            String photoNum = photo.substring(photo.length()-2,photo.length());
            int photoInt = Integer.parseInt(photoNum);
            int photoImage = 1;
            switch (photoInt){
                case 1:
                    photoImage = R.drawable.paella_01;
                    break;
                case 2:
                    photoImage = R.drawable.cocido_02;
                    break;
                case 3:
                    photoImage = R.drawable.berenjenas_03;
                    break;
                case 4:
                    photoImage = R.drawable.pollo_04;
                    break;
                case 5:
                    photoImage = R.drawable.salmon_05;
                    break;
                case 6:
                    photoImage = R.drawable.revolconas_06;
                    break;
                case 7:
                    photoImage = R.drawable.lentejas_07;
                    break;
                case 8:
                    photoImage = R.drawable.arrozforn_08;
                    break;
                case 9:
                    photoImage = R.drawable.ensalada_09;
                    break;
                case 10:
                    photoImage = R.drawable.coulant_10;
                    break;
            }

            Double price = jsonArray.getJSONObject(i).getDouble("price");
            JSONArray allergeenList = jsonArray.getJSONObject(i).getJSONArray("allergen");
            Allergen[] allergen = createAllergen(allergeenList);
            Dish dish = new Dish(name, ingredients, photoImage, allergen, price, null);
            dishList.add(dish);
        }

        return dishList.toArray(new Dish[0]);
    }

    @NonNull
    private String[] createIngredients(JSONArray ingredientsJSON) throws JSONException {
        List<String> ingredientsList = new ArrayList<String>();

        for (int i = 0; i <ingredientsJSON.length() ; i++) {
            ingredientsList.add(ingredientsJSON.getString(i));
        }
        return ingredientsList.toArray(new String[0]);
    }

    @NonNull
    private Allergen[] createAllergen(JSONArray inAllergenList) throws JSONException {
        List<Allergen> allergenList = new ArrayList<Allergen>();
        for (int i = 0; i < inAllergenList.length(); i++) {
            Allergen allergen = new Allergen(inAllergenList.getString(i));
            allergenList.add(allergen);
        }

        return allergenList.toArray(new Allergen[0]);
    }


    public interface OnDishSelectedListener {
        void onDishSelected(Dish dish, int position);
    }

    public interface OnDataLoadedListener {
        void onDataLoaded();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof DishFragment.OnDishSelectedListener) {
            mOnDishSelectedListener = (DishFragment.OnDishSelectedListener) getActivity();
        }

        if (getActivity() instanceof DishFragment.OnDataLoadedListener) {
            mOnDataLoadedListener = (DishFragment.OnDataLoadedListener) getActivity();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getActivity() instanceof DishFragment.OnDishSelectedListener) {
            mOnDishSelectedListener = (DishFragment.OnDishSelectedListener) getActivity();
        }

        if (getActivity() instanceof DishFragment.OnDataLoadedListener) {
            mOnDataLoadedListener = (DishFragment.OnDataLoadedListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnDishSelectedListener = null;
        mOnDataLoadedListener = null;
    }


    private static void saveDataToFile(Context context, JSONObject jsonObject) {
        try {
            File dataDirectory = context.getDir("AppData", Context.MODE_PRIVATE);
            if (!dataDirectory.exists()) {
                dataDirectory.mkdir();
            }

            File file = new File(dataDirectory, "JSONMenu.json");
            OutputStream out = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
            outputStreamWriter.write(jsonObject.toString());
            outputStreamWriter.close();
        }
        catch (IOException exc) {
            Log.e(context.getString(R.string.app_name), "Error while saving app data file: " + exc.getMessage());
        }
    }

    public static String loadDataFromFile(Context context) {
        String json = null;

        File dataDirectory = context.getDir("AppData", Context.MODE_PRIVATE);
        if (!dataDirectory.exists()) {
            dataDirectory.mkdir();
        }

        File jsonFile = new File(dataDirectory, "JSONMenu.json");

        if (jsonFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(jsonFile);
                int size = fis.available();
                byte[] buffer = new byte[size];
                fis.read(buffer);
                fis.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }

        }
        return json;
    }

}
