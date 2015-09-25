package es.ujaen.dae.redsocialws.dtos;


public class UsuarioDTO  {

    private String user;
    private String pass;
    private String loc;

    public UsuarioDTO(String user, String pass, String loc) {
        this.user = user;
        this.pass = pass;
        this.loc = loc;
    }
    
    public UsuarioDTO(){
        
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user){
        this.user = user;
    }
    
    public String getPass(){
        return pass;
    }
    
    public void setPass(String pass){
        this.pass = pass;
    }
    
    public String getLoc() {
        return loc;
    }
    
    public void setLoc(String loc){
        this.loc = loc;
    }
}
