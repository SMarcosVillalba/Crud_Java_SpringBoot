package com.example.ejemplohitosegundotrimestreacceso.servicios;

import com.example.ejemplohitosegundotrimestreacceso.control.jpa.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Integer>{

    @Query("select count(t) as finalizadas from Tarea t where t.estado=3")
    public Integer finalizadas();

    @Query("select count(t) as cuenta from Tarea t where t.estado=?1")
    public Integer findByEstado(Integer estado);

    //borar por tarea
    @Modifying
    @Transactional
    @Query("delete from Tarea r where r.usuario.nif=:nif")

    void deleteByNif(@Param("nif") String nif);


    //mostra una tarea por nif
    @Query("select t from Tarea t where t.usuario.nif=:nif")
    public List<Tarea> findByNif(@Param("nif") String nif);

    // contar el numero de tareas
    @Query("select count(t) as cuenta from Tarea t")
    public Integer cuentaTareas();

    //tareas completadas
    @Query("select count(t) as completadas from Tarea t where t.estado=3")
    public Integer completadas();

    //tareas pendientes
    @Query("select count(t) as completadas from Tarea t where t.estado=2")
    public Integer pendientes();
    //promedio de tareas por usuario
    @Query("select avg(t) as promedio from Tarea t")
    public Double promedioTareas();

    @Query("SELECT AVG(t.cuenta) FROM (SELECT COUNT(t) AS cuenta FROM Tarea t GROUP BY t.usuario) t")
    Double promedio();


}






