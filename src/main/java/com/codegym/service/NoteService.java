package com.codegym.service;

import com.codegym.model.Note;
import com.codegym.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteService {
    Page<Note> findAll(Pageable pageable);
    Note findById(Long id);
    void save(Note note);
    void delete(Long id);
    Page<Note> findByType(Type type,Pageable pageable);
}
