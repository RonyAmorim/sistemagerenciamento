package br.com.sistemagerenciamento.controller;

import br.com.sistemagerenciamento.dto.impediment.CreateImpedimentDTO;
import br.com.sistemagerenciamento.dto.impediment.ImpedimentDTO;
import br.com.sistemagerenciamento.service.ImpedimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/impediments")
public class ImpedimentController {

    private final ImpedimentService impedimentService;

    @Autowired
    public ImpedimentController(ImpedimentService impedimentService) {
        this.impedimentService = impedimentService;
    }

    @GetMapping
    public ResponseEntity<List<ImpedimentDTO>> getAllImpediments() {
        List<ImpedimentDTO> impediments = impedimentService.getAllImpediments();
        return ResponseEntity.ok(impediments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImpedimentDTO> getImpedimentById(@PathVariable Long id) {
        ImpedimentDTO impediment = impedimentService.getImpedimentById(id);
        return ResponseEntity.ok(impediment);
    }

    @PostMapping
    public ResponseEntity<ImpedimentDTO> createImpediment(@RequestBody CreateImpedimentDTO createImpedimentDTO) {
        ImpedimentDTO createdImpediment = impedimentService.createImpediment(createImpedimentDTO);
        return ResponseEntity.ok(createdImpediment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImpediment(@PathVariable Long id) {
        impedimentService.deleteImpediment(id);
        return ResponseEntity.noContent().build();
    }
}
