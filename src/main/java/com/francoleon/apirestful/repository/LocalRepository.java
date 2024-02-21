package com.francoleon.apirestful.repository;

import com.francoleon.apirestful.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

    @Query
    public Optional<Local> findByName(String name);

    @Query
    public List<Local> findAllByFloor(String floor);
}
