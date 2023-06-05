
package com.fidelreactlibrary;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelapi.entities.CardVerificationConfiguration;
import com.fidelapi.entities.abstraction.OnResultObserver;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.adapters.FidelSetupKeys;
import com.fidelreactlibrary.adapters.abstraction.VerificationSetupAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class FidelModule extends ReactContextBaseJavaModule {

  private final DataProcessor<ReadableMap> setupProcessor;
  private final List<ConstantsProvider> constantsProviderList;
  private final OnResultObserver onResultObserver;
  private final VerificationSetupAdapter verificationSetupAdapter;
  private final ReactApplicationContext reactContext;

  public FidelModule(ReactApplicationContext reactContext,
                     DataProcessor<ReadableMap> setupProcessor,
                     OnResultObserver onResultObserver,
                     List<ConstantsProvider> constantsProviderList,
                     VerificationSetupAdapter verificationSetupAdapter) {
    super(reactContext);
    this.reactContext = reactContext;
    this.setupProcessor = setupProcessor;
    this.constantsProviderList = constantsProviderList;
    this.onResultObserver = onResultObserver;
    this.verificationSetupAdapter = verificationSetupAdapter;
  }

  @NonNull
  @Override
  public String getName() {
    return "NativeFidelBridge";
  }

  @ReactMethod
  public void addListener(String eventName) {
    Fidel.onResult = onResultObserver;
  }

  @ReactMethod
  public void removeListeners(Integer count) {
    Fidel.onResult = null;
  }

  @ReactMethod
  public void start() {
    final Activity activity = getCurrentActivity();
    if (activity != null) {
        Fidel.start(activity);
    }
  }

  @ReactMethod
  public void setup(ReadableMap map) {
    setupProcessor.process(map);
    final Activity activity = getCurrentActivity();
    if (activity != null) {
      Fidel.onMainActivityCreate(activity);
    }
  }

  @ReactMethod
  public void verifyCard(ReadableMap data) {
    final Activity activity = getCurrentActivity();
    if (activity != null) {
      CardVerificationConfiguration cardConfig = verificationSetupAdapter.map(data);
      Fidel.verifyCard(activity, cardConfig);
    }
  }

  @Nullable
  @Override
  public Map<String, Object> getConstants() {
    Map<String, Object> constants = new HashMap<>();
    for (ConstantsProvider provider: constantsProviderList) {
      constants.putAll(provider.getConstants());
    }
    return constants;
  }
}
