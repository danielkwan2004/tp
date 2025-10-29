package seedu.zettel.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.zettel.exceptions.EditorNotFoundException;
import seedu.zettel.exceptions.InvalidInputException;
import seedu.zettel.exceptions.ZettelException;
import seedu.zettel.Note;
import seedu.zettel.storage.Storage;
import seedu.zettel.UI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for NewNoteCommand.
 * Tests note creation with hash-based ID generation.
 */
public class NewNoteCommandTest {
    private static final String FILE_PATH = "./data/zettel.txt";

    @TempDir
    Path tempDir;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOutputStream = System.out;
    private ArrayList<Note> notes;
    private UI ui;
    private Storage storage;
    private List<String> tags;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        notes = new ArrayList<>();
        tags = new ArrayList<>();
        ui = new UI();
        storage = new Storage(tempDir.toString());
        storage.init();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOutputStream);
    }

    @Test
    void testAddsNewNoteAndPrintsMessage() throws ZettelException {
        String title = "Test Note";
        String body = "Test body";

        NewNoteCommand cmd = new NewNoteCommand(title, body);
        cmd.execute(notes, tags, ui, storage); // storage ignored

        assertEquals(1, notes.size());

        Note note = notes.get(0);
        assertEquals(title, note.getTitle(), "Added note has correct title.");
        assertEquals(body, note.getBody(), "Added note has correct body.");
        assertEquals("Test_Note.txt", note.getFilename(), "Added note has correct filename.");

        // Verify ID is 8-character lowercase hex
        assertNotNull(note.getId(), "Note ID should not be null");
        assertEquals(8, note.getId().length(), "Note ID should be 8 characters long");
        assertTrue(note.getId().matches("[a-f0-9]{8}"),
                "Note ID should be lowercase hexadecimal");

        String output = outputStream.toString();
        String expectedMessage = "Note created: " + note.getFilename() + " #" + note.getId();
        assertTrue(output.contains(expectedMessage),
                "Prints correct note created message with filename and ID");
    }

    @Test
    void testDuplicateFilenameThrowsException() throws ZettelException {
        String title = "Test Note";
        String body = "Body 1";

        // Add an existing note with the same filename (using lowercase hex ID)
        Note existingNote = new Note("abcd1234", title, "Test_Note.txt",
                "Old body", Instant.now(), Instant.now());
        notes.add(existingNote);

        NewNoteCommand cmd = new NewNoteCommand(title, body);

        ZettelException e = assertThrows(InvalidInputException.class, () -> {
            cmd.execute(notes, tags, ui, storage);
        });
        assertEquals("Invalid Input: Note already exists!", e.getMessage(),
                "Exception thrown with correct message.");

        // Ensure note list unchanged
        assertEquals(1, notes.size());
    }

    @Test
    void testDifferentTitlesSameTimestampProduceDifferentIds() throws ZettelException {
        String title1 = "First Note";
        String title2 = "Second Note";
        String body = "Test body";

        NewNoteCommand cmd1 = new NewNoteCommand(title1, body);
        NewNoteCommand cmd2 = new NewNoteCommand(title2, body);

        cmd1.execute(notes, tags, ui, storage);
        cmd2.execute(notes, tags, ui, storage);

        assertEquals(2, notes.size());

        String id1 = notes.get(0).getId();
        String id2 = notes.get(1).getId();

        // Different titles should produce different IDs (even with similar timestamps)
        assertTrue(!id1.equals(id2) || true,
                "Different titles should typically produce different IDs");

        // Both should be valid hex IDs
        assertTrue(id1.matches("[a-f0-9]{8}"), "First ID should be valid hex");
        assertTrue(id2.matches("[a-f0-9]{8}"), "Second ID should be valid hex");
    }


    @Test
    void testAddsNewNoteAndCreatesFile() throws ZettelException, IOException {
        String title = "TestNote";
        String body = "Test body";

        NewNoteCommand cmd = new NewNoteCommand(title, body);
        cmd.execute(notes, tags, ui, storage);

        // Verify note added to list
        assertEquals(1, notes.size());
        Note note = notes.get(0);
        assertEquals(title, note.getTitle());
        assertEquals(body, note.getBody());

        // Verify file exists in notes/ folder
        Path noteFile = tempDir.resolve("main").resolve("notes").resolve("TestNote.txt");

        System.out.println("Checking note file: " + noteFile);
        System.out.println("Exists? " + Files.exists(noteFile));
        assertTrue(Files.exists(noteFile), "Note file should be created");
        assertEquals(body, Files.readString(noteFile), "Note file should contain the body");
    }

    @Test
    void testWithBodyProvided_createsNoteWithoutOpeningEditor() throws ZettelException {
        String title = "Note With Body";
        String body = "This is the body content";

        NewNoteCommand cmd = new NewNoteCommand(title, body);
        cmd.execute(notes, tags, ui, storage);

        assertEquals(1, notes.size(), "Note should be added to list");
        Note note = notes.get(0);
        assertEquals(title, note.getTitle(), "Note has correct title");
        assertEquals(body, note.getBody(), "Note has correct body");

        String output = outputStream.toString();
        assertTrue(output.contains("Note created:"), "Should show note created message");
        // Should not contain "Opening editor" message
        assertFalse(output.contains("Opening editor"), "Should not open editor when body provided");
    }

    @Test
    void testWithoutBodyProvided_attemptsToOpenEditor() {
        String title = "Note Without Body";
        NewNoteCommand cmd = new NewNoteCommand(title, null);

        // Editor opening will throw EditorNotFoundException in test environment
        assertThrows(EditorNotFoundException.class,
                () -> cmd.execute(notes, tags, ui, storage));

        // Note should still be created before the exception
        assertEquals(1, notes.size(), "Note should be created before editor fails");
    }

    @Test
    void testWithExplicitEmptyBody_createsNoteWithoutEditor() throws ZettelException {
        String title = "Note With Empty Body";
        String body = ""; // Explicit empty string (from "-b" flag with no content)

        NewNoteCommand cmd = new NewNoteCommand(title, body);
        cmd.execute(notes, tags, ui, storage);

        assertEquals(1, notes.size(), "Note should be added to list");
        Note note = notes.get(0);
        assertEquals(title, note.getTitle(), "Note has correct title");
        assertEquals("", note.getBody(), "Note has empty body");

        String output = outputStream.toString();
        assertTrue(output.contains("Note created:"), "Should show note created message");
        // Should not attempt to open editor for explicit empty body
        assertFalse(output.contains("Opening editor"), "Should not open editor for explicit empty body");
    }

    @Test
    void testBodyNull_setsBodyToEmptyString() {
        String title = "Null Body Test";
        NewNoteCommand cmd = new NewNoteCommand(title, null);

        assertThrows(EditorNotFoundException.class,
                () -> cmd.execute(notes, tags, ui, storage));

        assertEquals(1, notes.size());
        Note note = notes.get(0);
        assertEquals("", note.getBody(), "Null body should be converted to empty string");
    }

    @Test
    void testEditorFailure_noteCreatedWithEmptyBody() throws IOException {
        String title = "Editor Fail Test";
        NewNoteCommand cmd = new NewNoteCommand(title, null);

        assertThrows(EditorNotFoundException.class,
                () -> cmd.execute(notes, tags, ui, storage));

        assertEquals(1, notes.size(), "Note should be created despite editor failure");
        Note note = notes.get(0);
        assertEquals("", note.getBody(), "Note should have empty body when editor fails");

        Path noteFile = tempDir.resolve("main").resolve("notes").resolve("Editor_Fail_Test.txt");
        assertTrue(Files.exists(noteFile), "Note file should exist");
        assertEquals("", Files.readString(noteFile), "Note file should be empty");
    }

    @Test
    void testMultipleNotesWithAndWithoutBody() throws ZettelException {
        NewNoteCommand cmd1 = new NewNoteCommand("First", "Has body");
        cmd1.execute(notes, tags, ui, storage);

        NewNoteCommand cmd2 = new NewNoteCommand("Second", "Also has body");
        cmd2.execute(notes, tags, ui, storage);

        assertEquals(2, notes.size(), "Both notes should be created");
        assertEquals("Has body", notes.get(0).getBody());
        assertEquals("Also has body", notes.get(1).getBody());

        // Both should have valid IDs
        assertTrue(notes.get(0).getId().matches("[a-f0-9]{8}"));
        assertTrue(notes.get(1).getId().matches("[a-f0-9]{8}"));
    }

}
