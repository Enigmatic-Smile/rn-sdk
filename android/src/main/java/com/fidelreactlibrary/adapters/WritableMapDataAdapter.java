package com.fidelreactlibrary.adapters;

import com.facebook.react.bridge.WritableMap;
import com.fidelreactlibrary.adapters.abstraction.DataAdapter;
import com.fidelreactlibrary.adapters.abstraction.ObjectFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Iterator;

public final class WritableMapDataAdapter implements DataAdapter<Object, WritableMap> {

    private final ObjectFactory<WritableMap> writableMapFactory;
    public WritableMapDataAdapter(ObjectFactory<WritableMap> writableMapFactory) {
        this.writableMapFactory = writableMapFactory;
    }

    @Override
    public WritableMap getAdaptedObjectFor(Object data) {
        if (data == null) {
            return null;
        }
        WritableMap map = writableMapFactory.create();
        try {
            for (Field field: data.getClass().getDeclaredFields()) {
                if (field.getType() == String.class) {
                    map.putString(field.getName(), (String)field.get(data));
                } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                    map.putBoolean(field.getName(), (boolean)field.get(data));
                } else if (field.getType() == int.class) {
                    map.putInt(field.getName(), (int)field.get(data));
                } else if (field.getType() == JSONObject.class) {
                    WritableMap mapToPut = this.getMapFor((JSONObject)field.get(data));
                    map.putMap(field.getName(), mapToPut);
                }
            }
            return map;
        } catch (Exception e) {
            return map;
        }
    }

    private WritableMap getMapFor(JSONObject json) {
        Iterator<String> jsonKeyIterator = json.keys();
        WritableMap map = writableMapFactory.create();
        while (jsonKeyIterator.hasNext()) {
            String key = jsonKeyIterator.next();
            try {
                Object value = json.get(key);
                Class valueClass = value.getClass();
                if (valueClass == String.class) {
                    map.putString(key, (String)value);
                }
                else if (valueClass == boolean.class || valueClass == Boolean.class) {
                    map.putBoolean(key, (boolean)value);
                }
                else if (valueClass == int.class || valueClass == Integer.class) {
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
