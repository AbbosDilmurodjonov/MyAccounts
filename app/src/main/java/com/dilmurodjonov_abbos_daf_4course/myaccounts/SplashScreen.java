package com.dilmurodjonov_abbos_daf_4course.myaccounts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SplashScreen extends AppCompatActivity {

    private  String password = "";
    private static final int COUNT_VIEW = 8;
    View []dotViews = new View[COUNT_VIEW];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView[]nubmerText = new TextView[10];

        nubmerText[0] = findViewById(R.id.t_0);
        nubmerText[1] = findViewById(R.id.t_1);
        nubmerText[2] = findViewById(R.id.t_2);
        nubmerText[3] = findViewById(R.id.t_3);
        nubmerText[4] = findViewById(R.id.t_4);
        nubmerText[5] = findViewById(R.id.t_5);
        nubmerText[6] = findViewById(R.id.t_6);
        nubmerText[7] = findViewById(R.id.t_7);
        nubmerText[8] = findViewById(R.id.t_8);
        nubmerText[9] = findViewById(R.id.t_9);



        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password += ((TextView)v).getText().toString();

                if(COUNT_VIEW < password.length())return;

                LinearLayout l = findViewById(R.id.password_linear);
                dotViews[password.length() - 1] = new View(SplashScreen.this);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(50,50);
                p.setMargins(15,0,15,0);
                dotViews[password.length() - 1].setLayoutParams(p);
                dotViews[password.length() - 1].setBackgroundResource(R.drawable.circle);

                l.addView(dotViews[password.length() - 1]);


            }
        };
        for(TextView t : nubmerText) t.setOnClickListener(listener);

        TextView backspaceTextView = findViewById(R.id.backspace_text);

        backspaceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!password.isEmpty()){

                    if(password.length() <= 8){
                        LinearLayout l = findViewById(R.id.password_linear);
                        l.removeView(dotViews[password.length() - 1]);
                    }

                    if(password.length() == 1){
                        password = "";
                    }
                    else{
                        password = password.substring(0,password.length() - 1);
                    }


                }


            }


        });




        TextView okTextView = findViewById(R.id.ok_text);

        okTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO check password, if it is true - > go to main activity
                DBHelper dbHelper = new DBHelper(SplashScreen.this);

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Cursor c = db.query("accounts", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false

                boolean check = true;

                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int emailColIndex = c.getColumnIndex("password");
                    System.out.println("pass: " + password);
                    do {

                        System.out.println("db: " + c.getString(emailColIndex));
                        if(password.equals(c.getString(emailColIndex))) {
                            //Toast.makeText(SplashScreen.this, "Welcome", Toast.LENGTH_SHORT).show();
                            String name = c.getString(nameColIndex);
                            int id = c.getInt(idColIndex);
                            check = false;

                            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("id",id);
                            startActivity(intent);
                            finish();

                            break;
                        }
                    } while (c.moveToNext());
                }
                if(check){

                    Toast.makeText(SplashScreen.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    LinearLayout l = findViewById(R.id.password_linear);
                    l.removeAllViews();
                    password = "";
                }

                c.close();
                dbHelper.close();

            }
        });

        TextView createAccount = findViewById(R.id.reg_text);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(SplashScreen.this);

                View view = LayoutInflater.from(SplashScreen.this).inflate(R.layout.dialog_item,null);
                dialog.setView(view);

                final EditText etName = view.findViewById(R.id.username);
                final EditText etPass = view.findViewById(R.id.password);


                        dialog.setPositiveButton("Create Account", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {

                        //TODO sign in

                        //ContentValues cv = new ContentValues();


                        String name = etName.getText().toString();
                        String password = etPass.getText().toString();

                        DBHelper dbHelper = new DBHelper(SplashScreen.this);

                        SQLiteDatabase db = dbHelper.getWritableDatabase();



                        Cursor c = db.query("accounts", null, null, null, null, null, null);

                        boolean check = true;
                        // ставим позицию курсора на первую строку выборки
                        // если в выборке нет строк, вернется false
                        if (c.moveToFirst()) {

                            // определяем номера столбцов по имени в выборке
                            int emailColIndex = c.getColumnIndex("password");

                            do {

                                if(password.equals(c.getString(emailColIndex))) {
                                    Toast.makeText(SplashScreen.this, "Error: Please, change your password!", Toast.LENGTH_LONG).show();
                                    check = false;
                                    break;
                                }
                            } while (c.moveToNext());
                        }
                        if(check){
                            ContentValues cv = new ContentValues();
                            cv.put("name", name);
                            cv.put("password", password);
                            db.insert("accounts", null, cv);
                            Toast.makeText(SplashScreen.this, "Successful", Toast.LENGTH_SHORT).show();
                        }

                        c.close();
                        dbHelper.close();
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

}
