
package com.fidelreactlibrary;

import android.app.Activity;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.WritableMapDataConverter;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.adapters.abstraction.ObjectFactory;
import com.fidelreactlibrary.events.CallbackActivityEventListener;
import com.fidelreactlibrary.events.CallbackInput;
import com.fidel.sdk.data.abstraction.FidelCardLinkingObserver;
import com.fidelreactlibrary.events.ErrorEventEmitter;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class FidelModule extends ReactContextBaseJavaModule {

  private final CallbackInput callbackInput;
  private final DataProcessor<ReadableMap> setupProcessor;
  private final DataProcessor<ReadableMap> optionsProcessor;
  private final List<ConstantsProvider> constantsProviderList;
  private final ReactApplicationContext reactContext;

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
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "NativeFidelBridge";
  }

  @ReactMethod
  public void openForm(Callback callback) {
    final Activity activity = getCurrentActivity();
    final FidelCardLinkingObserver cardLinkingObserver = getCardLinkingObserver();
    if (activity != null) {
        Fidel.setCardLinkingObserver(cardLinkingObserver);
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

  private CallbackActivityEventListener getCardLinkingObserver() {
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