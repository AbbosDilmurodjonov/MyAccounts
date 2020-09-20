package com.dilmurodjonov_abbos_daf_4course.myaccounts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean IS_PAUSE = false;
    private long TIME_PAUSE = 0;
    private long TIME_START = 0;

    private int ACCOUNT_ID = 0;
    List<MyData> myList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ACCOUNT_ID = getIntent().getIntExtra("id",0);

        TextView accName = findViewById(R.id.acc_name);
        accName.setText(getIntent().getStringExtra("name"));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);



        final RecycleAdapter adapter = new RecycleAdapter(this, readData(myList));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.add_btn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_info, null);
                dialog.setView(view);

                final EditText etName = view.findViewById(R.id.name);
                final EditText etLogin = view.findViewById(R.id.login);
                final EditText etPass = view.findViewById(R.id.password);
                final EditText etComment = view.findViewById(R.id.comment);


                dialog.setPositiveButton(" Add ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {

                        //TODO sign in

                        //ContentValues cv = new ContentValues();


                        String name = etName.getText().toString();
                        String login = etLogin.getText().toString();
                        String password = etPass.getText().toString();
                        String comment = etComment.getText().toString();

                        DBHelper dbHelper = new DBHelper(MainActivity.this);

                        SQLiteDatabase db = dbHelper.getWritableDatabase();


                        Cursor c = db.query("sites", null, null, null, null, null, null);


                        if(name.isEmpty() || login.isEmpty() || password.isEmpty())
                        {
                            Toast.makeText(MainActivity.this, "Invalid information", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            ContentValues cv = new ContentValues();
                            cv.put("name", name);
                            cv.put("login", login);
                            cv.put("password", password);
                            cv.put("comment", comment);
                            cv.put("account_id", ACCOUNT_ID);
                            db.insert("sites", null, cv);
                            Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        }

                        c.close();
                        dbHelper.close();


                        adapter.setMyList(readData(myList));
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.setCancelable(false);
                dialog.show();
            }
        });


    }

    private List<MyData> readData(List<MyData> myList){
        if(myList != null)
            myList.clear();
        else
            myList = new ArrayList<>();

        DBHelper dbHelper = new DBHelper(MainActivity.this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query("sites", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false


        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("account_id");
            int nameColIndex = c.getColumnIndex("name");
            int loginColIndex = c.getColumnIndex("login");
            int passColIndex = c.getColumnIndex("password");
            int commentColIndex = c.getColumnIndex("comment");

            do {

                System.out.println( ACCOUNT_ID + "; "+ c.getString(nameColIndex));

                if(ACCOUNT_ID == c.getInt(idColIndex)){

                    MyData myData = new MyData();
                    myData.setName(c.getString(nameColIndex));
                    myData.setPassword(c.getString(passColIndex));
                    myData.setLogin(c.getString(loginColIndex));
                    myData.setComment(c.getString(commentColIndex));

                    myList.add(myData);
                }

            } while (c.moveToNext());
        }


        c.close();
        dbHelper.close();

        return myList;
    }

    @Override
    protected void onStart() {
        super.onStart();

        TIME_START = Calendar.getInstance().getTimeInMillis();
        System.out.println("" + TIME_START);
        if (IS_PAUSE) {

            if (TIME_START - TIME_PAUSE > 10000) {
                Intent intent = new Intent(MainActivity.this, SplashScreen.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        IS_PAUSE = true;

        TIME_PAUSE = Calendar.getInstance().getTimeInMillis();

        System.out.println("" + TIME_PAUSE);

    }
}
