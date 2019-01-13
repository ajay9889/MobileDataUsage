package com.mobiledata.sg.network.AdapterClass;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import com.mobiledata.sg.network.MainActivity;
import com.mobiledata.sg.network.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.CoreMatchers.allOf;

public class CardListAdapterTest {
    @Rule
    public ActivityTestRule<MainActivity> mRuleActivity = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = mRuleActivity.getActivity();
    }
    @Test
    public void listViewTestd() throws Exception{
        mActivity.requestToNetwork();
        Thread.sleep(1000);
        onView(withIndex(withId(R.id.image_item_clicks), 2)).perform(click());
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

    @After
    public void tearDown() throws Exception {
        mActivity=null;
    }
}