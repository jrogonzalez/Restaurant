package com.jro.restaurant.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jro.restaurant.R;
import com.jro.restaurant.fragment.DishDetailFragment;
import com.jro.restaurant.model.Dish;

public class DishDetailActivity extends AppCompatActivity implements DishDetailFragment.OnDishAddListener{

    public static final String TAG = DishDetailActivity.class.getCanonicalName();

    public static final String EXTRA_DISH = "EXTRA_DISH";
    public static final String EXTRA_ADD_COURSE = "EXTRA_ADD_COURSE";

    private Dish dish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);

        Log.v(DishDetailActivity.TAG, "Hemos entrado en elDishDetailActivity");

        dish = (Dish) getIntent().getSerializableExtra(EXTRA_DISH);

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.fragment_dish_detail) == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_dish_detail, DishDetailFragment.newInstance(dish))
                    .commit();
        }

    }

    @Override
    public void onAddDishClick(Dish dish) {
        Log.v(DishDetailActivity.TAG, "***************** DishDetailActivity.OnAddDishClick *****************");

        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_ADD_COURSE, dish);

        setResult(RESULT_OK, returnIntent);

        finish();
    }
}
