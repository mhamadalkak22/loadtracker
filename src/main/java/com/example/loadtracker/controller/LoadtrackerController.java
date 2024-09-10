package com.example.loadtracker.controller;

import com.example.loadtracker.entity.Loadtracker;
import com.example.loadtracker.service.LoadtrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/load")
public class LoadtrackerController {

    @Autowired
    private LoadtrackerService loadtrackerService;


    @PostMapping
    public ResponseEntity<String> createLoad(@RequestBody Loadtracker loadtracker) {
        String shipperId = loadtracker.getShipperId();

        if (shipperId == null) {
            UUID generatedUUID = UUID.randomUUID();
            loadtracker.setShipperId(generatedUUID.toString());
        } else {
            if (shipperId.startsWith("shipper:")) {
                try {
                    String uuidPart = shipperId.substring(8);
                    UUID uuid = UUID.fromString(uuidPart);
                    loadtracker.setShipperId(uuid.toString());
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.badRequest().body("Invalid UUID format");
                }
            } else {
                return ResponseEntity.badRequest().body("Shipper ID must start with 'shipper:'");
            }
        }
        loadtrackerService.save(loadtracker);

        return ResponseEntity.ok("Load details added successfully");
    }


    @GetMapping
    public ResponseEntity<List<Loadtracker>> getLoads(@RequestParam(required = false) String shipperId) {
        if (shipperId != null) {
            try {
                String uuidPart = shipperId.substring(8);
                List<Loadtracker> loads = loadtrackerService.findByShipperId(shipperId);
                return ResponseEntity.ok(loads);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(null);
            }
        } else if (shipperId == null) {
            List<Loadtracker> loads = loadtrackerService.findAll();
            return ResponseEntity.ok(loads);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{loadId}")
    public ResponseEntity<Loadtracker> getLoadById(@PathVariable Long loadId) {
        Optional<Loadtracker> loadtracker = loadtrackerService.findById(loadId);
        return loadtracker.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{loadId}")
    public ResponseEntity<String> updateLoad(@PathVariable Long loadId, @RequestBody Loadtracker loadtracker) {
        Optional<Loadtracker> existingLoadtracker = loadtrackerService.findById(loadId);

        if (existingLoadtracker.isPresent()) {
            Loadtracker updatedLoadtracker = existingLoadtracker.get();
            updatedLoadtracker.setLoadingPoint(loadtracker.getLoadingPoint());
            updatedLoadtracker.setUnloadingPoint(loadtracker.getUnloadingPoint());
            updatedLoadtracker.setProductType(loadtracker.getProductType());
            updatedLoadtracker.setTruckType(loadtracker.getTruckType());
            updatedLoadtracker.setNoOfTrucks(loadtracker.getNoOfTrucks());
            updatedLoadtracker.setWeight(loadtracker.getWeight());
            updatedLoadtracker.setDate(loadtracker.getDate());
            updatedLoadtracker.setComment(loadtracker.getComment());

            loadtrackerService.save(updatedLoadtracker);
            return ResponseEntity.ok("Load details updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{loadId}")
    public ResponseEntity<String> deleteLoad(@PathVariable Long loadId) {
        Optional<Loadtracker> loadtracker = loadtrackerService.findById(loadId);

        if (loadtracker.isPresent()) {
            loadtrackerService.deleteById(loadId);
            return ResponseEntity.ok("Load details deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
