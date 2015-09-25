/*
 * Copyright 2014 alumno.
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
package es.ujaen.dae.redsocialws.dao;

import es.ujaen.dae.redsocialws.exceptions.ErrorActualizarMensaje;
import es.ujaen.dae.redsocialws.exceptions.ErrorBorrarMensaje;
import es.ujaen.dae.redsocialws.exceptions.ErrorInsertarMensaje;
import es.ujaen.dae.redsocialws.model.Mensaje;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author David Fernández Puentes
 */
@Repository
@Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
@Component
public class MensajeDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public MensajeDAO() {
    }
    
    
    @Cacheable("Mensajes")
    public Mensaje buscar(long id){
        return entityManager.find(Mensaje.class, id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=es.ujaen.dae.redsocialws.exceptions.ErrorInsertarMensaje.class)
    public void insertar(Mensaje mensaje){
        try{
            entityManager.persist(mensaje);
            entityManager.flush();
        }catch(Exception e){
            throw new ErrorInsertarMensaje();
        }
    }
    
    @CacheEvict(value = "Mensajes", key="#mensaje.getId()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=es.ujaen.dae.redsocialws.exceptions.ErrorActualizarMensaje.class)
    public void actualizar(Mensaje mensaje){
        try{
            entityManager.merge(mensaje);
        }catch(Exception e){
            throw new ErrorActualizarMensaje();
        }
    }
    
    @CacheEvict(value = "Mensajes", key="#mensaje.getId()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=es.ujaen.dae.redsocialws.exceptions.ErrorBorrarMensaje.class)
    public void borrar(Mensaje mensaje){
        try{
            entityManager.remove(mensaje);
        }catch(Exception e){
            throw new ErrorBorrarMensaje();
        }
    }
}
