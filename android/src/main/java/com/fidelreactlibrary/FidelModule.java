
package com.fidelreactlibrary;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.fidelapi.Fidel;
import com.fidelapi.analytics.Analytics;
import com.fidelapi.analytics.domain.SdkDetails;
import com.fidelapi.entities.CardVerificationConfiguration;
import com.fidelapi.entities.abstraction.OnCardVerificationChoiceSelectedObserver;
import com.fidelapi.entities.abstraction.OnCardVerificationStartedObserver;
import com.fidelapi.entities.abstraction.OnResultObserver;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.adapters.abstraction.VerificationConfigurationAdapter;
import com.fidelreactlibrary.events.BridgeLibraryEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class FidelModule extends ReactContextBaseJavaModule {

  private final DataProcessor<ReadableMap> setupProcessor;
  private final OnCardVerificationChoiceSelectedObserver onCardVerificationChoiceSelectedObserver;
  private final List<ConstantsProvider> constantsProviderList;
  private final OnResultObserver onResultObserver;
  private final OnCardVerificationStartedObserver onCardVerificationStartedObserver;
  private final VerificationConfigurationAdapter verificationAdapter;

  public FidelModule(ReactApplicationContext reactContext,
      DataProcessor<ReadableMap> setupProcessor,
      OnResultObserver onResultObserver,
      OnCardVerificationStartedObserver onCardVerificationStartedObserver,
      OnCardVerificationChoiceSelectedObserver onCardVerificationChoiceSelectedObserver,
      List<ConstantsProvider> constantsProviderList,
      VerificationConfigurationAdapter verificationAdapter) {
    super(reactContext);
    this.setupProcessor = setupProcessor;
    this.onCardVerificationChoiceSelectedObserver = onCardVerificationChoiceSelectedObserver;
    this.constantsProviderList = constantsProviderList;
    this.onResultObserver = onResultObserver;
    this.onCardVerificationStartedObserver = onCardVerificationStartedObserver;
    this.verificationAdapter = verificationAdapter;
  }

  @NonNull
  @Override
  public String getName() {
    return "NativeFidelBridge";
  }

  @ReactMethod
  public void addListener(String eventName) {
    if (eventName.equals(BridgeLibraryEvent.RESULT_AVAILABLE.getEventName())) {
      Fidel.onResult = onResultObserver;
    } else if (eventName.equals(BridgeLibraryEvent.CARD_VERIFICATION_STARTED.getEventName())) {
      Fidel.onCardVerificationStarted = onCardVerificationStartedObserver;
    } else if (eventName.equals(BridgeLibraryEvent.CARD_VERIFICATION_CHOICE.getEventName())) {
      Fidel.onCardVerificationChoiceSelected = onCardVerificationChoiceSelectedObserver;
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

  @ReactMethod
  public void verifyCard(ReadableMap data) {
    final Activity activity = getCurrentActivity();
    if (activity != null) {
      CardVerificationConfiguration cardVerificationConfig = verificationAdapter.adapt(data);
      Fidel.verifyCard(activity, cardVerificationConfig);
    }
  }

  @ReactMethod
  public void identifyMetricsDataSource(String name, String version) {
    SdkDetails reactNativeSdkDetails = new SdkDetails(name, version);
    Analytics analytics = new Analytics();
    analytics.identifyMultiplatformDataSource(reactNativeSdkDetails);
  }
  
  @Nullable
  @Override
  public Map<String, Object> getConstants() {
    Map<String, Object> constants = new HashMap<>();
    for (ConstantsProvider provider : constantsProviderList) {
      constants.putAll(provider.getConstants());
    }
    return constants;
  }
}
