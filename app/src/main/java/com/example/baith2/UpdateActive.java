package com.example.baith2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.baith2.dal.SQLiteHelper;
import com.example.baith2.model.Item;

import java.util.Calendar;

public class UpdateActive extends AppCompatActivity implements View.OnClickListener {
    public Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btnUpdate, btnCancel, btnDelete;
    private Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_active);
        initView();
        btnUpdate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        eDate.setOnClickListener(this);
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");
        eTitle.setText(item.getTitle());
        ePrice.setText(item.getPrice());
        eDate.setText(item.getDate());

        int p = 0;
        for(int i=0; i<sp.getCount(); i++) {
            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory()) ) {
                p=i;
                break;
            }
        }

        sp.setSelection(p);
    }

    private void initView() {
        sp = findViewById(R.id.spCategory);
        eTitle = findViewById(R.id.tvTitle);
        ePrice = findViewById(R.id.tvPrice);
        eDate = findViewById(R.id.tvDate);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, getResources().getStringArray(R.array.category)));
    }

    @Override
    public void onClick(View view) {
        if (view == eDate) {
            final Calendar c =  Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(UpdateActive.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if (m>8) {
                        date = d +"/"+(m+1)+"/"+y;
                    } else {
                        date = d +"/0"+(m+1)+"/"+y;
                    }

                    eDate.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if(view == btnCancel) {
            finish();
        }

        if (view == btnUpdate) {
            String t = eTitle.getText().toString();
            String p = ePrice.getText().toString();
            String c = sp.getSelectedItem().toString();
            String d = eDate.getText().toString();
            Log.i("t", t);
            Log.i("p", p);
            Log.i("c", c);
            Log.i("d", d);
            Log.i("id", String.valueOf(item.getId()));

            if(!t.isEmpty() && p.matches("\\d+")) {
                Item i = new Item(item.getId(),t,c,p,d);
                SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
                sqLiteHelper.update(i);
                finish();

            }
        }

        if(view == btnDelete) {
            int id = item.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("THong bao xoa");
            builder.setMessage("Ban co chac chan muon xoa "+item.getTitle()+" khong ?");
            builder.setIcon(R.drawable.delete);
            builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
                    sqLiteHelper.delete(item.getId());
                    finish();
                }
            });

            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

}