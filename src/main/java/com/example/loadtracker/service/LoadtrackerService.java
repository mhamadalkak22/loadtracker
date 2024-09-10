package com.example.loadtracker.service;

import com.example.loadtracker.entity.Loadtracker;
import com.example.loadtracker.repository.LoadtrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LoadtrackerService {

    @Autowired
    private LoadtrackerRepository loadtrackerRepository;

    public Loadtracker save(Loadtracker loadtracker) {
        return loadtrackerRepository.save(loadtracker);
    }

    public List<Loadtracker> findAll() {
        return loadtrackerRepository.findAll();
    }

    public Optional<Loadtracker> findById(Long id) {
        return loadtrackerRepository.findById(id);
    }

    public List<Loadtracker> findByShipperId(String  shipperId) {
        return loadtrackerRepository.findByShipperId(shipperId);
    }

    public void deleteById(Long id) {
        loadtrackerRepository.deleteById(id);
    }
}
