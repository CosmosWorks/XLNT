package technologies.lnthales.com.lttech;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.filters.MediumTest;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AlertDialog;
import android.view.View;

import android.widget.ListView;


import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by chthp00104 on 10/10/18.
 */

public class SearchActivityTest {
    private SearchActivity mTestActivity;
    private AlertDialog.Builder alertDialog;
    SearchActivity s;


    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("technologies.lnthales.com.lttech", appContext.getPackageName());

    }

    @Rule
    public ActivityTestRule<SearchActivity> rule = new ActivityTestRule<SearchActivity>(SearchActivity.class);


    @Test
    @SmallTest

    public void notnull() {
        mTestActivity = rule.getActivity();

        assertNotNull("mTestActivity is  null", mTestActivity);

    }

    @Test
    @MediumTest
    public void ListView() {
        SearchActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.listView3);
        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(ListView.class));

    }
}