package com.jro.restaurant.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.jro.restaurant.R;
import com.jro.restaurant.model.Table;
import com.jro.restaurant.model.Tables;

public class TableDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TABLES = "EXTRA_TABLES";
    public static final String EXTRA_TABLE = "EXTRA_TABLE";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";

    public static final String TAG = TableDetailActivity.class.getCanonicalName();
    public static final int DISH_ADD_RESULT = 0;

    private ImageView mImage;
    private TextView mDishesOrderes;
    private TextView mNumPersons;
    private TextView mTableObservations;
    private Button mAddDishBtn;
    private Button mShowBillBtn;
    private TextView mDishesNum;
    private TextView mTableNum;

    private Table table;
    private static Tables tables = new Tables();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_detail);

        buildView();

        position = getIntent().getIntExtra(EXTRA_POSITION, -1);

        if (position != -1){
            table = tables.getTable(position);

            mTableNum.setText(returnNumber(table.getNumber()));
            mDishesOrderes.setText(table.getDishesName());
            mNumPersons.setText(table.getNumPerson().toString());
            mTableObservations.setText(table.getObservations());
            mDishesNum.setText(table.getNumDish().toString());
        }

        mAddDishBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Log.v(TAG, "He entrado en el addDishBtn");

                // Vamos a mostrar la vista de detalle
                Intent intent = new Intent(view.getContext(), DishMenuActivity.class);
                intent.putExtra(DishMenuActivity.TABLE_SELECTED, table);

                startActivityForResult(intent, DISH_ADD_RESULT);
            }
        });

        mShowBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "He entrado en el showBillBtn");

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                String bill = table.getBill().toString() + "€";
                builder.setMessage(bill)
                        .setTitle("Your Bill is:");


                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    private void buildView() {
        mImage = (ImageView) findViewById(R.id.table_image);
        mTableNum = (TextView) findViewById(R.id.table_number);
        mDishesOrderes = (TextView) findViewById(R.id.dishes_ordered);
        mDishesNum = (TextView) findViewById(R.id.dishes_num);
        mNumPersons = (TextView) findViewById(R.id.num_persons);
        mTableObservations = (TextView) findViewById(R.id.table_observations);
        mAddDishBtn = (Button) findViewById(R.id.addDish_btn);
        mShowBillBtn = (Button) findViewById(R.id.showBill_btn);
    }

    private String returnNumber(Number tableNumber) {

        String number = "0";
        switch (tableNumber.intValue()){
            case 1:
                number = "1";
                break;
            case 2:
                number = "2";
                break;
            case 3:
                number = "3";
                break;
            case 4:
                number = "4";
                break;
            case 5:
                number = "5";
                break;
            case 6:
                number = "6";
                break;
            case 7:
                number = "7";
                break;
            case 8:
                number = "8";
                break;
            case 9:
                number = "9";
                break;
        }

        return number;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.v(DishMenuActivity.TAG, "OnActivityResult TableDetailActivity");

        if (requestCode == DISH_ADD_RESULT && resultCode == RESULT_OK) {

            table = (Table) data.getSerializableExtra(EXTRA_TABLE);
            tables.getTables().set(position, table);
            syncModelWithView(table);
        }
//        else{
//            Intent returnIntent = new Intent();
//            returnIntent.putExtra(MainActivity.EXTRA_TABLES, tables);
//
//            setResult(RESULT_OK, returnIntent);
//
//            finish();
//        }
    }

    private void syncModelWithView(Table table) {

        mImage = (ImageView) findViewById(R.id.table_image);
        mTableNum = (TextView) findViewById(R.id.table_number);
        mDishesOrderes = (TextView) findViewById(R.id.dishes_ordered);
        mDishesNum = (TextView) findViewById(R.id.dishes_num);
        mNumPersons = (TextView) findViewById(R.id.num_persons);
        mTableObservations = (TextView) findViewById(R.id.table_observations);
        mAddDishBtn = (Button) findViewById(R.id.addDish_btn);
        mShowBillBtn = (Button) findViewById(R.id.showBill_btn);

        mTableNum.setText(returnNumber(table.getNumber()));
        mDishesOrderes.setText(table.getDishesName());
        mNumPersons.setText(table.getNumPerson().toString());
        mTableObservations.setText(table.getObservations());
        mDishesNum.setText(table.getNumDish().toString());
    }



}
