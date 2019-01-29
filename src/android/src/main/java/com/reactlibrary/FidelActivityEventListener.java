package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.BaseActivityEventListener;
import com.fidel.sdk.Fidel;
import com.fidel.sdk.LinkResult;

final class FidelActivityEventListener extends BaseActivityEventListener {
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);
        if(requestCode == Fidel.FIDEL_LINK_CARD_REQUEST_CODE) {
            if(data != null && data.hasExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD)) {
                LinkResult card = data.getParcelableExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD);
                Log.d("d", "CARD ID = " + card.id);
                Log.d("e", "CARD error = " + card.getError().message);
            }
        }
    }
}
