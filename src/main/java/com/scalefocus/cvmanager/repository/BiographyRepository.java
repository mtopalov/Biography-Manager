package com.scalefocus.cvmanager.repository;

import com.scalefocus.cvmanager.model.Biography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mariyan Topalov
 */
@Repository
public interface BiographyRepository extends JpaRepository<Biography, Long> {

}
