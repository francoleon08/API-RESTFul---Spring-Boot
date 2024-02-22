package com.francoleon.apirestful.service;

import com.francoleon.apirestful.error.LocalNotFoundException;
import com.francoleon.apirestful.persistence.entity.Local;
import com.francoleon.apirestful.persistence.repository.LocalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LocalServiceTest {

    @Autowired
    private LocalService localService;
    @MockBean
    private LocalRepository localRepository;

    @BeforeEach
    void setUp() {
        Local local_1 = Local.builder()
                .id(1L)
                .name("The shop")
                .floor("Second Floor")
                .code(1234)
                .build();
        Local local_2 = Local.builder()
                .id(2L)
                .name("Free")
                .floor("First Floor")
                .code(999)
                .build();
        Local local_3 = Local.builder()
                .id(3L)
                .name("Small Shop")
                .floor("Second Floor")
                .code(100)
                .build();
        List<Local> list = Arrays.asList(local_1, local_2, local_3);

        // Para el método findLocalByNameFound
        Mockito.when(localRepository.findByName("The shop")).thenReturn(Optional.of(local_1));

        // Para el método findLocalByNameNotFound
        Mockito.when(localRepository.findByName("Non-existent shop")).thenReturn(Optional.empty());

        // Para el método findAllLocals
        Mockito.when(localRepository.findAll()).thenReturn(list);

        // Para el método saveLocal
        Mockito.when(localRepository.save(Mockito.any(Local.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Para el método saveLocals
        Mockito.when(localRepository.saveAll(Mockito.anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // Para el método updateLocal
        Mockito.when(localRepository.save(Mockito.any(Local.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(localRepository.findById(Mockito.anyLong())).thenAnswer(invocation -> {
            long id = invocation.getArgument(0);
            return list.stream().filter(local -> local.getId() == id).findFirst();
        });
    }

    @Test
    void findLocalByNameFound() {
        String localName = "The shop";
        try {
            Local local = localService.findLocalByName(localName);
            assertEquals(local.getName(), localName);
        } catch (LocalNotFoundException e) {
            fail("Error: El servicio findLocalByName() al consultar con [%s]".formatted(localName));
        }
    }

    @Test
    void findLocalByNameNotFound() {
        String localName = "Non-existent shop";

        //Verifica que ante la falla el servicio lanze su correspondiente Exception
        assertThrows(LocalNotFoundException.class, () -> {
            localService.findLocalByName(localName);
        });

        //Verifica que assertThrows haya llamado una vez a findLocalByName().
        verify(localRepository, times(1)).findByName(localName);
    }

    @Test
    void findAllLocals() {
        List<Local> local_list = localService.findAllLocals();
        assertEquals(local_list.size(), 3);
    }

    @Test
    void saveLocal() {
        // Crea un local nuevo
        Local local = Local.builder()
                .name("New Shop")
                .floor("Third Floor")
                .code(555)
                .build();

        // Simula el comportamiento del repositorio al guardar un nuevo local
        Mockito.when(localRepository.save(local)).thenReturn(local);

        // Llama al método del servicio para guardar el local y verifica que se haya guardado correctamente
        Local savedLocal = localService.saveLocal(local);
        assertNotNull(savedLocal);
        assertEquals("New Shop", savedLocal.getName());
        assertEquals("Third Floor", savedLocal.getFloor());
        assertEquals(555, savedLocal.getCode());
    }

    @Test
    void saveLocals() {
        // Crea una lista de locales para guardar
        List<Local> localsToSave = new ArrayList<>();
        localsToSave.add(Local.builder().name("Shop 1").floor("First Floor").code(111).build());
        localsToSave.add(Local.builder().name("Shop 2").floor("Second Floor").code(222).build());
        localsToSave.add(Local.builder().name("Shop 3").floor("Third Floor").code(333).build());

        // Simula el comportamiento del repositorio al guardar los locales
        Mockito.when(localRepository.saveAll(localsToSave)).thenReturn(localsToSave);

        // Llama al método del servicio para guardar los locales y verifica que se hayan guardado correctamente
        List<Local> savedLocals = localService.saveLocals(localsToSave);
        assertNotNull(savedLocals);
        assertEquals(3, savedLocals.size());
        assertEquals("Shop 1", savedLocals.get(0).getName());
        assertEquals("Shop 2", savedLocals.get(1).getName());
        assertEquals("Shop 3", savedLocals.get(2).getName());
    }

    @Test
    void updateLocal() {
        try {
            // Crea un local existente y modifica su nombre
            Optional<Local> existingLocal = Optional.ofNullable(Local.builder()
                    .id(1L)
                    .name("Existing Shop")
                    .floor("First Floor")
                    .code(123)
                    .build());
            String updatedName = "Updated Shop";

            // Simula el comportamiento del repositorio al actualizar el local
            Mockito.when(localRepository.save(existingLocal.get())).thenReturn(existingLocal.get());
            Mockito.when(localRepository.findById(existingLocal.get().getId())).thenReturn(existingLocal);

            // Llama al método del servicio para actualizar el local y verifica que se haya actualizado correctamente
            existingLocal.get().setName(updatedName);
            Local updatedLocal = null;
            updatedLocal = localService.updateLocal(existingLocal.get().getId(), existingLocal.get());
            assertEquals(updatedName, updatedLocal.getName());
        } catch (LocalNotFoundException e) {
            fail("El metodo no funciona correctamente");
        }
    }

    @Test
    void deleteLocal() {
        // Arrange
        long idToDelete = 1L;
        // Act
        localService.deleteLocal(idToDelete);
        // Assert
        verify(localRepository, times(1)).deleteById(idToDelete);
    }

    @Test
    void findAllLocalByFloor() {
        // Define el piso de búsqueda
        String floor = "Second Floor";

        // Simula el comportamiento del repositorio al buscar los locales por piso
        List<Local> locals = Arrays.asList(
                Local.builder().name("Shop 1").floor("Second Floor").code(111).build(),
                Local.builder().name("Shop 2").floor("Second Floor").code(222).build(),
                Local.builder().name("Shop 3").floor("Second Floor").code(333).build()
        );
        Mockito.when(localRepository.findAllByFloorIgnoreCase(floor)).thenReturn(locals);

        // Llama al método del servicio para buscar los locales por piso y verifica los resultados
        List<Local> foundLocals = localService.findAllLocalByFloor(floor);
        assertNotNull(foundLocals);
        assertEquals(3, foundLocals.size());
        assertEquals("Second Floor", foundLocals.get(0).getFloor());
        assertEquals("Second Floor", foundLocals.get(1).getFloor());
        assertEquals("Second Floor", foundLocals.get(2).getFloor());
    }

    @Test
    void findLocalById() {
        try {
            // Define el ID del local a buscar
            Long id = 1L;

            // Crea el local existente para el ID dado
            Local existingLocal = Local.builder().id(id).name("Existing Shop").floor("First Floor").code(123).build();

            // Simula el comportamiento del repositorio al buscar el local por ID
            Mockito.when(localRepository.findById(id)).thenReturn(Optional.of(existingLocal));

            // Llama al método del servicio para buscar el local por ID y verifica los resultados
            Local foundLocal = null;
            foundLocal = localService.findLocalById(id);
            assertNotNull(foundLocal);
            assertEquals(id, foundLocal.getId());
            assertEquals("Existing Shop", foundLocal.getName());
            assertEquals("First Floor", foundLocal.getFloor());
            assertEquals(123, foundLocal.getCode());
        } catch (LocalNotFoundException e) {
            fail("El metodo no funciona correctamente");
        }
    }
}
