package com.fidelreactlibrary.fakes;

import android.content.Context;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.JavaScriptModuleRegistry;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class ReactContextMock extends ReactApplicationContext {

    public Class<?> classAskedFor;
    public String eventEmitterInvokedMethodName;
    public String receivedErrorName;
    public WritableMap receivedErrorData;

    public ReactContextMock(Context context) {
        super(context);
    }

    @Override
    public <T extends JavaScriptModule> T getJSModule(Class<T> jsInterface) {
        classAskedFor = jsInterface;
        JavaScriptModule interfaceProxy = (JavaScriptModule) Proxy.newProxyInstance(
                jsInterface.getClassLoader(),
                new Class[]{jsInterface},
                new JSModuleSpyInvocationHandler());
        T moduleToReturn = (T)interfaceProxy;
        return moduleToReturn;
    }

    private class JSModuleSpyInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object o, Method method, Object[] objects) {
            receivedErrorName = (String) objects[0];
            receivedErrorData = (WritableMap) objects[1];
            eventEmitterInvokedMethodName = method.getName();
            return null;
        }
    }
}
