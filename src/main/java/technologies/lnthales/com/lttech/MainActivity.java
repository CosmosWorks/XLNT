package technologies.lnthales.com.lttech;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tc;
    final Context c = this;
    private String TAG = "TIMEWRAP";
    MyDataBase myDataBase;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase.loadLibs(this);
        final Date ca = Calendar.getInstance().getTime();

        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2 @ " + ca);

            } else {

                Log.v(TAG, "Permission is revoked2 @" + ca);
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
        } else {
            Log.v(TAG, "Permission is granted2 @" + ca);
            check("Permission check success ! Already Granted >.< @" + ca);
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tc = findViewById(R.id.test);


        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(null);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(MainActivity.this,"root@Amar:# ",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date c2 = Calendar.getInstance().getTime();

                Log.i(TAG, "Tapped to add new record @" + c2);
                check("Trying to add new record ! @" + c2);
                final MyDataBase myDataBase;


                myDataBase = new MyDataBase(MainActivity.this);
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.user_input_layout, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);


                final EditText ed1 = (EditText) mView.findViewById(R.id.userInputDialog);
                final EditText ed2 = (EditText) mView.findViewById(R.id.editText);
                final EditText ed3 = (EditText) mView.findViewById(R.id.editText2);
                final EditText ed4 = (EditText) mView.findViewById(R.id.editText1);


                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {


                                String ps = ed1.getText().toString();
                                String name = ed2.getText().toString();
                                String email = ed3.getText().toString();
                                String ph = ed4.getText().toString();

                                try {
                                    final Date ca = Calendar.getInstance().getTime();

                                    boolean l = myDataBase.insertData(new StudentInfo(ps, name, email, ph));
                                    Log.i(TAG, "Is successful in inserting into db ? " + l + "@ " + ca);
                                    check("Is successful in inserting into db ? " + l + "@" + ca);
                                } catch (Exception ex) {

                                    Log.e(TAG, "Is successful in inserting into db ? " + ex + "@" + ca, ex);
                                    check("Exception" + ex + "@ " + ca);

                                }
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
                Snackbar.make(view, "You can now add a record to DB", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun) {
            String[] PS = {"449908", "994400", "99000409", "9900041", "2137484"};
            String[] name = {"NOVA", "StarDust", "Amar", "Andromeda", "TimeDrunk"};
            String[] email = {"abc@xyz", "lt@techservices", "space@exploration", "deepspace@nasa", "noob@pro"};
            String[] Ph = {"6789021", "2739109", "42600312", "3102381064", "536474"};

            myDataBase = new MyDataBase(this);
            myDataBase.deleteAll();
            Date ca = Calendar.getInstance().getTime();
            File folder = new File(Environment.getExternalStorageDirectory().toString() + "/TLOGS/");
            folder.mkdirs();
            Log.i(TAG, "Wiping out existing db " + "@" + ca);
            for (int i = 0; i <= 4; i++) {
                myDataBase.insertData(new StudentInfo(PS[i], name[i], email[i], Ph[i]));

            }



            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            Log.i(TAG, "Is this first Run ? " + isFirstRun);
            editor.commit();
        }



        final SwipeMenuListView lv = findViewById(R.id.listView);

        myDataBase = new MyDataBase(this);
        List<StudentInfo> list = MyDataBase.getInstance(MainActivity.this).getAllData();

        Custom3Edit adapter = new Custom3Edit(this, list);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final StudentInfo studentInfo;
                final int posi = i;


                StudentInfo studentInfo1;
                myDataBase = new MyDataBase(MainActivity.this);

                List<StudentInfo> list = myDataBase.getAllData();
                studentInfo1 = list.get(posi);
                String ps = studentInfo1.getPs();
                String name = studentInfo1.getName();
                String email = studentInfo1.getEmail();
                String ph = studentInfo1.getPh();


                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        " PS Number :" + ps + " Name :" + name + " Email Address :" + email + " Phone number :" + ph);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);


                // alertDialog.show();
                return false;
            }

        });


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0xCD,
                        0x66)));

                openItem.setWidth(190);
                // set item title
                openItem.setTitle("Edit");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xff,
                        0xff, 0xff)));
                // set item width
                deleteItem.setWidth(190);
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);

                // create "open" item

            }
        };

        lv.setMenuCreator(creator);
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        final MyDataBase myDataBase;
                        final StudentInfo studentInfo;
                        myDataBase = new MyDataBase(MainActivity.this);
                        List<StudentInfo> list = myDataBase.getAllData();
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                        View mView = layoutInflaterAndroid.inflate(R.layout.user_input_layout, null);
                        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                        alertDialogBuilderUserInput.setView(mView);


                        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                        final EditText userInputDialogEditText2 = (EditText) mView.findViewById(R.id.editText);
                        final EditText userInputDialogEditText3 = (EditText) mView.findViewById(R.id.editText2);
                        final EditText userInputDialogEditText4 = (EditText) mView.findViewById(R.id.editText1);

                        studentInfo = list.get(position);
                        userInputDialogEditText.setText(studentInfo.getPs());
                        userInputDialogEditText2.setText(studentInfo.getName());
                        userInputDialogEditText3.setText(studentInfo.getEmail());
                        userInputDialogEditText4.setText(studentInfo.getPh());


                        alertDialogBuilderUserInput
                                .setCancelable(false)
                                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {


                                        String s = userInputDialogEditText.getText().toString();
                                        String s1 = userInputDialogEditText2.getText().toString();
                                        String s2 = userInputDialogEditText3.getText().toString();
                                        String s3 = userInputDialogEditText4.getText().toString();

                                        studentInfo.setName(s);
                                        studentInfo.setName(s1);
                                        studentInfo.setEmail(s2);
                                        studentInfo.setPh(s3);

                                        boolean l = myDataBase.upDate(studentInfo);
                                        Toast.makeText(getApplicationContext(), "Row effected :D ", Toast.LENGTH_LONG).show();
                                        Log.i(TAG, "Database updated and effected ?" + l);
                                        check("Database updated and effected ?" + l);
                                        startActivity(new Intent(MainActivity.this, MainActivity.class));


                                    }
                                })

                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogBox, int id) {
                                                dialogBox.cancel();
                                                Date ca = Calendar.getInstance().getTime();

                                                Log.i(TAG, "User tapped cancel :( " + ca);
                                                check("User tapped cancel :( " + ca);
                                            }
                                        });

                        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                        alertDialogAndroid.show();
                        Date ca = Calendar.getInstance().getTime();

                        Log.i(TAG, "Displaying alert dialog " + ca);
                        check("Displaying alert dialog " + ca);

                        break;

                    case 1:


                        myDataBase = new MyDataBase(MainActivity.this);
                        List<StudentInfo> list1 = myDataBase.getAllData();
                        studentInfo = list1.get(position);
                        String ps = String.valueOf(studentInfo.getId());
                        try {

                            Date ca2 = Calendar.getInstance().getTime();

                            boolean state = myDataBase.deleteTitle(ps);
                            Toast.makeText(getApplicationContext(), "Deleted Successfully ;)", Toast.LENGTH_LONG).show();
                            Log.i(TAG, "Was record deleted ? " + state + "@ " + ca2);
                            check("Was record deleted ? " + state + "@ " + ca2);
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                        } catch (Exception ex) {
                            Date ca2 = Calendar.getInstance().getTime();

                            Log.i(TAG, "Was record deleted ? " + false + "@ " + ca2);
                            check("Exception " + ex + "@ " + ca2);
                        }

                        break;
                }

                return false;

            }
        });
        return false;   //enables & disables settings option
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

        }

        return super.onOptionsItemSelected(item);
    }


    public  void check(String log) {

        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/TLOGS/");

        File[] fList = folder.listFiles();

        for (File file : fList) {
            try{
            if (file.isFile()) {
                if (file.length() >= 1024) {
                    genRan(log);
                } else {
                    ch(file, log);
                }
            }
            }catch(Exception ex){
                Log.e(TAG,"Exception generated"+ex);
            }
        }
    }

    public void genRan(String log){
    String logFileName = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());
      logFileName = "Log" + logFileName;
        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/TLOGS/");
        File file = new File(folder, logFileName);
      ch(file,log);
    }




    public void ch(File file,String log) {


        {

            try {

                FileOutputStream stream = new FileOutputStream(file, true);

                {

                    try {
                        String newline = "\n";
                        stream.write(newline.getBytes());
                        stream.write(log.getBytes());
                        stream.close();

                    } finally {
                        stream.close();
                    }
                }


            } catch (Exception e) {
                Log.e(TAG, "Exception", e);

            }


        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // if (id == R.id.nav_camera) {
        //   Intent n = new Intent(MainActivity.this,EditActivity.class);

        // startActivity(n);
        // } //else if (id == R.id.nav_gallery) {
        //Intent n = new Intent(MainActivity.this,Main2Activity.class);

        //startActivity(n);

        if (id == R.id.nav_send) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app -Amar :D");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


