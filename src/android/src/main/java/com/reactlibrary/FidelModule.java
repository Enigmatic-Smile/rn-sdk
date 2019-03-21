
package com.reactlibrary;

import android.app.Activity;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.fidel.sdk.Fidel;

import android.net.Uri;

import java.io.IOException;

public class FidelModule extends ReactContextBaseJavaModule {

  private final BaseActivityEventListener mActivityEventListener;

  public FidelModule(ReactApplicationContext reactContext) {
    super(reactContext);
    mActivityEventListener = new FidelActivityEventListener();
    reactContext.addActivityEventListener(mActivityEventListener);
  }

  @Override
  public String getName() {
    return "Fidel";
  }

  @ReactMethod
  public void openForm() {
    Fidel.programId = "your program id";
    Fidel.apiKey = "your api key";
    final Activity activity = getCurrentActivity();
    Fidel.present(activity);
  }
  @ReactMethod
  public void setOptions(ReadableMap map) {
    ReadableMap bannerImageMap = map.getMap("bannerImage");
    String imageURIString = bannerImageMap.getString("uri");
    Uri imageURI = Uri.parse(imageURIString);
    Bitmap bitmap = null;
    try {
      bitmap = MediaStore.Images.Media.getBitmap(getCurrentActivity().getContentResolver(), imageURI);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Fidel.bannerImage = bitmap;
  }
}