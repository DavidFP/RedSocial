package es.ujaen.dae.restClient.dtos;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MensajeDTO{
 
    private  long id;
    private  Date date;
    private  String text;
    private  String user;
    private  List<MensajeDTO> comentarios;
    private  Map<String, UsuarioDTO> mapaUsersMeGusta;
    
    public MensajeDTO(long id, String text, String user, Date date, List<MensajeDTO> comentarios, Map<String, UsuarioDTO> mapaUsersMeGusta)  {
        this.id = id;
        this.user = user;
        this.text = text;
        this.date = (Date)date.clone();
        this.comentarios = comentarios;
        this.mapaUsersMeGusta = mapaUsersMeGusta;
    }

    public MensajeDTO(long id, String text, String user, Date date){
        this.id = id;
        this.user = user;
        this.text = text;
        this.date = (Date)date.clone();
        comentarios = null;
        mapaUsersMeGusta = null;
    }
    
    public MensajeDTO(){
        
    }
    
    public long getId(){
        return id;
    }
    
    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getUser() {
        return user;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    public List<MensajeDTO> getComentarios() {
        return comentarios;
    }

    public Map<String, UsuarioDTO> getMapaUsersMeGusta() {
        return mapaUsersMeGusta;
    }
    
    
    public static Comparator<MensajeDTO> ComparadorMensajeAscendente  = new Comparator<MensajeDTO>() {
        @Override
        public int compare(MensajeDTO m1, MensajeDTO m2) {
            if (m1.date.before(m2.date)) {
                return -1;
            } else if (m1.date.after(m2.date)) {
                return 1;
            } else {
                return 0;
            }
        }
    };
    
    public static Comparator<MensajeDTO> ComparadorMensajeDescendente  = new Comparator<MensajeDTO>() {
        @Override
        public int compare(MensajeDTO m1, MensajeDTO m2) {
            if (m1.date.before(m2.date)) {
                return 1;
            } else if (m1.date.after(m2.date)) {
                return -1;
            } else {
                return 0;
            }
        }
    };
}
