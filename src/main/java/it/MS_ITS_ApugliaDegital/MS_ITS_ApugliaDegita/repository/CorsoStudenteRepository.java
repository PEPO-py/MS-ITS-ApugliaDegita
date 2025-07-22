package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.repository;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.entity.CorsoStudente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorsoStudenteRepository extends JpaRepository<CorsoStudente, Long> {
    List<CorsoStudente> findCorsoStudenteByCorsoId(long corsoId);

    List<CorsoStudente> findCorsoStudenteByStudenteId(long studenteId);
}
