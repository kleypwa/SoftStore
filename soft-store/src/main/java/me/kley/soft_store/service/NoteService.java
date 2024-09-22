package me.kley.soft_store.service;

import me.kley.soft_store.models.Note;
import me.kley.soft_store.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private AppUserService appUserService;

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    public ResponseEntity<String> addNote(Note note) {
        note.setAuthor(appUserService.getCurrentAuthenticatedUser().getId());
        Note isSaved = this.saveNote(note);

        if (isSaved != null) {
            return ResponseEntity.ok("Note successfully added.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add note.");
        }
    }
}