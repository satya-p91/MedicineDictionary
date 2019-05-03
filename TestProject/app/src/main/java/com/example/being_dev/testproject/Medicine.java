package com.example.being_dev.testproject;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;

public class Medicine extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    TextView about,use,precaution,side,how;

    SQLiteDatabase db;
    DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        about =(TextView) findViewById(R.id.about);
        use = (TextView) findViewById(R.id.use);
        how = (TextView) findViewById(R.id.how);
        side = (TextView) findViewById(R.id.side);
        precaution = (TextView) findViewById(R.id.precaution);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            toolbar.setTitle(bundle.getString("c"));
        }







        dbHelper = new DbHelper(this);

        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }


        StringBuilder q=new StringBuilder();
        q.append("select * from drug where name='");
        q.append(bundle.getString("c").toString());
        q.append("'");


        if (dbHelper.openDataBase()) {
            Cursor c = db.rawQuery(q.toString(), null);
            if(c.moveToFirst()) {
                this.about.setText(c.getString(2));
                this.use.setText(c.getString(3));
                this.how.setText(c.getString(4));
                this.side.setText(c.getString(5));
                this.precaution.setText(c.getString(6));


            }else{
                this.about.setText("Value not found error");
                this.use.setText("bakchodi ho rha h yaar ye toh. XD");
            }

        }





    }

}
