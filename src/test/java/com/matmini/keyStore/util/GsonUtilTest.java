package com.matmini.keyStore.util;

import com.matmini.keyStore.manager.Registry;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GsonUtilTest {

    @Test
    void testToJsonFromJson() {
        Registry registry = new Registry("n1", "url.com", "user", "pass", "note");
        String json = GsonUtil.toJson(registry);
        Registry parsed = GsonUtil.fromJson(json, Registry.class);

        assertEquals(registry.getName(), parsed.getName());
        assertEquals(registry.getUrl(), parsed.getUrl());
        assertEquals(registry.getUsername(), parsed.getUsername());
        assertEquals(registry.getPassword(), parsed.getPassword());
        assertEquals(registry.getNote(), parsed.getNote());
    }
}
