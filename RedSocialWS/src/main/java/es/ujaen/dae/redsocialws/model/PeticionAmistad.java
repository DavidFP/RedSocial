package es.ujaen.dae.redsocialws.model;

import es.ujaen.dae.redsocialws.dtos.PeticionAmistadDTO;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PeticionAmistad{

    @Id
    @org.hibernate.annotations.GenericGenerator(name="peticion-id", strategy = "hilo")
    @GeneratedValue(generator = "peticion-id")
    private long id;
    private String userFrom;
    private String userTo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public PeticionAmistad() {
    }

    public PeticionAmistad(String userFrom, String userTo, Date date) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.creationDate = date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date date) {
        this.creationDate = date;
    }
    
    public PeticionAmistadDTO getDTO(){
        return new PeticionAmistadDTO(userFrom, userTo, (Date) creationDate.clone());
    }
}