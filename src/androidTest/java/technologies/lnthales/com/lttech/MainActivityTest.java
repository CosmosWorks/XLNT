package technologies.lnthales.com.lttech;

/**
 * Created by chthp00104 on 10/10/18.
 */

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.filters.MediumTest;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AlertDialog;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.ListView;


import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.io.File;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)

public class MainActivityTest {
    public static final String foldername = "/storage/emulated/0/TLOGS/";
    private FloatingActionButton mFab;
    private MainActivity mTestActivity;
    private AlertDialog.Builder alertDialog;


    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("technologies.lnthales.com.lttech", appContext.getPackageName());

    }

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void ensureListViewIsPresent() throws Exception {

    }

    @Test
    @MediumTest

    public void TestingFolder() {

        mTestActivity = rule.getActivity();
        mFab = (FloatingActionButton) mTestActivity.findViewById(R.id.fab);

    }

    @Test
    @SmallTest

    public void testingcount() {
        SwipeMenuListView sw = (SwipeMenuListView) rule.getActivity().findViewById(R.id.listView);


        int expectedCount = 10;
        int actualCount = sw.getAdapter().getCount();
        assertNotEquals(expectedCount, actualCount);
    }


    @SmallTest
    public void testPreconditions() {

        assertNotNull("mTestActivity is not null", mTestActivity);
        assertNotNull("mFab is null", mFab);
    }

    @Test
    @SmallTest
    public void insertData() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        MyDataBase myDataBase = new MyDataBase(appContext);
        assertEquals(false, myDataBase.deleteTitle("900409"));
    }

    @Test
    @SmallTest
    public void Alertdialog() {

        Espresso.onView(withId(R.id.fab)).perform(click());
    }

    @Test
    @MediumTest
    public void FileCheck() {
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/TLOGS/");
        assertTrue(file.exists());
    }

   
}



