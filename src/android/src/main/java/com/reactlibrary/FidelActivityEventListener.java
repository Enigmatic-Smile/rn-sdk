package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.fidel.sdk.Fidel;

final class FidelActivityEventListener extends BaseActivityEventListener {



    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if(requestCode == Fidel.FIDEL_LINK_CARD_REQUEST_CODE) {

        }
    }
}
