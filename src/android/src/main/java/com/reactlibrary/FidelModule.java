
package com.reactlibrary;

import android.app.Activity;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.fidel.sdk.Fidel;

public class FidelModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public FidelModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
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