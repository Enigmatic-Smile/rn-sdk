
package com.fidelreactlibrary;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelapi.entities.abstraction.OnCardVerificationStartedObserver;
import com.fidelapi.entities.abstraction.OnResultObserver;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.events.BridgeLibraryEventTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class FidelModule extends ReactContextBaseJavaModule {

  private final DataProcessor<ReadableMap> setupProcessor;
  private final List<ConstantsProvider> constantsProviderList;
  private final OnResultObserver onResultObserver;
  private final OnCardVerificationStartedObserver onCardVerificationStartedObserver;

  public FidelModule(ReactApplicationContext reactContext,
                     DataProcessor<ReadableMap> setupProcessor,
                     OnResultObserver onResultObserver,
                     OnCardVerificationStartedObserver onCardVerificationStartedObserver,
                     List<ConstantsProvider> constantsProviderList) {
    super(reactContext);
    this.setupProcessor = setupProcessor;
    this.constantsProviderList = constantsProviderList;
    this.onResultObserver = onResultObserver;
    this.onCardVerificationStartedObserver = onCardVerificationStartedObserver;
  }

  @NonNull
  @Override
  public String getName() {
    return "NativeFidelBridge";
  }

  @ReactMethod
  public void addListener(String eventName) {
    if (eventName.equals(BridgeLibraryEventTypes.RESULT_AVAILABLE.getEventName())) {
      Fidel.onResult = onResultObserver;
    } else if (eventName.equals(BridgeLibraryEventTypes.CARD_VERIFICATION_STARTED.getEventName())) {
      Fidel.onCardVerificationStarted = onCardVerificationStartedObserver;
    }
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