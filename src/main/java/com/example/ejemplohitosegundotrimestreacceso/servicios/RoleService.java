package com.example.ejemplohitosegundotrimestreacceso.servicios;



import com.example.ejemplohitosegundotrimestreacceso.control.jpa.Role;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Service
@ApplicationScope
public class RoleService {


    private RoleRepository roles;

    public RoleService(RoleRepository roles) {
        this.roles = roles;
    }

   public void guardarRol(Role rol){
        roles.save(rol);
    }

    public void deleteByNif(String nif){

        roles.deleteByNif(nif);

    }




}
