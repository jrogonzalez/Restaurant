package com.jro.restaurant.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ViewSwitcher;

import com.jro.restaurant.R;
import com.jro.restaurant.fragment.DishFragment;
import com.jro.restaurant.model.Dish;
import com.jro.restaurant.model.Table;

public class DishMenuActivity extends AppCompatActivity implements DishFragment.OnDishSelectedListener, DishFragment.OnDataLoadedListener {

    public static final String TAG = DishMenuActivity.class.getCanonicalName();
    public static final String TABLE_SELECTED = "TABLE_SELECTED";
    public static final int DISH_ADD_RESULT = 0;

    private static final int LOADING_VIEW_INDEX = 0;
    private static final int DISHES_VIEW_INDEX = 1;

    private ViewSwitcher mViewSwitcher;
    private Table table;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //with this activate the menu
        setContentView(R.layout.activity_dish_menu);

        table = (Table) getIntent().getSerializableExtra(TABLE_SELECTED);

        mViewSwitcher = (ViewSwitcher) findViewById(R.id.view_switcher);
        mViewSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mViewSwitcher.setOutAnimation(this, android.R.anim.fade_out);

        mViewSwitcher.setDisplayedChild(LOADING_VIEW_INDEX);

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.fragment_dishes_list) == null) {

            fm.beginTransaction()
                    .add(R.id.fragment_dishes_list, new DishFragment())
                    .commit();
        }

    }

    @Override
    public void onDishSelected(Dish dish, int position) {

        Log.v(DishMenuActivity.TAG, "OnDishSelected_INI");

        Intent intent = new Intent(this, DishDetailActivity.class);
        intent.putExtra(DishDetailActivity.EXTRA_DISH, dish);
        startActivityForResult(intent, DISH_ADD_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.v(DishMenuActivity.TAG, "**************** DishMenuActivity.OnActivityResult ****************");

        if (requestCode == DISH_ADD_RESULT && resultCode == RESULT_OK) {

            Dish dish = (Dish) data.getSerializableExtra(DishDetailActivity.EXTRA_ADD_COURSE);
            table.addDish(dish);

            Intent returnIntent = new Intent();
            returnIntent.putExtra(TableDetailActivity.EXTRA_TABLE, table);

            setResult(RESULT_OK, returnIntent);

            finish();
        }


    }

    @Override
    public void onDataLoaded() {
        mViewSwitcher.setDisplayedChild(DISHES_VIEW_INDEX);
    }
}
