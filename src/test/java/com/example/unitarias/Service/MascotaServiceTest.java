package com.example.unitarias.Service;

import com.example.unitarias.Model.Mascota;
import com.example.unitarias.Repository.MascotaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class MascotaServiceTest {

    @Mock
    private MascotaRepository mascotaRepository;

    @InjectMocks
    private MascotaService mascotaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    /* Test para guardar mascota en la capa servicio */
    @Test
    void testGuardarMascota() {
        Mascota mascota = new Mascota(null, "Rex", "Perro", 5);
        Mascota mascotaGuardada = new Mascota(1L, "Rex", "Perro", 5);
        when(mascotaRepository.save(mascota)).thenReturn(mascotaGuardada);

        Mascota resultado = mascotaService.guardarMascota(mascota);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(mascotaRepository).save(mascota);
    }
    @Test
    void testListarMascotas() {
        Mascota m1 = new Mascota(1L, "Rex", "Perro", 5);
        Mascota m2 = new Mascota(2L, "Michi", "Gato", 2);
        when(mascotaRepository.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<Mascota> resultado = mascotaService.listarMascotas();
        assertThat(resultado).hasSize(2).contains(m1, m2);
        verify(mascotaRepository).findAll();
    }
    @Test
    void testObtenerMascotaPorId() {
        Long idMascota = 1L;
        Mascota mascota = new Mascota(idMascota, "Rex", "Perro", 5);

        // Mockeamos el comportamiento del repositorio
        when(mascotaRepository.findById(idMascota)).thenReturn(Optional.of(mascota));

        // Ejecutamos el método de servicio
        Optional<Mascota> resultado = mascotaService.obtenerMascotaPorId(idMascota);

        // Validamos el resultado
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(idMascota);
        assertThat(resultado.get().getNombre()).isEqualTo("Rex");
        assertThat(resultado.get().getTipo()).isEqualTo("Perro");
        assertThat(resultado.get().getEdad()).isEqualTo(5);

        // Verificamos que se haya llamado al repositorio
        verify(mascotaRepository).findById(idMascota);
    }
    @Test
    void testEliminarMascota() {
        Long idMascota = 1L;

        // Ejecutamos el método de servicio
        mascotaService.eliminarMascota(idMascota);

        // Verificamos que el repositorio llamó a deleteById con el id correcto
        verify(mascotaRepository).deleteById(idMascota);
    }

}