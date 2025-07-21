package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.repository;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.entity.Corso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorsoRepository extends JpaRepository<Corso, Long> {

    Optional<Corso> getCorsoById(long id);

}
