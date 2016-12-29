package com.spinnerdatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText name,address;
    Spinner city;
    SQLiteDatabase db;
    Button store,display;
    ArrayList<String> cityid,cityname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText)findViewById(R.id.editText);
        address=(EditText)findViewById(R.id.editText2);
        city=(Spinner)findViewById(R.id.spinner);
        store=(Button)findViewById(R.id.button);
        display=(Button)findViewById(R.id.button2);
        db=openOrCreateDatabase("temp.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
        try{
            db.execSQL("create table city(cid Integer Primary Key Autoincrement,cityname Text)");
            db.execSQL("create table details(did Integer Primary Key Autoincrement,name Text,address Text,cityid Integer)");
            ContentValues inp=new ContentValues();
            inp.put("cityname","Ahmedabad");
            db.insert("city",null,inp);
            inp.clear();
            inp.put("cityname","Banglore");
            db.insert("city",null,inp);
            inp.clear();
            inp.put("cityname","Pune");
            db.insert("city",null,inp);
            inp.clear();
        }
        catch (Exception e){
        }
        Cursor cr=db.rawQuery("select * from city",null);
        cityid=new ArrayList<>();
        cityname=new ArrayList<>();
        while(cr.moveToNext()){
            cityid.add(cr.getString(0));
            cityname.add(cr.getString(1));
        }
        ArrayAdapter<String> ad=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,cityname);
        city.setAdapter(ad);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv=new ContentValues();
                cv.put("name",name.getText().toString());
                cv.put("address",address.getText().toString());
                cv.put("cityid",cityid.get(city.getSelectedItemPosition()));
                db.insert("details",null,cv);
                //Toast.makeText(MainActivity.this,city.getSelectedItemPosition(),Toast.LENGTH_LONG).show();
            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c=db.rawQuery("select * from details",null);
                while(c.moveToNext()){
                    String str=c.getString(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3);
                    Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
