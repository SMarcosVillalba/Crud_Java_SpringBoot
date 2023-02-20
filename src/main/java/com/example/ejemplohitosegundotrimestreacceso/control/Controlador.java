package com.example.ejemplohitosegundotrimestreacceso.control;

//IMPORTS NECESARIOS
import com.example.ejemplohitosegundotrimestreacceso.control.jpa.Role;
import com.example.ejemplohitosegundotrimestreacceso.control.jpa.Tarea;
import com.example.ejemplohitosegundotrimestreacceso.control.jpa.Usuario;
import com.example.ejemplohitosegundotrimestreacceso.servicios.RoleService;
import com.example.ejemplohitosegundotrimestreacceso.servicios.TareaRepository;
import com.example.ejemplohitosegundotrimestreacceso.servicios.TareaService;
import com.example.ejemplohitosegundotrimestreacceso.servicios.UsuarioService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;


//INDICAMOS QUE ES TIPO CONTROLADOR CON ESTA ANOTACION

@Controller
public class Controlador {
   @Autowired
    PasswordEncoder encoder;
   @Autowired
    UsuarioService usuarios;
   @Autowired
    RoleService roles;

    @Autowired
    TareaService tareas;


    @RequestMapping("/") // AHORA LE DECIMOS LO QUE VA A MOSTRAR EN RAIZ CUANDO EL USUARIO VA A  / EN EL PROYECTO

    public ModelAndView peticionRaiz(Authentication aut){
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());

        mv.setViewName("index");
        String texto = "123";
        String encriptado = encoder.encode(texto);
        System.out.println("Texto original: "+texto);
        System.out.println("Texto emcriptado: "+encriptado);

        return mv;

    }

    //para nuestro formulario de login
    @RequestMapping("login")
    public ModelAndView peticionSesion(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping("/user")
    public ModelAndView peticionUsuario(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());

        Optional<Usuario> userOptional = usuarios.buscarUsuario(aut.getName());
        Usuario user=null;
        if(userOptional.isPresent()){
            user = userOptional.get();
        }
        mv.addObject("user", user);
        mv.setViewName("usuario");
        return mv;
    }

    @RequestMapping("/admin")
    public ModelAndView peticionAdmin(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());

        List<Usuario> listaUsuarios = usuarios.listaUsuarios();

        for (Usuario user : listaUsuarios) {

                System.out.println(user.getNif()+""+user.getNombre());
                mv.addObject("listaUsuarios", listaUsuarios);

        }
        mv.setViewName("administrador");
        return mv;
    }

    //listar tareas

    @RequestMapping("/user/tareas")
    public ModelAndView peticionUserTareas(Authentication aut,HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());



        List<Tarea> listaTareas = tareas.listarTareasPorNif(aut.getName());

        for (Tarea user : listaTareas) {

            System.out.println(user.getNombre()+user.getDescripcion()+user.getEstado());
            mv.addObject("listaTareas", listaTareas);

        }
        mv.setViewName("listadotareas");
        return mv;
    }

    //mostrar nombre apellido y estado y activo del usuario en perfil

    @RequestMapping("/user/perfil")
    public ModelAndView peticionUserPerfil(Authentication aut,HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());

        Optional<Usuario> userOptional = usuarios.buscarUsuario(aut.getName());
        Usuario user=null;
        if(userOptional.isPresent()){
            user = userOptional.get();
        }
        mv.addObject("userNombre", user.getNombre());
        mv.addObject("userApellidos", user.getApellidos());
        mv.addObject("userActivo", user.getActivo());
        mv.setViewName("perfil");
        return mv;
    }



    @RequestMapping("/admin/usuario/nuevousuario")
    public ModelAndView registro() {
        ModelAndView mv = new ModelAndView();
        Usuario c = new Usuario();
        mv.addObject("usuario", c);
        mv.setViewName("nuevousuario");
        return mv;
    }

    @RequestMapping("/guardar")
    public ModelAndView peticionGuardar(Usuario u, Authentication aut){
        ModelAndView mv = new ModelAndView();
        System.out.println(u);
        //para cifrar la contraseña
        String sinCifrar = u.getPw();
        String cifrado = encoder.encode(sinCifrar);
        u.setPw(cifrado);
        //guardamos el usuario
        Optional<Usuario> usuarioBuscado = usuarios.buscarUsuario(u.getNif());

        if(usuarioBuscado.isPresent()) {
            mv.addObject("sms", "El nif " + u.getNif() + " ya está utilizado");
        }else{
            usuarios.guardarUsuario(u);
            Role rol = new Role();
            rol.setUsuario(u);
            rol.setRol("USUARIO");
            roles.guardarRol(rol);

            mv.addObject("sms", "El usuario " + u.getNombre() +" con NIF "+u.getNif() + " se ha registrado correctamente");
        }
        mv.setViewName("informa");
        return mv;
    }


    @RequestMapping("/denegado")
    public ModelAndView peticionDenegado(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.setViewName("/denegado");
        return mv;
    }


    @RequestMapping("/user/tareas/nueva")
    public ModelAndView peticioNuevaTarea(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.setViewName("nuevatarea");
        return mv;
    }
    @RequestMapping("/user/tareas/listadotareas")
    public ModelAndView peticioListdoTareas(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.setViewName("listadotareas");
        return mv;
    }

    @RequestMapping("/admin/dashboard")
    public ModelAndView peticioDashboard(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        usuarios.numeroUsuarios();
        mv.addObject("numUsuarios", usuarios.numeroUsuarios());
        mv.addObject("numTareas", tareas.cuentaTareas());
        mv.addObject("numTareasCompletadas", tareas.tareasCompletadas());
        mv.addObject("numTareaspendientes", tareas.tareaspendientes());
        Double promedioTareas = tareas.promedioTareas();
        mv.addObject("promedioTareas", promedioTareas);

        // mv.addObject("numpromediotareas", tareas.promedioTareas());
        mv.setViewName("dashboard");
        return mv;
    }

    @RequestMapping("/admin/usuario/mostrar")
    public ModelAndView peticioUsuariosMostrar(Authentication aut) {
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.setViewName("mostrarusuarios");
        return mv;
    }

    @RequestMapping("/admin/usuario/editar")
    public ModelAndView peticioUsuariosEditar(Authentication aut, HttpServletRequest request) {
        String nif = request.getParameter("nif");
        Optional<Usuario> usuarioDpt = usuarios.buscarUsuario(nif);
        Usuario user = usuarioDpt.get();
        ModelAndView mv = new ModelAndView();
        if(aut==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", aut.getName());
        mv.addObject("usuario", user);

        mv.setViewName("editarusuarios");
        return mv;
    }



    @RequestMapping("/actualizar")

    public String peticionActualizar(Usuario u, Authentication aut){
        usuarios.guardarUsuario(u);
        return "redirect:/admin";
    }

    @RequestMapping("/admin/usuario/borrarUsuario")
    public String peticionBorrarUsuario(HttpServletRequest request){
        String nif = request.getParameter("nif");
        roles.deleteByNif(nif);
        tareas.deletetareaByNif(nif);
        usuarios.eliminarUsuario(nif);
        return "redirect:/admin";
    }



} // CIERRA CLASE CONTROLADOR
