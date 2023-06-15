package com.fidelreactlibrary.adapters;

import static com.fidelreactlibrary.adapters.FidelCardVerificationChoiceAdapter.CARD_VERIFICATION_CHOICE;
import static com.fidelreactlibrary.adapters.FidelCardVerificationChoiceAdapter.CARD_VERIFICATION_DELEGATED_TO_THIRD_PARTY;
import static com.fidelreactlibrary.adapters.FidelCardVerificationChoiceAdapter.CARD_VERIFICATION_ON_THE_SPOT;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.fidelapi.entities.CardVerificationChoice;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FidelCardVerificationChoiceAdapterTest {

    @Test
    public void test_ShouldExposeConstantsWithCorrectKey() {
        FidelCardVerificationChoiceAdapter sut = new FidelCardVerificationChoiceAdapter(null);

        Map<String, Object> constants = sut.getConstants();

        assertTrue(constants.containsKey(CARD_VERIFICATION_CHOICE));
    }

    @Test
    public void test_ShouldExposeConstantsThatContainDelegatedToThirdParty() {
        FidelCardVerificationChoiceAdapter sut = new FidelCardVerificationChoiceAdapter(null);

        Map<String, Object> constants = sut.getConstants();

        Map<String, String> verificationChoicesMap = (HashMap) constants.get(CARD_VERIFICATION_CHOICE);
        assertEquals(CARD_VERIFICATION_DELEGATED_TO_THIRD_PARTY, verificationChoicesMap.get(CARD_VERIFICATION_DELEGATED_TO_THIRD_PARTY));
    }

    @Test
    public void test_ShouldExposeConstantsThatContainOnTheSpot() {
        FidelCardVerificationChoiceAdapter sut = new FidelCardVerificationChoiceAdapter(null);

        Map<String, Object> constants = sut.getConstants();

        Map<String, String> verificationChoicesMap = (HashMap) constants.get(CARD_VERIFICATION_CHOICE);
        assertEquals(CARD_VERIFICATION_ON_THE_SPOT, verificationChoicesMap.get(CARD_VERIFICATION_ON_THE_SPOT));
    }

    @Test
    public void test_ShouldAdaptOnTheSpotVerificationChoiceCorrectly() {
        FidelCardVerificationChoiceAdapter sut = new FidelCardVerificationChoiceAdapter(JavaOnlyMap::new);

        ReadableMap map = sut.adapt(CardVerificationChoice.ON_THE_SPOT);

        assertEquals(CARD_VERIFICATION_ON_THE_SPOT, map.getString(CARD_VERIFICATION_CHOICE));
    }

    @Test
    public void test_ShouldAdaptDelegatedToThirdPartyCorrectly() {
        FidelCardVerificationChoiceAdapter sut = new FidelCardVerificationChoiceAdapter(JavaOnlyMap::new);

        ReadableMap map = sut.adapt(CardVerificationChoice.DELEGATED_TO_THIRD_PARTY);

        assertEquals(CARD_VERIFICATION_DELEGATED_TO_THIRD_PARTY, map.getString(CARD_VERIFICATION_CHOICE));
    }


}