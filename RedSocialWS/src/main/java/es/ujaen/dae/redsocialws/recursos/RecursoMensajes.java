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

import es.ujaen.dae.redsocialws.dtos.MensajeDTO;
import es.ujaen.dae.redsocialws.exceptions.ErrorActualizarMensaje;
import es.ujaen.dae.redsocialws.exceptions.ErrorInsertarMensaje;
import es.ujaen.dae.redsocialws.exceptions.ErrorMensajeInexistente;
import es.ujaen.dae.redsocialws.exceptions.ErrorUsuarioIncorrecto;
import es.ujaen.dae.redsocialws.exceptions.ErrorUsuarioNoExiste;
import es.ujaen.dae.redsocialws.model.Gestor;
import es.ujaen.dae.redsocialws.model.Mensaje;
import es.ujaen.dae.redsocialws.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/mensajes")
public class RecursoMensajes {
    @Autowired
    private Gestor gestor;
    
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({ErrorUsuarioNoExiste.class})
    public void handlerParametroIncorrecto(){}
    
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({ErrorActualizarMensaje.class})
    public void handlerMensajeIncorrecto(){}
    
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ErrorInsertarMensaje.class})
    public void handlerMensajeExistente(){}
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ErrorMensajeInexistente.class})
    public void handlerMensajeInexistente(){}
    
    /**
     *
     * @param idMensaje
     */
    @RequestMapping(value="/meGusta/{id}", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void MeGusta(@PathVariable(value = "id") long idMensaje) {
        // TODO - implement InterfazBean.MeGusta
        String username = RecursoUsuarios.getUsuarioAutentificado();
        gestor.meGustaMensaje(idMensaje, username);
    }

    /**
     *
     * @param text
     */
    @RequestMapping(value="/nuevo", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void EscribirMensaje(@RequestBody String text) {
        String username = RecursoUsuarios.getUsuarioAutentificado();
        gestor.crearMensaje(text, username);
    }

    /**
     *
     * @param idMensaje
     * @param text
     */
    @RequestMapping(value="/comentar/{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void EscribirComentario(@PathVariable(value = "id") long idMensaje,@RequestBody String text) {
        String username = RecursoUsuarios.getUsuarioAutentificado();
        gestor.addComentario(idMensaje, text, username);
    }
    /**
     * 
     * @return 
     */
    @RequestMapping(value="/muro", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<MensajeDTO> VerMuro() {
        String username = RecursoUsuarios.getUsuarioAutentificado();
        return gestor.VerMuro(username);
    }

}
