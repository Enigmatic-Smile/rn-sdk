package com.fidelreactlibrary.fakes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fidelapi.entities.ProgramType;
import com.fidelreactlibrary.adapters.abstraction.ProgramTypeAdapter;

import java.util.HashMap;
import java.util.Map;

public class ProgramTypeAdapterStub implements ProgramTypeAdapter {

    public ProgramType programTypeToReturn;
    public String receivedProgramTypeString;

    @NonNull
    @Override
    public ProgramType parseProgramType(@Nullable String programType) {
        receivedProgramTypeString = programType;
        return programTypeToReturn;
    }

    @NonNull
    @Override
    public Map<String, Object> getConstants() {
        HashMap<String, Object> hashmapToReturn = new HashMap<>();
        hashmapToReturn.put("some key", "some value");
        return hashmapToReturn;
    }
}
