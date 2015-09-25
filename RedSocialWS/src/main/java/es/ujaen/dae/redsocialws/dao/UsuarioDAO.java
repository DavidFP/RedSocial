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

import es.ujaen.dae.redsocialws.exceptions.ErrorActualizarUsuario;
import es.ujaen.dae.redsocialws.exceptions.ErrorBorrarUsuario;
import es.ujaen.dae.redsocialws.exceptions.ErrorInsertarUsuario;
import es.ujaen.dae.redsocialws.model.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
public class UsuarioDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public UsuarioDAO() {
    }
    
    
    public Usuario buscar(String user){
        return entityManager.find(Usuario.class, user);
    }
    
    public Usuario buscarPorUUID(String uuid){
        return entityManager.createQuery(" SELECT u FROM Usuario u WHERE u.uuid = ?1", 
                Usuario.class).setParameter(1, uuid).getSingleResult();
    }
    
    public List<Usuario> buscarVarios(String user){
        List<Usuario> lista = entityManager.createQuery(
                "SELECT u FROM Usuario u WHERE UPPER(u.username) LIKE ?1",
                Usuario.class
        ).setParameter(1, "%"+user.toUpperCase()+"%").getResultList();
        return lista;
    }
    
    public List<Usuario> buscarPorLocalizacion(String loc){
        List<Usuario> lista = entityManager.createQuery(
                "select u from Usuario u where upper(u.loc) like ?1",
                Usuario.class
        ).setParameter(1, "%"+loc.toUpperCase()+"%").getResultList();
        return lista;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=es.ujaen.dae.redsocialws.exceptions.ErrorInsertarUsuario.class)
    public void insertar(Usuario usuario){
        try{
            entityManager.persist(usuario);
            entityManager.flush();
        }catch(Exception e){
            throw new ErrorInsertarUsuario();
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=es.ujaen.dae.redsocialws.exceptions.ErrorActualizarUsuario.class)
    public void actualizar(Usuario usuario){
        try{
            entityManager.merge(usuario);
        }catch(Exception e){
            throw new ErrorActualizarUsuario();
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor=es.ujaen.dae.redsocialws.exceptions.ErrorBorrarUsuario.class)
    public void borrar(Usuario usuario){
        try{
            entityManager.remove(usuario);
        }catch(Exception e){
            throw new ErrorBorrarUsuario();
        }
    }
}
