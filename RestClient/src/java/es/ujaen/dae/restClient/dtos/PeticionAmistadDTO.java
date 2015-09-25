package es.ujaen.dae.restClient.dtos;

import java.util.Date;

public class PeticionAmistadDTO {

    private String  userFrom;
    private String userTo;
    private Date date;

    public PeticionAmistadDTO(){
    }
    public PeticionAmistadDTO(String userFrom, String userTo, Date date) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.date = date;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public Date getDate() {
        return date;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}