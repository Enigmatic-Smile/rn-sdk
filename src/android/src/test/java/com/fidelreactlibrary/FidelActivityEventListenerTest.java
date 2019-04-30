package com.fidelreactlibrary;

import junit.framework.TestCase;

public class FidelActivityEventListenerTest extends TestCase {

    public void test_WhenRequestCodeIsNotFidelCode_DontCall() {
        FidelActivityEventListener sut = new FidelActivityEventListener();
        sut.onActivityResult(null,0,0,null);
    }
}