package com.mobiledata.sg.network.CommonUtils;

import android.support.test.rule.ActivityTestRule;
import com.mobiledata.sg.network.MainActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilityTest {

    @Rule
    public ActivityTestRule<MainActivity> mRuleActivity = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = mRuleActivity.getActivity();
    }

    @Test
    public void checkNework(){
        boolean isTrue = Utility.isNetworkAvailable(mActivity);
        assertTrue(isTrue);
    }
    @After
    public void tearDown() throws Exception {
        mActivity =null;
    }
}