package com.fidelreactlibrary.events;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.WritableMap;
import com.fidelapi.entities.EnrollmentResult;
import com.fidelapi.entities.FidelError;
import com.fidelapi.entities.FidelResult;
import com.fidelapi.entities.abstraction.OnResultObserver;
import com.fidelreactlibrary.adapters.abstraction.ConstantsProvider;
import com.fidelreactlibrary.adapters.abstraction.DataAdapter;
import com.fidelreactlibrary.adapters.abstraction.DataProcessor;
import com.fidelreactlibrary.adapters.abstraction.ObjectFactory;

import java.util.HashMap;
import java.util.Map;

public final class ResultsObserver implements OnResultObserver, ConstantsProvider {

    private final DataAdapter<Object, WritableMap> resultAdapter;
    private final DataProcessor<WritableMap> resultHandler;
    private final ObjectFactory<WritableMap> writableMapFactory;

    private final String ENROLLMENT_RESULT_TYPE = "EnrollmentResult";
    private final String ERROR_RESULT_TYPE = "Error";
    private final String VERIFICATION_SUCCESSFUL_RESULT_TYPE = "VerificationSuccessful";

    public ResultsObserver(DataAdapter<Object, WritableMap> resultAdapter,
                           DataProcessor<WritableMap> resultHandler,
                           ObjectFactory<WritableMap> writableMapFactory) {
        this.resultAdapter = resultAdapter;
        this.resultHandler = resultHandler;
        this.writableMapFactory = writableMapFactory;
    }

    @Override
    public void onResultAvailable(FidelResult fidelResult) {
        WritableMap resultMap = writableMapFactory.create();
        String RESULT_TYPE_KEY = "type";
        if (fidelResult instanceof FidelResult.Enrollment) {
            EnrollmentResult enrollmentResult = ((FidelResult.Enrollment) fidelResult).getEnrollmentResult();
            WritableMap adaptedObject = resultAdapter.getAdaptedObjectFor(enrollmentResult);
            resultMap.putString(RESULT_TYPE_KEY, ENROLLMENT_RESULT_TYPE);
            resultMap.putMap("enrollmentResult", adaptedObject);
        } else if (fidelResult instanceof FidelResult.VerificationSuccessful) {
            resultMap.putString(RESULT_TYPE_KEY, VERIFICATION_SUCCESSFUL_RESULT_TYPE);
        } else {
            resultMap.putString(RESULT_TYPE_KEY, ERROR_RESULT_TYPE);
            FidelError error = ((FidelResult.Error) fidelResult).getError();
            WritableMap adaptedError = resultAdapter.getAdaptedObjectFor(error);
            resultMap.putMap("error", adaptedError);
        }
        resultHandler.process(resultMap);
    }

    @NonNull
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        final Map<String, String> resultTypesMap = new HashMap<>();
        resultTypesMap.put(ENROLLMENT_RESULT_TYPE, ENROLLMENT_RESULT_TYPE);
        resultTypesMap.put(ERROR_RESULT_TYPE, ERROR_RESULT_TYPE);
        resultTypesMap.put(VERIFICATION_SUCCESSFUL_RESULT_TYPE, VERIFICATION_SUCCESSFUL_RESULT_TYPE);
        constants.put("ResultType", resultTypesMap);
        return constants;
    }
}
