package com.francoleon.apirestful.controller;

import com.francoleon.apirestful.error.LocalNotFoundException;
import com.francoleon.apirestful.persistence.entity.Local;
import com.francoleon.apirestful.service.LocalService;
import jakarta.validation.Valid;
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

    @GetMapping("/findLocalByName/{name}")
    public Local findLocalByName(@PathVariable String name) throws LocalNotFoundException {
        return service.findLocalByName(name);
    }

    @GetMapping("/findLocalById/{id}")
    public Local findLocalById(@PathVariable long id) throws LocalNotFoundException {
        return service.findLocalById(id);
    }

    @GetMapping("/findAllLocalByFloor/{floor}")
    public List<Local> findAllLocalByFloor(@PathVariable String floor) {
        return service.findAllLocalByFloor(floor);
    }

    @PostMapping("/saveLocal")
    public Local saveLocal(@Valid @RequestBody Local local){
        return service.saveLocal(local);
    }

    //falta caputurar la excpetion en RestResponse.. para cuando alguna entidad no es valida
    @PostMapping("/saveLocals")
    public List<Local> saveLocals(@RequestBody List<@Valid Local> locals) {
        return service.saveLocals(locals);
    }

    @PutMapping ("/updateLocal/{id}")
    public Local updateLocal(@PathVariable Long id, @Valid @RequestBody Local local) throws LocalNotFoundException {
        return service.updateLocal(id, local);
    }

    @DeleteMapping ("/deleteLocal/{id}")
    public void deleteLocal(@PathVariable long id) {
        service.deleteLocal(id);
    }
}
