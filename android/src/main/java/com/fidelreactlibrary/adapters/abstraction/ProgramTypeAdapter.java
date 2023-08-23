package com.fidelreactlibrary.adapters.abstraction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fidelapi.entities.ProgramType;

public interface ProgramTypeAdapter extends ConstantsProvider {
    @NonNull
    ProgramType parseProgramType(@Nullable String programType);
}
