package com.jro.restaurant.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jro.restaurant.R;
import com.jro.restaurant.activity.DishDetailActivity;
import com.jro.restaurant.model.Dish;

/**
 * Created by jro on 10/12/2016.
 */

public class DishDetailFragment extends Fragment {

    private final static String TAG = DishDetailActivity.class.getCanonicalName();

    private OnDishAddListener mOnDishAddListener;

    private ImageView mImage;
    private TextView mDishName;
    private TextView mIngredients;
    private TextView mAllergens;
    private TextView mPrice;
    private TextView mObservations;
    private Button mAddDish;

    private final static String ARG_DISH = "arg_dish";

    private Dish mDish;

    public static DishDetailFragment newInstance(Dish dish) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_DISH, dish);

        DishDetailFragment dishDetailFragment = new DishDetailFragment();
        dishDetailFragment.setArguments(arguments);

        return dishDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mDish = (Dish) getArguments().getSerializable(ARG_DISH);
        }

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_dish_detail, container, false);

        // Obtain de View fields
        mImage = (ImageView) root.findViewById(R.id.imageView);
        mDishName = (TextView) root.findViewById(R.id.detail_dish_name);
        mIngredients = (TextView) root.findViewById(R.id.dish_detail_ingredients);
        mAllergens = (TextView) root.findViewById(R.id.dish_detail_allergens);
        mPrice = (TextView) root.findViewById(R.id.dish_detail_price);
        mObservations = (TextView) root.findViewById(R.id.dish_detail_observations);
        mAddDish = (Button) root.findViewById(R.id.addDish_btn);

        // contribute the fields with model
        mImage.setImageResource(mDish.getnPhoto());
        mDishName.setText(mDish.getName());
        mIngredients.setText(mDish.getIngredientesDescription());
        mPrice.setText(mDish.getPriceDescription());
        mAllergens.setText(mDish.getAllergenDescription());
        mObservations.setText("");

        mAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "***************** DishDetailFragment.OnClickSelected *****************");
                Dish newDish = new Dish(mDishName.getText().toString(), mDish.getIngredientes(), mImage.getId(), mDish.getAllergen(), mDish.getPrice(), mObservations.getText().toString());
                if (mOnDishAddListener != null){
                    mOnDishAddListener.onAddDishClick(newDish);
                }
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getActivity() instanceof OnDishAddListener) {
            mOnDishAddListener = (OnDishAddListener) getActivity();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();

        mOnDishAddListener = null;
    }


    public interface OnDishAddListener {
        public void onAddDishClick(Dish dish);
    }

}
