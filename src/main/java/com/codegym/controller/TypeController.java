package com.codegym.controller;

import com.codegym.model.Note;
import com.codegym.model.Type;
import com.codegym.service.NoteService;
import com.codegym.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class TypeController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private NoteService noteService;
    @GetMapping("/type/list")
    public ModelAndView list(Pageable pageable){
        Page<Type> types = typeService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/type/list");
        modelAndView.addObject("types",types);
        return modelAndView;
    }
    @GetMapping("/type/create")
    public ModelAndView createForm(){
        ModelAndView modelAndView = new ModelAndView("/type/create");
        modelAndView.addObject("type",new Type());
        return modelAndView;
    }
    @PostMapping("/type/create")
    public ModelAndView save(@ModelAttribute Type type){
        typeService.save(type);
        ModelAndView  modelAndView = new ModelAndView("/type/create");
        modelAndView.addObject("type",type);
        return modelAndView;

    }
   @PostMapping("/type/edit")
    public ModelAndView updateCategory(@ModelAttribute Type type) {
        typeService.save(type);
        ModelAndView modelAndView = new ModelAndView("/type/edit");
        modelAndView.addObject("type", type);
        return modelAndView;
    }

    @GetMapping("/type/edit/{id}")
    public ModelAndView updateCategoryForm(@PathVariable Long id) {
        Type type = typeService.findById(id);

            ModelAndView modelAndView = new ModelAndView("/type/edit");
            modelAndView.addObject("type", type);
            return modelAndView;

    }
    @GetMapping("/type/delete/{id}")
    public ModelAndView deleteForm(@PathVariable Long id){
        Type type = typeService.findById(id);
            ModelAndView modelAndView = new ModelAndView("/type/delete");
            modelAndView.addObject("type", type);
            return modelAndView;
    }

    @PostMapping("/type/delete")
    public String deleteType(@ModelAttribute Type type){
        typeService.delete(type.getId());
        return "redirect:list";
    }
    @GetMapping("/type/view/{id}")
    public ModelAndView viewType(@PathVariable Long id,Pageable pageable){
        Type type = typeService.findById(id);
        Iterable<Note> notes = noteService.findByType(type,pageable);
        ModelAndView modelAndView = new ModelAndView("/type/view");
        modelAndView.addObject("type",type);
        modelAndView.addObject("notes", notes);
        return modelAndView;
    }


}

