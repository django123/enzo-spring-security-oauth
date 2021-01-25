package com.enzo.repository;

import com.enzo.entities.Compte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompteRepository extends JpaRepository<Compte, String> {

    @Query("SELECT c FROM Compte c WHERE id = :code")
    public Compte findByCode(@Param("code") String code);

    /** get compte if have or not params **/
    @Query("SELECT c FROM Compte c WHERE SocialReason like CONCAT('%', :param, '%')")
    public Page<Compte> getComptesWithParam(@Param("param") String param, Pageable page);


}
