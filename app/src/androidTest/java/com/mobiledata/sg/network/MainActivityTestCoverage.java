package com.mobiledata.sg.network;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import com.mobiledata.sg.network.CommonUtils.Utility;
import com.mobiledata.sg.network.ModelClass.MobileData;
import com.mobiledata.sg.network.NetworkAPICalls.EndpointUrls;
import com.mobiledata.sg.network.NetworkAPICalls.NetworkHandler;
import com.mobiledata.sg.network.NetworkAPICalls.ResponseInterface;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.mobiledata.sg.network.NetworkAPICalls.EndpointUrls.HOST;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class MainActivityTestCoverage {

    @Rule
    public ActivityTestRule<MainActivity> mRuleActivity = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mActivity;
    @Before
    public void setUp() throws Exception {
        mActivity = mRuleActivity.getActivity();
    }

    @Test
    public void testLaunch(){
        assertNotNull( mActivity.findViewById(R.id.chart));
    }
    @Test
    public void checkNework(){
        boolean isTrue = Utility.isNetworkAvailable(mActivity);
        assertTrue(isTrue);
    }
    @Test
    public void insertAll_Launch() {
        MobileData[] lMobileData=new MobileData[20];
        for(int i = 0 ; i < 20 ; i++){
            MobileData mMobileData = new MobileData();
            mMobileData.set_id("1"+i);
            mMobileData.setVolume_of_mobile_data(0.0f);
            mMobileData.setQuarter("Q1");
            mMobileData.setYear(2004);
            lMobileData[i]=mMobileData;
        }
        mActivity.insertAll(mActivity ,lMobileData );
    }
    /**
     *
     * It is the complete flow of showing the navigations
     * */
    @Test
    public void completDataTes() throws Exception{
        mActivity.requestToNetwork();
        Thread.sleep(1000);
        onView(withId(R.id.graph_menu)).perform(click());
        onView(withId(R.id.graph_menu)).perform(click());
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


    @Test
    public void requestToNetwork_Launch() {
        new NetworkHandler().onRequest(mResponseInterface ,200, EndpointUrls.FetchNetworkMobileData);
        String FakeMobileData =HOST+"/action/datastore_search_2";
        new NetworkHandler().onRequest(mResponseInterface ,404, FakeMobileData);
    }
    ResponseInterface mResponseInterface = new ResponseInterface() {
        @Override
        public void onResponse(String serverResponse, int requestCode) {
            assertNotNull(serverResponse);
        }
    };

    @Test
    public void ViewGraph(){
        onView(withId(R.id.graph_menu)).perform(click());
    }
    @Test
    public void fetchAllLocalDatabase() {

        mActivity.fetchAllLocalCachedData(mActivity  );
    }



    @After
    public void tearDown() throws Exception {
        mActivity=null;
    }
}