package com.jro.restaurant.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jro.restaurant.R;
import com.jro.restaurant.model.Dish;

import java.util.List;

/**
 * Created by jro on 09/12/2016.
 */

public class DishAdapter extends ArrayAdapter<Dish> {

    private final List<Dish> mList;
    private final Context mContext;

    public DishAdapter(Context context, List<Dish> list) {
        super(context, R.layout.item_dish_list, list);
        this.mContext = context;
        this.mList = list;
    }

    // put tha fields we want to represent in the list item
    static class ViewHolder {
        protected ImageView imageDish;
        protected TextView nameDish;
        protected TextView priceDish;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        super.getView(position, convertView, parent);

        View view = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.item_dish_list, null); // the second parameter is dteh parent ut we always set it null, in other case it doesn`t work
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageDish = (ImageView) view.findViewById(R.id.dishListImage);
            viewHolder.nameDish = (TextView) view.findViewById(R.id.dishListName);
            viewHolder.priceDish = (TextView) view.findViewById(R.id.dishListPrice);

            view.setTag(viewHolder);



        }else{
            view = convertView;
//            ((ViewHolder) view.getTag())

        }
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.imageDish.setImageResource(mList.get(position).getnPhoto());
        holder.nameDish.setText(mList.get(position).getName());
        holder.priceDish.setText(mList.get(position).getPriceDescription());

        return view;
    }
}
