package com.fidelreactlibrary.helpers;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

public final class AssertHelpers {
    static public void assertMapContainsMap(Map<String, Object> map,
                                            Map<String, Object> mapToContain) {
        Set<Map.Entry<String, Object>> mapEntriesSet = map.entrySet();
        for (Map.Entry<String, Object> entry : mapToContain.entrySet()) {
            assertThat(mapEntriesSet, hasItem(entry));
        }
    }
}
