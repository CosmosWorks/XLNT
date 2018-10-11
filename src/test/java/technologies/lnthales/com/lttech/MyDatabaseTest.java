package technologies.lnthales.com.lttech;


import android.content.Context;

import org.junit.Test;
import static org.junit.Assert.*;



public class MyDatabaseTest {


    Context context;


    @Test
    public void test() {
        MyDataBase myDataBase = new MyDataBase(context);
        int stage1 = 1;
        //int state = myDataBase.getAllData();
       // assertEquals(stage1,state);

    }

}
