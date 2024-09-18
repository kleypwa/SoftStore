package me.kley.soft_store.service;

import me.kley.soft_store.models.Note;
import me.kley.soft_store.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }
}