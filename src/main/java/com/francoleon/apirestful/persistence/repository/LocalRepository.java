package com.francoleon.apirestful.persistence.repository;

import com.francoleon.apirestful.persistence.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

    public Optional<Local> findByName(String name);
    public List<Local> findAllByFloorIgnoreCase(String floor);
}
