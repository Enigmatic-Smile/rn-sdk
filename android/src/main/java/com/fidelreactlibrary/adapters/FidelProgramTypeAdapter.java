package com.fidelreactlibrary.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fidelapi.entities.ProgramType;
import com.fidelreactlibrary.adapters.abstraction.ProgramTypeAdapter;

import java.util.HashMap;
import java.util.Map;

public class FidelProgramTypeAdapter implements ProgramTypeAdapter {

    public @NonNull String keyFor(@NonNull ProgramType programType) {
        switch (programType) {
            case TRANSACTION_SELECT: return "transactionSelect";
            default: return "notFound";
        }
    }

    @NonNull
    @Override
    public ProgramType parseProgramType(@Nullable String programType) {
        ProgramType programTypeToReturn;
        try {
            programTypeToReturn = ProgramType.valueOf(programType);
        } catch (Exception e) {
            programTypeToReturn = ProgramType.TRANSACTION_SELECT;
        }
        return programTypeToReturn;
    }

    @NonNull
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        final Map<String, String> programTypesMap = new HashMap<>();
        for (ProgramType programType : ProgramType.values()) {
            String programTypeKey = keyFor(programType);
            programTypesMap.put(programTypeKey, programType.name());
        }
        constants.put("ProgramType", programTypesMap);
        return constants;
    }
}
