package com.fidelreactlibrary;

import android.app.Activity;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.WritableMap;
import com.fidel.sdk.Fidel;
import com.fidel.sdk.LinkResult;
import com.fidelreactlibrary.events.CallbackActivityEventListener;
import com.fidelreactlibrary.fakes.CallbackSpy;
import com.fidelreactlibrary.fakes.DataConverterStub;
import com.fidelreactlibrary.fakes.IntentMock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class CallbackActivityEventListenerTests {

    private CallbackActivityEventListener sut;
    private CallbackSpy callbackSpy;
    private DataConverterStub<Object, WritableMap> linkResultConverterStub;

    @Before
    public final void setUp() {
        linkResultConverterStub = new DataConverterStub<>();
        sut = new CallbackActivityEventListener(linkResultConverterStub);
        callbackSpy = new CallbackSpy();
        sut.callbackIsReady(callbackSpy);
    }

    @After
    public final void tearDown() {
        sut = null;
        callbackSpy = null;
    }

    @Test
    public void test_WhenReceivingLinkResult_SendItConvertedInCallback() {
        Activity activity = Robolectric.buildActivity(Activity.class).setup().get();
        IntentMock<LinkResult> intent = new IntentMock<>(activity, Activity.class);
        LinkResult testLinkResult = new LinkResult("TEST CARD ID");
        intent.putExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD, testLinkResult);
        linkResultConverterStub.convertedDataToReturn = new JavaOnlyMap();
        sut.onActivityResult(activity,0, 0, intent);

        assertEquals(Fidel.FIDEL_LINK_CARD_RESULT_CARD, intent.parcelableExtraNameAskedFor);
        assertTrue(callbackSpy.didInvoke);
        assertEquals(linkResultConverterStub.convertedDataToReturn, callbackSpy.receivedResultMap);
    }
}
