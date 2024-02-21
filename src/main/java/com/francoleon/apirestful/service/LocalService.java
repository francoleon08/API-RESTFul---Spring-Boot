package com.francoleon.apirestful.service;

import com.francoleon.apirestful.entity.Local;

import java.util.List;

public interface LocalService {

    /**
     * Retorna una lista con todos los locales almacenados en la base de datos.
     * @return List<Local> lista de locales.
     */
    public List<Local> findAllLocals();

    /**
     * Almcane un local en la base de datos.
     * @param local
     * @return
     */
    public Local saveLocal(Local local);

    /**
     * Almcane una lista de locales en la base de datos.
     * @param locals
     * @return
     */
    public List<Local> saveLocals(List<Local> locals);

    /**
     * Acutaliza un local en la base de datos
     * @param id del local a actualizar
     * @param local
     * @return
     */
    public Local updateLocal(long id, Local local);

    /**
     * Elimina un local por id de la base de datos
     * @param id
     */
    public void deleteLocal(long id);
}