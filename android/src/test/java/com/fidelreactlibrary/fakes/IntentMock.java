package com.fidelreactlibrary.fakes;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

public final class IntentMock<T extends Parcelable> extends Intent {

    public IntentMock(Context packageContext, Class<?> cls) {
        super(packageContext, cls);
    }

    public String parcelableExtraNameAskedFor;
    public T parcelableExtraToReturn;
    @Override
    public T getParcelableExtra(String name) {
        parcelableExtraNameAskedFor = name;
        return parcelableExtraToReturn;
    }
}
