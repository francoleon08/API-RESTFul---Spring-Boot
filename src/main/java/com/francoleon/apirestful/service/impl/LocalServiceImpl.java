package com.francoleon.apirestful.service.impl;

import com.francoleon.apirestful.error.LocalNotFoundException;
import com.francoleon.apirestful.persistence.entity.Local;
import com.francoleon.apirestful.persistence.repository.LocalRepository;
import com.francoleon.apirestful.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LocalServiceImpl implements LocalService {

    @Autowired
    private LocalRepository repository;

    @Override
    public List<Local> findAllLocals() {
        return repository.findAll();
    }

    @Override
    public Local saveLocal(Local local) {
        return repository.save(local);
    }

    @Override
    public List<Local> saveLocals(List<Local> locals) {
        return repository.saveAll(locals);
    }

    @Override
    public Local updateLocal(long id, Local local) throws LocalNotFoundException{
        Optional<Local> update = repository.findById(id);

        if(!update.isPresent()){
            throw new LocalNotFoundException("Local con Id [%d] no disponible".formatted(id));
        }

        //Si no es un objeto nulo y no recibimos informacion vacia en sus atributos
        if(!"".equalsIgnoreCase(local.getCode()+"")){
            update.get().setCode(local.getCode());
        }
        if(!"".equalsIgnoreCase(local.getName())){
            update.get().setName(local.getName());
        }
        if(!"".equalsIgnoreCase(local.getFloor())){
            update.get().setFloor(local.getFloor());
        }

        return saveLocal(update.get());
    }

    @Override
    public void deleteLocal(long id) {
        repository.deleteById(id);
    }

    @Override
    public Local findLocalByName(String name) throws LocalNotFoundException {
        Optional<Local> local = repository.findByName(name);
        if(!local.isPresent()){
            throw new LocalNotFoundException("Local con nombre [%s] no disponible".formatted(name));
        }
        return repository.findByName(name).get();
    }

    @Override
    public List<Local> findAllLocalByFloor(String floor) {
        return repository.findAllByFloorIgnoreCase(floor);
    }

    @Override
    public Local findLocalById(long id) throws LocalNotFoundException {
        Optional<Local> local = repository.findById(id);
        if(!local.isPresent()){
            throw new LocalNotFoundException("Local con Id [%d] no disponible".formatted(id));
        }
        return local.get();
    }
}
