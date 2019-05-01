package com.fidelreactlibrary.helpers;

import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public final class AssertHelpers {
    static public void assertMapContainsMap(@Nullable Map<String, Object> map,
                                            @Nullable Map<String, Object> mapToContain) {
        if (map == null || mapToContain == null) {
            fail("The maps to check should not be null");
        }
        Set<Map.Entry<String, Object>> mapEntriesSet = map.entrySet();
        for (Map.Entry<String, Object> entry : mapToContain.entrySet()) {
            assertThat(mapEntriesSet, hasItem(entry));
        }
    }
}
