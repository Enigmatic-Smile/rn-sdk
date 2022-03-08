package com.fidelreactlibrary.adapters;

import com.facebook.react.bridge.WritableMap;
import com.fidel.sdk.LinkResultErrorCode;
import com.fidelreactlibrary.adapters.abstraction.DataConverter;
import com.fidelreactlibrary.adapters.abstraction.ObjectFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Iterator;

public final class WritableMapDataConverter implements DataConverter<Object, WritableMap> {

    private final ObjectFactory<WritableMap> writableMapFactory;
    public WritableMapDataConverter(ObjectFactory<WritableMap> writableMapFactory) {
        this.writableMapFactory = writableMapFactory;
    }
    @Override
    public WritableMap getConvertedDataFor(Object data) {
        if (data == null) {
            return null;
        }
        WritableMap map = writableMapFactory.create();

        for (Field field: data.getClass().getDeclaredFields()) {
            if (field.getType() == String.class) {
                try {
                    map.putString(field.getName(), (String) field.get(data));
                } catch (Exception e) {
                    map.putNull(field.getName());
                }
            } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                try {
                    map.putBoolean(field.getName(), field.getBoolean(data));
                } catch (Exception e) {
                    map.putBoolean(field.getName(), false);
                }
            } else if (field.getType() == int.class) {
                try {
                    map.putInt(field.getName(), field.getInt(data));
                } catch (Exception e) {
                    map.putInt(field.getName(), -1);
                }
            } else if (field.getType() == LinkResultErrorCode.class) {
                String displayFieldName = field.getName().equals("errorCode") ? "code" : field.getName();
                try {
                    LinkResultErrorCode errorCode = (LinkResultErrorCode)field.get(data);
                    map.putString(displayFieldName, errorCode.toString().toLowerCase());
                } catch (Exception e) {
                    map.putNull(displayFieldName);
                }
            } else if (field.getType() == JSONObject.class) {
                try {
                    WritableMap mapToPut = this.getMapFor((JSONObject)field.get(data));
                    map.putMap(field.getName(), mapToPut);
                } catch (Exception e) {
                    map.putNull(field.getName());
                }
            }
        }
        return map;
    }

    private WritableMap getMapFor(JSONObject json) {
        Iterator<String> jsonKeyIterator = json.keys();
        WritableMap map = writableMapFactory.create();
        while (jsonKeyIterator.hasNext()) {
            String key = jsonKeyIterator.next();
            try {
                Object value = json.get(key);
                Class<?> valueClass = value.getClass();
                if (valueClass == String.class) {
                    map.putString(key, (String)value);
                }
                else if (valueClass == Boolean.class) {
                    map.putBoolean(key, (boolean)value);
                }
                else if (valueClass == Integer.class) {
                    map.putInt(key, (int)value);
                }
                else if (valueClass == JSONObject.class) {
                    WritableMap mapToPut = this.getMapFor((JSONObject)value);
                    map.putMap(key, mapToPut);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
