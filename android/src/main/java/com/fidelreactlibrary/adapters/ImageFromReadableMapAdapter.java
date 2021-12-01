package com.fidelreactlibrary.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Closeables;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferInputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.react.bridge.ReadableMap;
import com.fidelreactlibrary.adapters.abstraction.DataOutput;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;

import java.io.IOException;

public final class ImageFromReadableMapAdapter implements DataProcessor<ReadableMap> {

    public DataOutput<Bitmap> bitmapOutput;
    private final Context context;

    public ImageFromReadableMapAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void process(ReadableMap data) {
        if (data == null || !data.hasKey("uri")) {
            bitmapOutput.output(null);
            return;
        }
        String imageURIString = data.getString("uri");
        Uri imageURI = Uri.parse(imageURIString);
        if (imageURI.getScheme() != null && (imageURI.getScheme().equals("http") ||
                imageURI.getScheme().equals("https"))) {
            ImageDecodeOptions decodeOptions = ImageDecodeOptions.newBuilder().build();
            ImageRequest imageRequest = ImageRequestBuilder
                    .newBuilderWithSource(imageURI)
                    .setImageDecodeOptions(decodeOptions)
                    .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                    .build();
            Fresco.initialize(context);
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            DataSource<CloseableReference<PooledByteBuffer>> dataSource = imagePipeline.fetchEncodedImage(imageRequest, context);

            BaseDataSubscriber dataSubscriber = new BaseDataSubscriber<CloseableReference<PooledByteBuffer>>() {
                @Override
                protected void onNewResultImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
                    if (!dataSource.isFinished()) {
                        return;
                    }

                    CloseableReference<PooledByteBuffer> result = dataSource.getResult();

                    if (result == null) {
                        return;
                    }

                    PooledByteBufferInputStream inputStream = new PooledByteBufferInputStream(result.get());

                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        bitmapOutput.output(bitmap);
                    } catch (Exception e) {

                    } finally {
                        Closeables.closeQuietly(inputStream);
                    }
                }

                @Override
                protected void onFailureImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {

                }
            };

            dataSource.subscribe(dataSubscriber, CallerThreadExecutor.getInstance());
        } else {
            try {
                Uri relativeImageURI = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/" + imageURIString);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), relativeImageURI);
                bitmapOutput.output(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
