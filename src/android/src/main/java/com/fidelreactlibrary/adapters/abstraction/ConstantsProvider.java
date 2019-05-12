package com.fidelreactlibrary.adapters.abstraction;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ConstantsProvider {
    @NotNull Map<String, Object> getConstants();
}
