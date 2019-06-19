
package com.fidelreactlibrary;

import android.app.Activity;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.events.CallbackInput;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class FidelModule extends ReactContextBaseJavaModule {

  private final CallbackInput callbackInput;
  private final DataProcessor<ReadableMap> setupProcessor;
  private final DataProcessor<ReadableMap> optionsProcessor;
  private final List<ConstantsProvider> constantsProviderList;

  public FidelModule(ReactApplicationContext reactContext,
                     DataProcessor<ReadableMap> setupProcessor,
                     DataProcessor<ReadableMap> optionsProcessor,
                     List<ConstantsProvider> constantsProviderList,
                     CallbackInput callbackInput) {
    super(reactContext);
    this.setupProcessor = setupProcessor;
    this.optionsProcessor = optionsProcessor;
    this.callbackInput = callbackInput;
    this.constantsProviderList = constantsProviderList;
  }

  @Override
  public String getName() {
    return "NativeFidelBridge";
  }

  @ReactMethod
  public void openForm(Callback callback) {
    final Activity activity = getCurrentActivity();
    if (activity != null) {
        Fidel.present(activity);
    }
    callbackInput.callbackIsReady(callback);
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