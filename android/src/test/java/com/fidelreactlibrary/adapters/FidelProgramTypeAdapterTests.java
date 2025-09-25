package com.fidelreactlibrary.adapters;

import static org.junit.Assert.*;

import com.fidelapi.entities.ProgramType;
import com.fidelreactlibrary.adapters.FidelProgramTypeAdapter;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FidelProgramTypeAdapterTests {
    private final FidelProgramTypeAdapter sut = new FidelProgramTypeAdapter();

    @Test
    public void test_ToExposeProgramTypeConstants() {
        Map<String, Object> exposedConstants = sut.getConstants();
        String expectedProgramTypesConstantsKey = "ProgramType";
        assertTrue(exposedConstants.containsKey(expectedProgramTypesConstantsKey));

        Map<String, String> programTypesMap = (HashMap<String, String>)exposedConstants.get(expectedProgramTypesConstantsKey);
        assertNotNull(programTypesMap);

        for (ProgramType programType : ProgramType.values()) {
            String programTypeKey = sut.keyFor(programType);
            assertNotEquals(programTypeKey, "notFound");
            assertTrue(programTypesMap.containsKey(programTypeKey));
            String programTypeValue = programTypesMap.get(programTypeKey);
            assertNotNull(programTypeValue);
            assertEquals(programTypeValue, programType.name());
        }
    }

    @Test
    public void test_WhenReceivingToParseProgramTypeWithNullValue_ShouldReturnTransactionSelectProgramType() {
        assertEquals(ProgramType.TRANSACTION_SELECT, sut.parseProgramType(null));
    }

    @Test
    public void test_WhenReceivingToParseProgramTypeWithUnknownValue_ShouldReturnTransactionSelectProgramType() {
        assertEquals(ProgramType.TRANSACTION_SELECT, sut.parseProgramType("unknown program type value"));
    }

    @Test
    public void test_WhenReceivingToParseProgramTypeWithTransactionSelectValue_ShouldReturnTransactionSelectProgramType() {
        assertEquals(ProgramType.TRANSACTION_SELECT, sut.parseProgramType(ProgramType.TRANSACTION_SELECT.name()));
    }
}
