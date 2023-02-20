package com.example.ejemplohitosegundotrimestreacceso.servicios;

import com.example.ejemplohitosegundotrimestreacceso.control.jpa.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepositorio  extends JpaRepository<Usuario, String> {


    @Query("delete from Usuario r where r.nif=:nif")

    void deleteByNifUsuario(@Param("nif") String nif);

    //query numero de usuarios
    @Query("select count(*) from Usuario r")
    int numeroUsuarios();



}
