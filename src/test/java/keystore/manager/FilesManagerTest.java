//package keystore.manager;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.io.TempDir;
//
//import keyStore.manager.FilesManager;
//import keyStore.manager.Key;
//
//public class FilesManagerTest {
//
//    @TempDir
//    File tempDir;
//    
//    private FilesManager filesManager;
//    private File tempFile;
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        filesManager = FilesManager.getInstance();
//        tempFile = new File(tempDir, "testFile.txt");
//        filesManager.setFilePath(tempFile.getAbsolutePath());
//        tempFile.createNewFile();
//    }
//
//    @Test
//    public void testSingletonInstance() {
//        FilesManager instance1 = FilesManager.getInstance();
//        FilesManager instance2 = FilesManager.getInstance();
//        assertNotNull(instance1);
//        assertEquals(instance1, instance2, "FilesManager should return the same instance");
//    }
//
//    @Test
//    public void testGetContentAsString_emptyFile() throws IOException {
//        String content = filesManager.getContentAsString();
//        assertEquals("", content, "The content of an empty file should be an empty string");
//    }
//
//    @Test
//    public void testGetContentAsString_nonEmptyFile() throws IOException {
//        try (FileWriter writer = new FileWriter(tempFile)) {
//            writer.write("line1\nline2\nline3\n");
//        }
//
//        String content = filesManager.getContentAsString();
//        assertEquals("line1\nline2\nline3\n", content, "The content should match the content of the file");
//    }
//
//    @Test
//    public void testOverwriteFile() throws IOException {
//        String newContent = "This is new content for the file.";
//        filesManager.overwriteFile(newContent);
//
//        String content = filesManager.getContentAsString();
//        assertEquals(newContent + "\n", content, "The content should match the new content written to the file");
//    }
//
//    @Test
//    public void testGetContentAsMapAndOverwriteContentAsMap() {
//        Map<String, List<Key>> keysMap = new HashMap<>();
//        filesManager.overwriteContentAsMap(keysMap);
//
//        Map<String, List<Key>> contentMap = filesManager.getContentAsMap();
//        assertEquals(keysMap, contentMap, "The content map should match the original map written to the file");
//    }
//}
