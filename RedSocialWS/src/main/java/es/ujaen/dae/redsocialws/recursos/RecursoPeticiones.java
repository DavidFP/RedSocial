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

import es.ujaen.dae.redsocialws.dtos.PeticionAmistadDTO;
import es.ujaen.dae.redsocialws.exceptions.ErrorActualizarPeticion;
import es.ujaen.dae.redsocialws.exceptions.ErrorBorrarPeticion;
import es.ujaen.dae.redsocialws.exceptions.ErrorInsertarPeticion;
import es.ujaen.dae.redsocialws.exceptions.ErrorUsuarioIncorrecto;
import es.ujaen.dae.redsocialws.exceptions.ErrorUsuarioNoExiste;
import es.ujaen.dae.redsocialws.model.Gestor;
import es.ujaen.dae.redsocialws.model.PeticionAmistad;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/peticiones")
public class RecursoPeticiones {
    @Autowired
    private Gestor gestor;
    
    
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ErrorInsertarPeticion.class})
    public void handlerErrorNuevaPeticion(){}
    
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ErrorBorrarPeticion.class})
    public void handlerErrorBorrarPeticion(){}
    
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ErrorUsuarioNoExiste.class})
    public void handlerErrorUsuarioParametro(){}
    /**
     *
     * @param userTo
     */
    @RequestMapping(value="/enviar/{userTo}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void EnviarPeticionAmistad(@PathVariable(value="userTo") String userTo) {
        String username = RecursoUsuarios.getUsuarioAutentificado();
        gestor.addPeticionAmistad(username, userTo);
    }

    @RequestMapping(value="/ver", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PeticionAmistadDTO> VerPeticionesAmistad() {
        String username = RecursoUsuarios.getUsuarioAutentificado();
        List<PeticionAmistad> peticiones = gestor.getPeticionesAmistad(username);
        if(peticiones == null)
            return null;
        else{
            List<PeticionAmistadDTO> peticionesDTO = new ArrayList();
            for(PeticionAmistad pa : peticiones){
                peticionesDTO.add(pa.getDTO());
            }
            return peticionesDTO;
        }
    }

    /**
     *
     * @param user
     */
    @RequestMapping(value="/aceptar/{user}", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void AceptarPeticionAmistad(@PathVariable(value="user") String user) {
        String username = RecursoUsuarios.getUsuarioAutentificado();
        gestor.aceptarPeticionAmistad(username, user);
    }

    /**
     *
     * @param user
     */
    @RequestMapping(value="/rechazar/{user}", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void RechazarPeticonAmistad(@PathVariable(value="user") String user) {
        String username = RecursoUsuarios.getUsuarioAutentificado();
        gestor.eliminarPeticionAmistad(username, user);
    }
    
}
