package com.francoleon.apirestful.service;

import com.francoleon.apirestful.entity.Local;
import com.francoleon.apirestful.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocalServiceImpl implements LocalService{

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
    public Local updateLocal(long id, Local local) {
        Local update = repository.findById(id).get();

        //Si no es un objeto nulo y no recibimos informacion vacia en sus atributos
        if(!"".equalsIgnoreCase(local.getCode()+"")){
            update.setCode(local.getCode());
        }
        if(!"".equalsIgnoreCase(local.getName())){
            update.setName(local.getName());
        }
        if(!"".equalsIgnoreCase(local.getFloor())){
            update.setFloor(local.getFloor());
        }

        return saveLocal(update);
    }

    @Override
    public void deleteLocal(long id) {
        repository.deleteById(id);
    }
}
