package technologies.lnthales.com.lttech;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import  android.database.sqlite.SQLiteDatabase;
import  android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="EMPLOYEE";//db name
    public static final String TABLE_NAME="empp"; //table name
    //table fields
    public static final String NAME="name";
    public static final String PS="ps";
    public static final String Ph="phone";
    public static final String TAG="TIMEWRAP";

    public static final String EMAIL="email";
    public static final String ID="id";
   // public static final String PASSKEY = "@$AMAR#$";

    public static final int DB_version=3;//3 Db version
    private static MyDataBase instance;
    Context c;


    static public synchronized MyDataBase getInstance(Context context){
        if(instance==null)
        {
            instance = new MyDataBase(context);
        }
        return instance;
    }


    public MyDataBase(Context context) {
        super(context, DATABASE_NAME, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("create table empp"+"(id integer primary key,ps text,name text,email text,phone text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    //insert data


    public boolean insertData(StudentInfo studentInfo) {
        MainActivity n = new MainActivity();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PS, studentInfo.getPs());
        contentValues.put(NAME, studentInfo.getName());
        contentValues.put(EMAIL, studentInfo.getEmail());
        contentValues.put(Ph, studentInfo.getPh());
        long l = db.insert(TABLE_NAME, null, contentValues);
        long m = l-1;
        //boolean res = n.isWriteStoragePermissionGranted();

        if (l != 0) {
           Log.v(TAG,"Value of l during insertion is "+m);

             //   n.check("Value of l during insertion is " + m);

            return true;

        }
        return false;
    }


    //get the data
    public List<StudentInfo> getAllData(){
        ArrayList<StudentInfo> list=new ArrayList<>();
        SQLiteDatabase db= getReadableDatabase();
        Cursor cursor= db.rawQuery("select * from empp",null);
        if (cursor.moveToFirst()){
            do {
                StudentInfo studentInfo=new StudentInfo();//create the empty constructor
                studentInfo.setId(Integer.parseInt(cursor.getString(0)));  //table id String convert to int
                studentInfo.setPs(cursor.getString(1));
                studentInfo.setName(cursor.getString(2));
                studentInfo.setEmail(cursor.getString(3));
                studentInfo.setPh(cursor.getString(4));

                list.add(studentInfo);
                Log.v(TAG,"Generated list"+list);

            }while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public boolean upDate(StudentInfo studentInfo){
        SQLiteDatabase database= getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PS,studentInfo.getPs());
        contentValues.put(NAME,studentInfo.getName());
        contentValues.put(EMAIL,studentInfo.getEmail());
        contentValues.put(Ph,studentInfo.getPh());

        double l = database.update(TABLE_NAME,contentValues,ID+"=?",new String[]{String.valueOf(studentInfo.getId())});

        if(l==1.0)
        {
            return true;
        }
        return false;
    }


    public boolean deleteTitle(String name)

    {
        SQLiteDatabase db= getWritableDatabase();


        return db.delete(TABLE_NAME, ID + "=" + name, null) > 0;
    }

    public int deleteAll(){
        SQLiteDatabase database= getWritableDatabase();
       return database.delete(TABLE_NAME,null,null);
    }

    public Cursor retrieve(String searchTerm)
    {
        SQLiteDatabase database= getWritableDatabase();

       String[] columns={ID,NAME};
        Cursor c=null;

        if(searchTerm != null && searchTerm.length()>0)
        {
          //SELECT *FROM posts WHERE posts MATCH 'text' ORDER BY rank;
           String sql="SELECT * FROM "+TABLE_NAME+" WHERE "+NAME+" LIKE '"+searchTerm+"'";
            c=database.rawQuery(sql,null);
            return c;

        }

        c=database.query(TABLE_NAME,columns,null,null,null,null,null);
        return c;
    }
}