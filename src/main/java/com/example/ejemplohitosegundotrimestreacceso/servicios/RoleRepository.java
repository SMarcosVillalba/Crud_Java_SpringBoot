package com.example.ejemplohitosegundotrimestreacceso.servicios;

import com.example.ejemplohitosegundotrimestreacceso.control.jpa.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends JpaRepository<Role, Integer>{

//query eliminar roles
@Modifying
@Transactional
@Query("delete from Role r where r.usuario.nif=:nif")
void deleteByNif(@Param("nif") String nif);

}



