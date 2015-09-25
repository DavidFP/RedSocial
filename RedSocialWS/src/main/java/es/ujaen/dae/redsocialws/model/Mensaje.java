package es.ujaen.dae.redsocialws.model;

import es.ujaen.dae.redsocialws.dtos.MensajeDTO;
import es.ujaen.dae.redsocialws.dtos.UsuarioDTO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Mensaje implements Comparable<Mensaje>{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String username;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    private String text;
    @OneToMany
    private List<Mensaje> comentarios;
    @ManyToMany
    private Map<String, Usuario> mapaUsersMeGusta;

    public Mensaje() {
    }
    public Mensaje(String text, String user, boolean comentario) {
        this.text = text;
        this.username = user;
        creationDate = Calendar.getInstance().getTime();
        if(!comentario){
            comentarios = new ArrayList();
            mapaUsersMeGusta = new TreeMap();
        }else{
            comentarios = null;
            mapaUsersMeGusta = null;
        }
            
    }
        
    public long getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }
    public List<Mensaje> getComentarios() {
        return comentarios;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String user) {
        this.username = user;
    }

    public void setComentarios(List<Mensaje> comentarios) {
        this.comentarios = comentarios;
    }

    public void setMapaUsersMeGusta(Map<String, Usuario> mapaUsersMeGusta) {
        this.mapaUsersMeGusta = mapaUsersMeGusta;
    }

    public Map<String, Usuario> getMapaUsersMeGusta() {
        return mapaUsersMeGusta;
    }

    /**
     *
     * @param comentario
     * @return boolean
     */
    public boolean AddComentario(Mensaje comentario) {
        return comentarios.add(comentario);
    }

    /**
     *
     * @param user
     * @return boolean
     */
    public boolean MeGusta(Usuario user) {
        if(mapaUsersMeGusta.containsKey(user.getUsername()))
            return false;
        mapaUsersMeGusta.put(user.getUsername(), user);
        return true;
    }

    public MensajeDTO getDTO(){
        if(this.comentarios == null || mapaUsersMeGusta == null){
            return new MensajeDTO(id, text, username, (Date) creationDate.clone());
        }else{
            List<MensajeDTO> dtoComentarios = new ArrayList();
            for(Mensaje m : comentarios){
                dtoComentarios.add(new MensajeDTO(m.id, m.text, m.username, (Date) m.creationDate.clone()));
            }
            dtoComentarios.sort(MensajeDTO.ComparadorMensajeAscendente);
            Map<String, UsuarioDTO> dtoMapaUsuarios = new TreeMap();
            for(Usuario u : mapaUsersMeGusta.values()){
                dtoMapaUsuarios.put(u.getUsername(), u.getDTO());
            }
            return new MensajeDTO(id, text, username, (Date)creationDate.clone(), dtoComentarios, dtoMapaUsuarios);
        }
    }
    
    @Override
    public int compareTo(Mensaje o) {
        return o.creationDate.compareTo(this.creationDate);
    }
}
