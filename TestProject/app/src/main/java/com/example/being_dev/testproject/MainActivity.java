package com.example.being_dev.testproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity{

    Toolbar toolbar;

    boolean d=false;

    SQLiteDatabase db;
    ListView listView;
    DbHelper dbHelper;

  //  ListAdapter listAdapter;

    ArrayAdapter<String> listAdapter;

    private static String DB_NAME = "info.db";
    private static String DB_PATH = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        toolbar = findViewById(R.id.t);

        toolbar.setTitle("Medicine");

        setSupportActionBar(toolbar);



        dbHelper = new DbHelper(MainActivity.this);

        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }


        listView = findViewById(R.id.lst);

        final ArrayList<String> namelist = getData();

//        listAdapter = new SimpleAdapter(MainActivity.this, namelist, R.layout.items, new String[]{"name"}, new int[]{R.id.item});


          listAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.items, R.id.item, namelist);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=  new Intent(MainActivity.this, Medicine.class);



                intent.putExtra("c", listView.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });


        listView.setAdapter(listAdapter);


    }



    //for back button click;

    @Override
    public void onBackPressed() {
        if(d) {
            super.onBackPressed();
            return;
        }
        this.d=true;
        Toast.makeText(this , "Please click BACK again to exit" , Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                d = false;
            }
        } , 2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.search_action);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String sn) {

                listAdapter.getFilter().filter(sn);
                return false;
            }
        });




        return true;
    }










    public ArrayList<String> getData() {

        ArrayList<String> nameList = new ArrayList<>();
        if (dbHelper.openDataBase()) {
            Cursor c = db.rawQuery("select name from drug", null);
            while (c.moveToNext()) {
                String name = c.getString(c.getColumnIndex("name"));
                nameList.add(name);
            }

        }
        return nameList;

    }



}
