package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.service;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.entity.CorsoStudente;
import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.repository.CorsoStudenteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class CorsoStudenteService {
    @Autowired
    private CorsoStudenteRepository corsoStudenteRepository;

    public CorsoStudente createCorsoStudente(@Valid CorsoStudente newCorsoStudente) {
        return corsoStudenteRepository.save(newCorsoStudente);
    }

    public List<CorsoStudente> getStudentiOfCorso(long corso_id) {
        return corsoStudenteRepository.findCorsoStudenteByCorsoId(corso_id);
    }

    public List<CorsoStudente> getCorsiOfStudente(long student_id) {
        return corsoStudenteRepository.findCorsoStudenteByStudenteId(student_id);
    }

}
