package com.matmini.keyStore.service;

import com.matmini.keyStore.manager.FilesManager;
import com.matmini.keyStore.manager.Registry;
import com.matmini.keyStore.manager.interfaces.RegistryHandlerInterface;
import com.matmini.keyStore.manager.service.KeysManagerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.management.openmbean.InvalidKeyException;
import javax.management.openmbean.KeyAlreadyExistsException;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KeysManagerServiceTest {

    private FilesManager filesManagerMock;
    private RegistryHandlerInterface handlerMock;
    private KeysManagerService service;

    @BeforeEach
    public void setup() throws Exception {
        filesManagerMock = mock(FilesManager.class);
        handlerMock = mock(RegistryHandlerInterface.class);
        setFilesManagerSingleton(filesManagerMock);
        service = new KeysManagerService(handlerMock);
    }

    private void setFilesManagerSingleton(FilesManager mock) throws Exception {
        Field instanceField = FilesManager.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, mock);
    }

    @Test
    public void testSave_NewRegistry_Success() {
        Registry registry = new Registry("GitHub", "https://github.com", "user", "pass", "note");
        String key = "GitHub|https://github.com|user";

        Map<String, Registry> map = new HashMap<>();
        Map<String, Registry> newMap = new HashMap<>();
        newMap.put(key, registry);

        when(filesManagerMock.getContentAsMap()).thenReturn(map);
        when(handlerMock.getKeyMap(registry)).thenReturn(key);
        when(handlerMock.parseRegistryToMap(registry)).thenReturn(newMap);

        service.save(registry);

        verify(filesManagerMock).overwriteContentAsMap(newMap);
    }

    @Test
    public void testSave_ExistingRegistry_ThrowsException() {
        Registry registry = new Registry("GitHub", "https://github.com", "user", "pass", "note");
        String key = "GitHub|https://github.com|user";

        Map<String, Registry> map = new HashMap<>();
        map.put(key, registry);

        when(filesManagerMock.getContentAsMap()).thenReturn(map);
        when(handlerMock.getKeyMap(registry)).thenReturn(key);

        assertThrows(KeyAlreadyExistsException.class, () -> service.save(registry));
    }

    @Test
    public void testDelete_ValidKey_Success() {
        Registry registry = new Registry("GitHub", "https://github.com", "user", "pass", "note");
        String key = "GitHub|https://github.com|user";

        Map<String, Registry> map = new HashMap<>();
        map.put(key, registry);

        when(filesManagerMock.getContentAsMap()).thenReturn(map);
        when(handlerMock.getKeyMap(registry)).thenReturn(key);

        service.delete(registry);

        verify(filesManagerMock).overwriteContentAsMap(anyMap());
    }

    @Test
    public void testDelete_InvalidKey_ThrowsException() {
        Registry registry = new Registry("GitHub", "https://github.com", "user", "pass", "note");
        String key = "GitHub|https://github.com|user";

        when(filesManagerMock.getContentAsMap()).thenReturn(new HashMap<>());
        when(handlerMock.getKeyMap(registry)).thenReturn(key);

        assertThrows(InvalidKeyException.class, () -> service.delete(registry));
    }

    @Test
    public void testRealAll_ReturnsList() {
        Registry r1 = new Registry("GitHub", "url", "user", "pass", "note");
        Map<String, Registry> map = new HashMap<>();
        map.put("GitHub|url|user", r1);

        when(filesManagerMock.getContentAsMap()).thenReturn(map);

        List<Registry> result = service.realAll();

        assertEquals(1, result.size());
        assertEquals(r1, result.get(0));
    }

    @Test
    public void testUpdate_RegistryExists_ChangesSomeFields() {
        String key = "GitHub|https://github.com|user";
        Registry old = new Registry("GitHub", "https://github.com", "user", "pass", "note");
        Registry updated = new Registry("GitHub", "https://github.com", "user", "newpass", "note");

        Map<String, Registry> map = new HashMap<>();
        map.put(key, old);

        when(filesManagerMock.getContentAsMap()).thenReturn(map);
        when(handlerMock.getKeyMap(updated)).thenReturn(key);

        service.update(updated);

        assertEquals("newpass", old.getPassword());
        verify(filesManagerMock).overwriteContentAsMap(map);
    }

    @Test
    public void testUpdate_RegistryNotFound_ThrowsException() {
        Registry updated = new Registry("GitHub", "https://github.com", "user", "pass", "note");
        String key = "GitHub|https://github.com|user";

        when(filesManagerMock.getContentAsMap()).thenReturn(new HashMap<>());
        when(handlerMock.getKeyMap(updated)).thenReturn(key);

        assertThrows(IllegalArgumentException.class, () -> service.update(updated));
    }

    @Test
    public void testUpdate_NoChanges_ShouldNotOverwrite() {
        String key = "GitHub|https://github.com|user";
        Registry registry = new Registry("GitHub", "https://github.com", "user", "pass", "note");

        Map<String, Registry> map = new HashMap<>();
        map.put(key, registry);

        when(filesManagerMock.getContentAsMap()).thenReturn(map);
        when(handlerMock.getKeyMap(registry)).thenReturn(key);

        service.update(registry);

        verify(filesManagerMock, never()).overwriteContentAsMap(anyMap());
    }

    @Test
    public void testRealAllByNameLike_FilteredList() {
        Registry r1 = new Registry("GitHub", "url", "user", "pass", "note");
        Registry r2 = new Registry("Bitbucket", "url", "user", "pass", "note");
        Registry r3 = new Registry("GitLab", "url", "user", "pass", "note");

        Map<String, Registry> map = new HashMap<>();
        map.put("1", r1);
        map.put("2", r2);
        map.put("3", r3);

        when(filesManagerMock.getContentAsMap()).thenReturn(map);

        List<Registry> result = service.realAllByNameLike("git");

        assertEquals(2, result.size());
        List<String> names = Arrays.asList(result.get(0).getName(), result.get(1).getName());
        assertTrue(names.contains("GitHub"));
        assertTrue(names.contains("GitLab"));
    }

    @Test
    public void testRealAllByNameLike_EmptyMatch() {
        Registry r1 = new Registry("Bitbucket", "url", "user", "pass", "note");
        Map<String, Registry> map = new HashMap<>();
        map.put("1", r1);

        when(filesManagerMock.getContentAsMap()).thenReturn(map);

        List<Registry> result = service.realAllByNameLike("git");

        assertTrue(result.isEmpty());
    }
}
