
package com.fidelreactlibrary;

import android.app.Activity;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

public class FidelModule extends ReactContextBaseJavaModule {

  private final BaseActivityEventListener mActivityEventListener;
  private final DataProcessor<ReadableMap> optionsProcessor;

  public FidelModule(ReactApplicationContext reactContext,
                     DataProcessor<ReadableMap> optionsProcessor) {
    super(reactContext);
    this.optionsProcessor = optionsProcessor;
    mActivityEventListener = new FidelActivityEventListener();
    reactContext.addActivityEventListener(mActivityEventListener);
  }

  @Override
  public String getName() {
    return "NativeFidelBridge";
  }

  @ReactMethod
  public void openForm(Callback errorCallback) {
    Fidel.programId = "your program id";
    Fidel.apiKey = "your api key";
    final Activity activity = getCurrentActivity();
    Fidel.present(activity);
  }

  @ReactMethod
  public void setOptions(ReadableMap map) {
    optionsProcessor.process(map);
  }
}