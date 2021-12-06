
package com.fidelreactlibrary;

import android.app.Activity;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.fidelapi.Fidel;
import com.fidelapi.entities.abstraction.OnResultObserver;
import com.fidelreactlibrary.adapters.WritableMapDataConverter;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.adapters.abstraction.ObjectFactory;
import com.fidelreactlibrary.events.CallbackActivityEventListener;
import com.fidelreactlibrary.events.CallbackInput;
import com.fidelreactlibrary.events.ErrorEventEmitter;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class FidelModule extends ReactContextBaseJavaModule {

  private final CallbackInput callbackInput;
  private final DataProcessor<ReadableMap> setupProcessor;
  private final List<ConstantsProvider> constantsProviderList;
  private final ReactApplicationContext reactContext;

  public FidelModule(ReactApplicationContext reactContext,
                     DataProcessor<ReadableMap> setupProcessor,
                     List<ConstantsProvider> constantsProviderList,
                     CallbackInput callbackInput) {
    super(reactContext);
    this.setupProcessor = setupProcessor;
    this.callbackInput = callbackInput;
    this.constantsProviderList = constantsProviderList;
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "NativeFidelBridge";
  }

  @ReactMethod
  public void addListener(String eventName) {
    // Keep: Required for RN built in Event Emitter Calls.
  }

  @ReactMethod
  public void removeListeners(Integer count) {
    // Keep: Required for RN built in Event Emitter Calls.
  }

  @ReactMethod
  public void onResult(Callback callback) {
    Fidel.onResult = getCardLinkingObserver();
    callbackInput.callbackIsReady(callback);
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
  }

  @Nullable
  @Override
  public Map<String, Object> getConstants() {
    return constantsProviderList.get(0).getConstants();
  }

  private OnResultObserver getCardLinkingObserver() {
    WritableMapDataConverter linkResultConverter =
            new WritableMapDataConverter(new ObjectFactory<WritableMap>() {
              @Override
              public WritableMap create() {
                return new WritableNativeMap();
              }
            });
    ErrorEventEmitter errorEventEmitter =
            new ErrorEventEmitter(reactContext);
    return new CallbackActivityEventListener(linkResultConverter, errorEventEmitter);
  }
}