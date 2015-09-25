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

import es.ujaen.dae.redsocialws.exceptions.ErrorActualizarPeticion;
import es.ujaen.dae.redsocialws.exceptions.ErrorBorrarPeticion;
import es.ujaen.dae.redsocialws.exceptions.ErrorInsertarPeticion;
import es.ujaen.dae.redsocialws.model.PeticionAmistad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaDelete;
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
public class PeticionAmistadDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public PeticionAmistadDAO() {
    }
    
    @Cacheable("PeticionesAmistad")
    public PeticionAmistad buscar(long id){
        return entityManager.find(PeticionAmistad.class, id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=es.ujaen.dae.redsocialws.exceptions.ErrorInsertarPeticion.class)
    public void insertar(PeticionAmistad peticion){
        try{
            entityManager.persist(peticion);
            entityManager.flush();
        }catch(Exception e){
            throw new ErrorInsertarPeticion();
        }
    }
    
    @CacheEvict(value = "PeticionesAmistad", key="#peticion.getId()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=es.ujaen.dae.redsocialws.exceptions.ErrorActualizarPeticion.class)
    public void actualizar(PeticionAmistad peticion){
        try{
            entityManager.merge(peticion);
        }catch(Exception e){
            throw new ErrorActualizarPeticion();
        }
    }
    
    @CacheEvict(value = "PeticionesAmistad", key="#peticion.getId()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=es.ujaen.dae.redsocialws.exceptions.ErrorBorrarPeticion.class)
    public void borrar(PeticionAmistad peticion){
        try{
            entityManager.remove(peticion);
        }catch(Exception e){
            throw new ErrorBorrarPeticion();
        }
    }
    
    public List<PeticionAmistad> buscarPorUser(String user){
            List<PeticionAmistad> lista = entityManager.createQuery(
                    "select p from PeticionAmistad p where p.userTo = ?1",
                    PeticionAmistad.class
            ).setParameter(1, user).getResultList();
            return lista;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=es.ujaen.dae.redsocialws.exceptions.ErrorBorrarPeticion.class)
    public void borrarPorUser(String userFrom, String userTo){
        try{
            CriteriaDelete d;
            entityManager.createNativeQuery("DELETE FROM PeticionAmistad p WHERE p.userFrom = ?1 AND p.userTo = ?2", 
                    PeticionAmistad.class).setParameter(1, userFrom).setParameter(2, userTo).executeUpdate();
        }catch(Exception e){
            throw new ErrorBorrarPeticion();
        }
    }
}
