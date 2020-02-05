package com.scalefocus.cvmanager.repository;

import com.scalefocus.cvmanager.model.biography.Biography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface which only extends the {@link JpaRepository}.
 *
 * @author Mariyan Topalov
 */
@Repository
public interface BiographyRepository extends JpaRepository<Biography, Long> {

}
