package com.francoleon.apirestful.controller;

import com.francoleon.apirestful.entity.Local;
import com.francoleon.apirestful.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocalController {

    @Autowired
    private LocalService service;

    @GetMapping("/findAllLocals")
    public List<Local> findAllLocals() {
        return service.findAllLocals();
    }

    @PostMapping("/saveLocal")
    public Local saveLocal(@RequestBody Local local){
        return service.saveLocal(local);
    }

    @PostMapping("/saveLocals")
    public List<Local> saveLocals(@RequestBody List<Local> locals){
        return service.saveLocals(locals);
    }

    @PutMapping ("/updateLocal/{id}")
    public Local updateLocal(@PathVariable Long id, @RequestBody Local local) {
        return service.updateLocal(id, local);
    }

    @DeleteMapping ("/deleteLocal/{id}")
    public void deleteLocal(@PathVariable long id) {
        service.deleteLocal(id);
    }
}