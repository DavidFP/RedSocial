
package es.ujaen.dae.redsocialws.model;

import es.ujaen.dae.localidadescercanas.Ciudad;
import es.ujaen.dae.localidadescercanas.CiudadInexistente_Exception;
import es.ujaen.dae.localidadescercanas.LocalidadesCercanas;
import es.ujaen.dae.localidadescercanas.LocalidadesCercanasService;
import es.ujaen.dae.redsocialws.dao.MensajeDAO;
import es.ujaen.dae.redsocialws.dao.PeticionAmistadDAO;
import es.ujaen.dae.redsocialws.dao.UsuarioDAO;
import es.ujaen.dae.redsocialws.dtos.MensajeDTO;
import es.ujaen.dae.redsocialws.dtos.UsuarioDTO;
import es.ujaen.dae.redsocialws.exceptions.ErrorMensajeInexistente;
import es.ujaen.dae.redsocialws.exceptions.ErrorUsuarioNoExiste;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Gestor {

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private PeticionAmistadDAO peticionAmistadDAO;
    @Autowired
    private MensajeDAO mensajeDAO;

    
    public Gestor(){
    }

    /**
     *
     * @param user
     * @param pass
     * @param loc
     */
    public void Crear(String user, String pass, String loc) {
        usuarioDAO.insertar(new Usuario(user, pass, loc));
    }

    /**
     *
     * @param user
     * @return Usuario
     */
    public Usuario BuscarPorNombre(String user) {
        return usuarioDAO.buscar(user);
    }
    
    public Usuario BuscarPorUUID(String uuid){
        return usuarioDAO.buscarPorUUID(uuid);
    }
    public List<UsuarioDTO> BuscarUsuarios(String user, String loc) {
        LocalidadesCercanasService lcs = new LocalidadesCercanasService();
        LocalidadesCercanas localidadesCercanasProxy = lcs.getLocalidadesCercanasPort();
        
        List<Usuario> usuarios = usuarioDAO.buscarVarios(user);
        List<UsuarioDTO> lista = new ArrayList();
        List<String> ciudades = new ArrayList();
        for(Usuario u : usuarios){
            lista.add(u.getDTO());
            ciudades.add(u.getLoc());
        }
        if(loc.isEmpty()) //Usuario no logueado que busca gente.
            return lista;
        List<UsuarioDTO> listaOrdenada = new ArrayList();
        try{
            List<Ciudad> ciudadesOrdenadas = localidadesCercanasProxy.ciudadesPorDistancia(loc, ciudades);
            for(Ciudad c : ciudadesOrdenadas){
                for(Usuario u : usuarios){
                    if(u.getLoc().toUpperCase().equals(c.getNombre().toUpperCase()))
                        listaOrdenada.add(u.getDTO());
                }
            }
        }catch(CiudadInexistente_Exception e){
            //Devolver la lista sin ordenar si la ciudad no existe
            return lista;
        }
        return listaOrdenada;
    }
 
    // convert from internal Java String format -> UTF-8
    private String convertToUTF8(String s) {
        String out;
        try {
            out = new String(s.getBytes("UTF-8"),"ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
    
    public void addPeticionAmistad(String userFrom, String userTo) {
        Date d = Calendar.getInstance().getTime();
        PeticionAmistad peticion = new PeticionAmistad(userFrom, userTo, d);
        peticionAmistadDAO.insertar(peticion);
    }

    public List<PeticionAmistad> getPeticionesAmistad(String user) {
        return peticionAmistadDAO.buscarPorUser(user);
    }

    public void aceptarPeticionAmistad(String yourself, String user){
        Usuario uYourself = BuscarPorNombre(yourself);
        Usuario uUser = BuscarPorNombre(user);
        if(uUser == null)
            throw new ErrorUsuarioNoExiste();
        uYourself.addAmigo(uUser);
        uUser.addAmigo(uYourself);
        eliminarPeticionAmistad(yourself, user);
        usuarioDAO.actualizar(uYourself);
        usuarioDAO.actualizar(uUser);
    }
    public void eliminarPeticionAmistad(String yourself, String user) {
        peticionAmistadDAO.borrarPorUser(user, yourself);
    }
    
    public List<String> BuscarPorLocalizacion(String loc){
        List<Usuario> usuarios = usuarioDAO.buscarPorLocalizacion(loc);
        List<String> lista = new ArrayList();
        for(Usuario u : usuarios)
            lista.add(u.getUsername());
        return lista;
    }
    
    public void crearMensaje(String text, String user){
        Usuario u = usuarioDAO.buscar(user);
        if(u == null)
            throw new ErrorUsuarioNoExiste();
        Mensaje m = new Mensaje(text,user,false);
        mensajeDAO.insertar(m);
        u.crearMensaje(m);
        usuarioDAO.actualizar(u);
    }
    
    public List<Mensaje> buscarMensajes(String user){
        return usuarioDAO.buscar(user).getMensajes();
    }
    
    public void addComentario(long id, String text, String yourself){
        Usuario u = usuarioDAO.buscar(yourself);
        if(u == null)
            throw new ErrorUsuarioNoExiste();
        Mensaje comm = new Mensaje(text, yourself, true);
        Mensaje m = mensajeDAO.buscar(id);
        if(m == null)
            throw new ErrorMensajeInexistente();
        mensajeDAO.insertar(comm);
        m.AddComentario(comm);
        mensajeDAO.actualizar(m);
    }
    
    public void meGustaMensaje(long id, String yourself){
        Usuario u = usuarioDAO.buscar(yourself);
        if(u == null)
            throw new ErrorUsuarioNoExiste();
        Mensaje m = mensajeDAO.buscar(id);
        if(m == null)
            throw new ErrorMensajeInexistente();
        m.MeGusta(u);
        mensajeDAO.actualizar(m);
    }
    
     public List<MensajeDTO> VerMuro(String username) {
        List<MensajeDTO> muro = new ArrayList();
        Usuario u = usuarioDAO.buscar(username);
        for (Usuario amigo : u.getAmigos()) {
            for(Mensaje m : amigo.getMensajes()){
                muro.add(m.getDTO());
            }
        }
        for(Mensaje m : u.getMensajes()){
            muro.add(m.getDTO());  
        }
        muro.sort(MensajeDTO.ComparadorMensajeDescendente);
        return muro;
    }
}
