package com.example.baith2.fragment;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baith2.AddActivity;
import com.example.baith2.R;
import com.example.baith2.UpdateActive;
import com.example.baith2.adapter.RecycleViewAdapter;
import com.example.baith2.dal.SQLiteHelper;
import com.example.baith2.model.Item;

import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener, RecycleViewAdapter.ItemListener {

    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private SQLiteHelper db;
    private TextView tvTong;
    private SearchView searchView;
    private EditText editText, eFrom, eTo;
    private Spinner spCategory;
    private Button btSearch;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     initView(view);
     adapter = new RecycleViewAdapter();
     db = new SQLiteHelper(getContext());
     List<Item> list = db.getAll();
     adapter.setList(list);
    tvTong.setText("Tong tien:"+tongChi(list));
    LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
    recyclerView.setLayoutManager(manager);
    recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Item> list = db.getItemByTitle(s);
                tvTong.setText("Tong tien:"+tongChi(list));
                adapter.setList(list);
                return true;
            }
        });

        eFrom.setOnClickListener(this);
        eTo.setOnClickListener(this);
        btSearch.setOnClickListener(this);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int p, long l) {
                String cate = spCategory.getItemAtPosition(p).toString();
                List<Item> list ;
                if(!cate.equalsIgnoreCase("all")) {
                    list = db.getItemByCategory(cate);
                } else {
                    list = db.getAll();
                }
                adapter.setList(list);
                tvTong.setText("Tong tien:"+tongChi(list));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        adapter.setItemListener(this);
    }

    private void initView(View view) {
        recyclerView=view.findViewById(R.id.recycleView);
        tvTong=view.findViewById(R.id.tvTong);
        btSearch = view.findViewById(R.id.btnSearch);
        searchView = view.findViewById(R.id.search);
        eFrom = view.findViewById(R.id.eFrom);
        eTo = view.findViewById(R.id.eTo);
        spCategory=view.findViewById(R.id.spCategory);
        String[] arr = getResources().getStringArray(R.array.category);
        String[] arr1 = new String[arr.length+1];
        arr1[0]="all";
        for(int i =0;i<arr.length;i++) {
            arr1[i+1] =arr[i];
        }
        spCategory.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_spinner, arr1));
    }

    private int tongChi(List<Item> list) {

        int i =0;
        for (Item item:list) {

            i+= Integer.parseInt(item.getPrice());
        }
        return  i;
    }
    @Override
    public void onItemClick(View view, int positon) {
        Log.i("item Click", "ssssssssss");
        Item item = adapter.getItem(positon);
        Intent intent = new Intent(getActivity(), UpdateActive.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view == eFrom) {
            final Calendar c =  Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if (m>8) {
                        date = d +"/"+(m+1)+"/"+y;
                    } else {
                        date = d +"/0"+(m+1)+"/"+y;
                    }

                    eFrom.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if (view == eTo) {
            final Calendar c =  Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if (m>8) {
                        date = d +"/"+(m+1)+"/"+y;
                    } else {
                        date = d +"/0"+(m+1)+"/"+y;
                    }

                    eTo.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if(view == btSearch) {
            String from = eFrom.getText().toString();
            String to = eTo.getText().toString();
            if(!from.isEmpty() && !to.isEmpty()) {
                List<Item> list =db.getItemByDateFromTo(from,to);
                adapter.setList(list);
                tvTong.setText("Tong tien:"+tongChi(list));
            }
        }
    }


}
