
package com.fidelreactlibrary;

import android.app.Activity;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class FidelModule extends ReactContextBaseJavaModule {

  private final BaseActivityEventListener mActivityEventListener;
  private final DataProcessor<ReadableMap> setupProcessor;
  private final DataProcessor<ReadableMap> optionsProcessor;
  private final List<ConstantsProvider> constantsProviderList;

  public FidelModule(ReactApplicationContext reactContext,
                     DataProcessor<ReadableMap> setupProcessor,
                     DataProcessor<ReadableMap> optionsProcessor,
                     List<ConstantsProvider> constantsProviderList) {
    super(reactContext);
    this.setupProcessor = setupProcessor;
    this.optionsProcessor = optionsProcessor;
    mActivityEventListener = new FidelActivityEventListener();
    reactContext.addActivityEventListener(mActivityEventListener);
    this.constantsProviderList = constantsProviderList;
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
  public void setup(ReadableMap map) {
    setupProcessor.process(map);
  }

  @ReactMethod
  public void setOptions(ReadableMap map) {
    optionsProcessor.process(map);
  }

  @Nullable
  @Override
  public Map<String, Object> getConstants() {
    return constantsProviderList.get(0).getConstants();
  }
}