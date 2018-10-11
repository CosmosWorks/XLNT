package technologies.lnthales.com.lttech;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView lv;
    MyDataBase md;
    Context c = this;
    MainActivity cs = new MainActivity();
    public static final String TAG = "TIMEWRAP";
    ArrayList<StudentInfo> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        lv = findViewById(R.id.listView3);
        md = new MyDataBase(this);

        final MyDataBase myDataBase;
        final StudentInfo studentInfo = new StudentInfo();
        myDataBase = new MyDataBase(SearchActivity.this);
        List<StudentInfo> list = myDataBase.getAllData();
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
        View mView = layoutInflaterAndroid.inflate(R.layout.user_input_layout2, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
        alertDialogBuilderUserInput.setView(mView);


        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);


        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {


                        String s = userInputDialogEditText.getText().toString();
                        getEmployee(s);
                        Log.e(TAG, "Search Started");
                        cs.check("Searching for Employee Name");


                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                                Date ca = Calendar.getInstance().getTime();
                                startActivity(new Intent(SearchActivity.this, MainActivity.class));
                                Log.e(TAG, "User tapped cancel,sending him back to Main Interface" + ca);
                                cs.check("User tapped cancel,sending back to Main Interface >.< " + ca);

                            }
                        });


        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
        Date ca = Calendar.getInstance().getTime();

        Log.e(TAG, "Displaying alert dialog from Search Activity " + ca);
        cs.check("Displaying alert dialog " + ca);


    }

    private void getEmployee(String searchTerm) {
        list = new ArrayList<>();
        MyDataBase db = new MyDataBase(this);
        StudentInfo p = new StudentInfo();

        Cursor c = db.retrieve(searchTerm);

        while (c.moveToNext()) {
            int id = c.getInt(0);
            String name = c.getString(2);
            String ps = c.getString(1);
            String email = c.getString(3);
            String phone = c.getString(4);

            p = new StudentInfo();
            p.setId(id);
            p.setName(name);
            p.setPs(ps);
            p.setEmail(email);
            p.setPh(phone);


            Toast.makeText(SearchActivity.this, "Found" + name, Toast.LENGTH_SHORT).show();
            list.add(p);

            CustomAdapters adapters = new CustomAdapters(this, list);
            lv.setAdapter(adapters);
        }

            }


        }



