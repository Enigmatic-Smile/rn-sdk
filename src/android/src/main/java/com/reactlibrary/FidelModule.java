
package com.reactlibrary;

import android.app.Activity;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.fidel.sdk.Fidel;

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
  public void present() {
    Fidel.programId = "your program id";
    Fidel.apiKey = "your api key";
    final Activity activity = getCurrentActivity();
    Fidel.present(activity);
  }
}