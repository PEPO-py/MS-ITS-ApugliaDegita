package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.repository;

import it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.entity.Esame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EsameRepository extends JpaRepository<Esame, Long> {
}
