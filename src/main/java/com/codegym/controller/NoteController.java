package com.codegym.controller;

import com.codegym.model.Note;
import com.codegym.model.Type;
import com.codegym.service.NoteService;
import com.codegym.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private TypeService typeService;

    @ModelAttribute("types")
    public Iterable<Type> types(Pageable pageable) {
        return typeService.findAll(pageable);
    }

    @GetMapping("/list")
    public ModelAndView notes(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
        Page<Note> notes;
        Sort sort = new Sort(Sort.Direction.ASC, "time");
        Pageable pageable = new PageRequest(page, size, sort);
        ModelAndView modelAndView = new ModelAndView("/note/list");
        notes = noteService.findAll(pageable);
        modelAndView.addObject("notes", notes);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/note/create");
        modelAndView.addObject("note", new Note());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createNote(@ModelAttribute Note note) {
        noteService.save(note);
        ModelAndView modelAndView = new ModelAndView("note/create");
        modelAndView.addObject("note", new Note());
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Note note = noteService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/note/delete");
        modelAndView.addObject("note", note);
        return modelAndView;

    }

    @PostMapping("/delete")
    public String deleteNote(@ModelAttribute Note note) {
        noteService.delete(note.getId());
        return "redirect:/note/list";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Note note = noteService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/note/edit");
        modelAndView.addObject("note", note);
        return modelAndView;

    }

    @PostMapping("/edit")
    public ModelAndView editNote(@ModelAttribute Note note) {
        noteService.save(note);
        ModelAndView modelAndView = new ModelAndView("/note/edit");
        modelAndView.addObject("note", note);
        return modelAndView;
    }
}
