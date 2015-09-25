package es.ujaen.dae.redsocialws.model;

import es.ujaen.dae.redsocialws.dtos.UsuarioDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Usuario{

    @Id
    private String username;
    private String pass;
    private String rol;
    @ManyToMany(fetch=FetchType.LAZY)
    private List<Usuario> amigos;
    @OneToMany(fetch=FetchType.LAZY)
    private List<Mensaje> mensajes;
    private String loc;
    
    public Usuario() {
    }

    public Usuario(String user, String pass, String loc) {
        this.username = user;
        this.pass = pass;
        this.rol = "ROLE_USUARIO";
        amigos = new ArrayList();
        mensajes = new ArrayList();
        this.loc = loc;
    }

    public void crearMensaje(Mensaje m){
        mensajes.add(m);
    }
    
    public Mensaje verMensaje(int index){
        if(index < 0 || index > mensajes.size())
            return null;
        return mensajes.get(index);
    }
    
    public int getNumMensajes(){
        return mensajes.size();
    }
    
    public Mensaje addComentario(long id, Mensaje comentario){
        for(Mensaje m : mensajes){
            if(m.getId() == id){
                if(m.AddComentario(comentario))
                    return m;
                else
                    return null;
            }
        }
        return null;
    }    
    
    public Mensaje meGustaMensaje(long id, Usuario user){
        for(Mensaje m : mensajes){
            if(m.getId() == id){
                if(m.MeGusta(user))
                    return m;
                else
                    return null;
            }
        }
        return null;
    }
    
    public String getUsername() {
        return username;
    }

    public List<Usuario> getAmigos() {
        return amigos;
    }

    public void addAmigo(Usuario user) {
        amigos.add(user);
    }

    public void setUsername(String user) {
        this.username = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public void setAmigos(List<Usuario> amigos) {
        this.amigos = amigos;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public UsuarioDTO getDTO(){
        return new UsuarioDTO(username, "", loc);
    }
}
