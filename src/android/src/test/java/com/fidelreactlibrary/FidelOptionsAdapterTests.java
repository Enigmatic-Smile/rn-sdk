package com.fidelreactlibrary;

import android.graphics.Bitmap;

import com.fidel.sdk.Fidel;
import com.fidelreactlibrary.adapters.FidelOptionsAdapter;
import com.fidelreactlibrary.fakes.DataProcessorSpy;
import com.fidelreactlibrary.fakes.ReadableMapStub;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FidelOptionsAdapterTests extends TestCase {

    private DataProcessorSpy imageAdapterSpy;
    private FidelOptionsAdapter sut;

    @Before
    public final void setUp() {
        imageAdapterSpy = new DataProcessorSpy();
        sut = new FidelOptionsAdapter(imageAdapterSpy);
    }

    @After
    public final void tearDown() {
        imageAdapterSpy = null;
        sut = null;
    }

    @Test
    public void test_ChecksForBannerImage() {
        ReadableMapStub map = new ReadableMapStub(FidelOptionsAdapter.BANNER_IMAGE_KEY, "");
        sut.process(map);
        assertEquals(map.keyNameCheckedFor, FidelOptionsAdapter.BANNER_IMAGE_KEY);
        assertEquals(map.keyNameVerifiedNullFor, FidelOptionsAdapter.BANNER_IMAGE_KEY);
        assertEquals(map.keyNameAskedFor, FidelOptionsAdapter.BANNER_IMAGE_KEY);
    }

    @Test
    public void test_IfHasBannerImageKeyButNoImage_DontTryDataToImageAdapter() {
        ReadableMapStub map = new ReadableMapStub(FidelOptionsAdapter.BANNER_IMAGE_KEY, FidelOptionsAdapter.BANNER_IMAGE_KEY);
        sut.process(map);
        assertFalse(imageAdapterSpy.hasAskedToProcessData);
    }

    @Test
    public void test_IfDoesntHaveBannerImageKey_DontTryDataToImageAdapter() {
        ReadableMapStub map = new ReadableMapStub("", "");
        sut.process(map);
        assertFalse(imageAdapterSpy.hasAskedToProcessData);
    }
    
    @Test
    public void test_WhenImageProcessorSendsBitmap_SetItForFidelBannerImage() {
        Bitmap newBitmap = Bitmap.createBitmap(100, 200, null);
        sut.output(newBitmap);
        assertEquals(Fidel.bannerImage, newBitmap);
    }
}
