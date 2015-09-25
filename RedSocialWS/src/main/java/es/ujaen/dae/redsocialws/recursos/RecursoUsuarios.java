/*
 * Copyright 2014 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.ujaen.dae.redsocialws.recursos;

import es.ujaen.dae.redsocialws.dtos.UsuarioDTO;
import es.ujaen.dae.redsocialws.exceptions.ErrorInsertarUsuario;
import es.ujaen.dae.redsocialws.exceptions.ErrorUsuarioIncorrecto;
import es.ujaen.dae.redsocialws.model.Gestor;
import es.ujaen.dae.redsocialws.model.Usuario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author David Fernández Puentes
 */
@Controller
@Component
@RequestMapping("/usuarios")
public class RecursoUsuarios {
    @Autowired
    private Gestor gestor;
    
    
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({ErrorUsuarioIncorrecto.class})
    public void handlerParametroIncorrecto(){}
    
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ErrorInsertarUsuario.class})
    public void handlerRecursoExistente(){}
    /**
     *
     * @param nuevo
     */
    @RequestMapping(value="/nuevo", method=RequestMethod.PUT, consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void CrearCuenta(@RequestBody UsuarioDTO nuevo) {
        if(nuevo == null)
            throw new ErrorUsuarioIncorrecto();
        gestor.Crear(nuevo.getUser(), nuevo.getPass(), nuevo.getLoc());
    }
    
    @RequestMapping(value="/amigos", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<UsuarioDTO> obtenerListaAmigosDeUsuario(){
        String username = getUsuarioAutentificado();
        if(!username.equals("anonimo")){
            Usuario user = gestor.BuscarPorNombre(username);
            if(user != null){
                List<UsuarioDTO> lista = new ArrayList();
                for(Usuario u : user.getAmigos()){
                    lista.add(u.getDTO());
                }
                return Collections.unmodifiableList(lista);
            }else
                throw new ErrorUsuarioIncorrecto();
        }
        else
            throw new ErrorUsuarioIncorrecto();
    }
    
    /**
     *
     * @param userToFind la cadena del usuario que buscas
     * @return
     */
    @RequestMapping(value="/buscarPorNombre/{userToFind}", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<UsuarioDTO> BuscarUsuarioPorNombre(@PathVariable(value="userToFind") String userToFind){
        String username = getUsuarioAutentificado();
        if(username.equals("anonimo")){
            return gestor.BuscarUsuarios(userToFind, "");
        }else{
            Usuario u = gestor.BuscarPorNombre(username);
            if(u != null)
                return gestor.BuscarUsuarios(userToFind, u.getLoc());
            else
                throw new ErrorUsuarioIncorrecto();
        }
    } 
      /**
     *
     * @param loc
     * @return
     */
    @RequestMapping(value="/buscarPorLocalizacion/{loc}", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<String> BuscarUsuariosPorLocalizacion(@PathVariable String loc) {
        // TODO - implement InterfazBean.BuscarUsuario
        return gestor.BuscarPorLocalizacion(loc);
    }
    
    
    @RequestMapping(value="/loginTest", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String loginTest(){
        return "correcto";
    }
    
    public static String getUsuarioAutentificado(){
        String usuario;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            usuario = "anonimo"; 
        } else {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            usuario = userDetails.getUsername();
        }
        return usuario;
    }
}
