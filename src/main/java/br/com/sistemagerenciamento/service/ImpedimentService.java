package br.com.sistemagerenciamento.service;

import br.com.sistemagerenciamento.domain.Impediment;
import br.com.sistemagerenciamento.domain.Task;
import br.com.sistemagerenciamento.domain.User;
import br.com.sistemagerenciamento.dto.impediment.CreateImpedimentDTO;
import br.com.sistemagerenciamento.dto.impediment.ImpedimentDTO;
import br.com.sistemagerenciamento.repository.ImpedimentRepository;
import br.com.sistemagerenciamento.repository.TaskRepository;
import br.com.sistemagerenciamento.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImpedimentService {

    private final ImpedimentRepository impedimentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public ImpedimentService(ImpedimentRepository impedimentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.impedimentRepository = impedimentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<ImpedimentDTO> getAllImpediments() {
        return impedimentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ImpedimentDTO getImpedimentById(Long id) {
        Optional<Impediment> impediment = impedimentRepository.findById(id);
        return impediment.map(this::convertToDTO).orElse(null);
    }

    public ImpedimentDTO createImpediment(CreateImpedimentDTO createImpedimentDTO) {
        Task task = taskRepository.findById(createImpedimentDTO.taskId()).orElseThrow();
        User reportedBy = userRepository.findById(createImpedimentDTO.reportedById()).orElseThrow();

        Impediment impediment = new Impediment();
        impediment.setTask(task);
        impediment.setDescription(createImpedimentDTO.description());
        impediment.setReportedBy(reportedBy);

        Impediment savedImpediment = impedimentRepository.save(impediment);
        return convertToDTO(savedImpediment);
    }

    public void deleteImpediment(Long id) {
        impedimentRepository.deleteById(id);
    }

    private ImpedimentDTO convertToDTO(Impediment impediment) {
        return new ImpedimentDTO(
                impediment.getImpedimentId(),
                impediment.getTask().getTaskId(),
                impediment.getDescription(),
                impediment.getReportedBy().getUserId()
        );
    }
}
