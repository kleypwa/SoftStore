package me.kley.soft_store.serviceTests;

import me.kley.soft_store.models.Note;
import me.kley.soft_store.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Integration tests for {@link NoteService} */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class NoteServiceIT {
    private static final int NOTES_COUNT = 3;
    private final NoteService underTest;

    @Autowired
    public NoteServiceIT(NoteService underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatFindAllMethodHasNotEmptyResult() {
        final List<Note> result = underTest.findAll();
        assertEquals(NOTES_COUNT, result.size());
    }
}
