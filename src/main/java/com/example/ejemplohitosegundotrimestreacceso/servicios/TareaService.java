package com.example.ejemplohitosegundotrimestreacceso.servicios;

import com.example.ejemplohitosegundotrimestreacceso.control.jpa.Tarea;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.lang.constant.Constable;
import java.util.List;
import java.util.Optional;

@Service
@ApplicationScope
public class TareaService {

    private TareaRepository tareas;

    public TareaService(TareaRepository tareas) {
     this.tareas = tareas;
    }

    public List<Tarea> ListaTareas(){
        return tareas.findAll();
    }


    public Integer tareasfinalizadas(){
        return tareas.finalizadas();
    }

    public Integer cuentaTareas(Integer estado){
        return tareas.findByEstado(estado);
    }


  public void deletetareaByNif(String nif){

      tareas.deleteByNif(nif);

  }

    public List<Tarea> listarTareasPorNif(String nif) {
        return tareas.findByNif(nif);
    }


    public Integer cuentaTareas(){
        return tareas.cuentaTareas();
    }

    public Integer tareasCompletadas(){
        return tareas.completadas();
    }
    public Integer tareaspendientes(){
        return tareas.pendientes();
    }


    public Double promedioTareas() {
        return tareas.promedio();
    }


}
