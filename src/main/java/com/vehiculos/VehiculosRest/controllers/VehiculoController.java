package com.vehiculos.VehiculosRest.controllers;

import com.vehiculos.VehiculosRest.models.VehiculoModel;
import com.vehiculos.VehiculosRest.services.VehiculoService;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Villacura
 * @author Benjamín Fernández-Niño
 */
@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {
    
    @Autowired
    private VehiculoService vehiculoService;
    
    // 1. Obtener todos los autos (GET)
    @GetMapping
    public ArrayList<VehiculoModel> getAutos(){
        return this.vehiculoService.getVehiculos();
    }
    
    // 2. Obtener un auto por su ID (GET por id)
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoModel> getAutoById(@PathVariable Long id) {
        Optional<VehiculoModel> auto = this.vehiculoService.getbyId(id);
        return auto.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    // 3. Crear un nuevo auto (POST)
    @PostMapping
    public ResponseEntity<VehiculoModel> guardarAuto(@RequestBody VehiculoModel vehiculo) {
        VehiculoModel guardado = this.vehiculoService.saveAuto(vehiculo);
        return new ResponseEntity<>(guardado, HttpStatus.CREATED);
    }

    // 4. Actualizar un auto existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoModel> actualizarAuto(@PathVariable Long id, @RequestBody VehiculoModel vehiculo) {
        try {
            VehiculoModel actualizado = this.vehiculoService.updateById(vehiculo, id);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 5. Eliminar un auto (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAuto(@PathVariable Long id) {
        try {
            this.vehiculoService.deleteAuto(id);
            return ResponseEntity.ok("Vehículo eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}