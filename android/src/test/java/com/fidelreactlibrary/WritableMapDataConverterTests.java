package com.fidelreactlibrary;

import android.os.Parcelable;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.fidel.sdk.LinkResult;
import com.fidel.sdk.LinkResultError;
import com.fidel.sdk.LinkResultErrorCode;
import com.fidelreactlibrary.adapters.WritableMapDataConverter;
import com.fidelreactlibrary.adapters.abstraction.ObjectFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.Field;

import static com.fidelreactlibrary.helpers.AssertHelpers.assertMapEqualsWithJSONObject;
import static org.junit.Assert.*;

//Custom test runner is necessary for being able to use JSONObject
@RunWith(RobolectricTestRunner.class)
public class WritableMapDataConverterTests {

    private static final String TEST_CARD_ID = "Test Card ID";
    private static final String TEST_ERROR_MESSAGE = "Test Error Message";

    private WritableMapDataConverter sut;

    @Before
    public final void setUp() {
        sut = new WritableMapDataConverter(new ObjectFactory<WritableMap>() {
            @Override
            public WritableMap create() {
                return new JavaOnlyMap();
            }
        });
    }

    @After
    public final void tearDown() {
        sut = null;
    }

    @Test
    public void test_WhenAskedToConvertNullObject_ReturnNull() {
        assertNull(sut.getConvertedDataFor(null));
    }

    @Test
    public void test_WhenExpectedNonNullStringFieldContainsNullValueInLinkResult_ReturnMapWithNullValueForIt() {
        LinkResult linkResult = new LinkResult(TEST_CARD_ID);
        setFieldsFor(linkResult);
        linkResult.accountId = null;
        WritableMap receivedMap = sut.getConvertedDataFor(linkResult);
        assertNull(receivedMap.getString("accountId"));
    }

    @Test
    public void test_WhenExpectedNonNullBooleanFieldContainsNullValueInLinkResult_ReturnMapWithFalseValueForIt() {
        LinkResult linkResult = new LinkResult(TEST_CARD_ID);
        setFieldsFor(linkResult);
        linkResult.live = null;
        WritableMap receivedMap = sut.getConvertedDataFor(linkResult);
        assertFalse(receivedMap.getBoolean("live"));
    }

    @Test
    public void test_WhenExpectedLinkResultWithNullMetaData_ReturnMapWithNullMetaData() {
        LinkResult linkResult = new LinkResult(TEST_CARD_ID);
        setFieldsFor(linkResult);
        linkResult.metaData = null;
        WritableMap receivedMap = sut.getConvertedDataFor(linkResult);
        assertNull(receivedMap.getMap("metaData"));
    }

    @Test
    public void test_WhenConvertingLinkResultWithError_AndErrorCodeIsUnknown_SetNullErrorCodeField() {
        LinkResult linkResult = new LinkResult(null, TEST_ERROR_MESSAGE, "2021-05-19T12:37:55.278Z");
        Object objectToConvert = linkResult.getError();

        WritableMap receivedMap = sut.getConvertedDataFor(objectToConvert);
        assertNotNull(receivedMap);
        assertNull(receivedMap.getString("code"));
    }

    @Test
    public void test_WhenConvertingValidLinkResult_IncludeAllObjectFields() throws IllegalAccessException {
        LinkResult linkResult = new LinkResult(TEST_CARD_ID);
        setFieldsFor(linkResult);

        WritableMap receivedMap = sut.getConvertedDataFor(linkResult);

        for (Field field: linkResult.getClass().getDeclaredFields()) {
            if (field.getType() == String.class) {
                String receivedString = receivedMap.getString(field.getName());
                assertEquals(receivedString, field.get(linkResult));
            }
            else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                boolean receivedVal = receivedMap.getBoolean(field.getName());
                assertEquals(receivedVal, field.get(linkResult));
            }
            else if (field.getType() == int.class) {
                int receivedVal = receivedMap.getInt(field.getName());
                assertEquals(receivedVal, field.get(linkResult));
            }
            else if (field.getType() == JSONObject.class) {
                ReadableMap mapField = receivedMap.getMap(field.getName());
                JSONObject jsonField = (JSONObject)field.get(linkResult);
                assertMapEqualsWithJSONObject(mapField.toHashMap(), jsonField);
            }
            else if (field.getType() != Parcelable.Creator.class && field.getType() != LinkResultError.class && !field.getName().equals("$jacocoData")) {
                fail("Some of the link result properties are not converted" + field + " field name: " + field.getName());
            }
        }
    }

    @Test
    public void test_WhenConvertingLinkResultWithError_IncludeAllErrorFields() throws IllegalAccessException {
        LinkResultErrorCode errorCode = LinkResultErrorCode.USER_CANCELED;
        LinkResult linkResult = new LinkResult(errorCode, TEST_ERROR_MESSAGE, "2021-05-19T12:37:55.278Z");
        Object objectToConvert = linkResult.getError();

        WritableMap receivedMap = sut.getConvertedDataFor(objectToConvert);

        for (Field field: objectToConvert.getClass().getDeclaredFields()) {
            if (field.getType() == String.class) {
                String receivedString = receivedMap.getString(field.getName());
                assertEquals(receivedString, field.get(objectToConvert));
            }
            else if (field.getType() == LinkResultErrorCode.class) {
                String displayFieldName = field.getName().equals("errorCode") ? "code" : field.getName();
                String receivedErrorCodeString = receivedMap.getString(displayFieldName);
                LinkResultErrorCode expectedErrorCode = (LinkResultErrorCode) field.get(objectToConvert);
                String expectedErrorCodeString = expectedErrorCode.toString().toLowerCase();
                assertEquals(expectedErrorCodeString, receivedErrorCodeString);
            }
            else if (!field.getName().equals("$jacocoData")) {
                fail("Some of the link result properties are not converted" + field + " field name: " + field.getName());
            }
        }
    }

    //Helpers
    private void setFieldsFor(Object object) {
        for (Field field: object.getClass().getDeclaredFields()) {
            try {
                if (field.getType() == String.class) {
                    field.set(object, field.getName()+"Value");
                }
                else if (field.getType() == boolean.class) {
                    field.setBoolean(object, getRandomBoolean());
                }
                else if (field.getType() == int.class) {
                    field.setInt(object, getRandomInt());
                }
                else if (field.getType() == JSONObject.class) {
                    JSONObject json = new JSONObject();
                    json.put("keyString", "StringValue");
                    json.put("keyInt", getRandomInt());
                    json.put("keyBool", getRandomBoolean());
                    JSONObject internalJSON = new JSONObject();
                    internalJSON.put("internalJSONKey1", getRandomInt());
                    internalJSON.put("internalJSONKey2", new JSONObject());
                    json.put("keyJSON", internalJSON);
                    field.set(object, json);
                }
            } catch (IllegalAccessException | JSONException e) {
                fail();
            }
        }
    }

    private static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }
    private static int getRandomInt() {
        return (int)(Math.random() * 1000);
    }
}
