/*
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
package es.ujaen.dae.restClient.controllers;

import es.ujaen.dae.restClient.dtos.MensajeDTO;
import es.ujaen.dae.restClient.dtos.PeticionAmistadDTO;
import es.ujaen.dae.restClient.dtos.UsuarioDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author David Fernández Puentes
 */
@ManagedBean(name="sessionBB")
@SessionScoped
public class SessionBackingBean{
    //private static final Logger logger = LoggerFactory.getLogger(SessionBackingBean.class);
    private static final String URL = "http://localhost:8084/RedSocialWS/";
    
    private UsuarioDTO user;
    private boolean loggedIn;
    private List<MensajeDTO> wall;
    private MensajeDTO message;
    private String userToFind;
    private List<UsuarioDTO> usersFound;
    private boolean hasNotifications;
    private List<PeticionAmistadDTO> notifications;
    private RestTemplate template;
    
    public SessionBackingBean(){
        user = new UsuarioDTO();
        wall = new ArrayList();
        message = new MensajeDTO();
        loggedIn = false;
        usersFound = new ArrayList();
        hasNotifications = false;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        ClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory(httpClient);
        template = new RestTemplate(rf);
    }

    public UsuarioDTO getUser() {
        return user;
    }

    public void setUser(UsuarioDTO user) {
        this.user = user;
    }
    
    public List<MensajeDTO> getWall() {
        return wall;
    }

    public void setWall(List<MensajeDTO> wall) {
        this.wall = wall;
    }

    public MensajeDTO getMessage() {
        return message;
    }

    public void setMessage(MensajeDTO message) {
        this.message = message;
    }

    public String getUserToFind() {
        return userToFind;
    }

    public void setUserToFind(String userToFind) {
        this.userToFind = userToFind;
    }

    public List<UsuarioDTO> getUsersFound() {
        return usersFound;
    }

    public void setUsersFound(List<UsuarioDTO> usersFound) {
        this.usersFound = usersFound;
    }

    public List<PeticionAmistadDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<PeticionAmistadDTO> notifications) {
        this.notifications = notifications;
    }
    
    public boolean isLoggedIn(){
        return loggedIn;
    }

    public boolean isHasNotifications() {
        return hasNotifications;
    }
    
    
    
    public String login(){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user.getUser(), user.getPass()));
        ClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory(httpClient);
        template = new RestTemplate(rf);
        try{
            template.getForObject(URL+"usuarios/loginTest", String.class);
            loggedIn = true;
            return "wall?faces-redirect=true";
        }catch(HttpClientErrorException e){
            loggedIn = false;
            user.setPass("");
            return "loginError?faces-redirect=true";
        }
            
    }
    
    public String createAccount(){
        try{
            template.put(URL+"usuarios/nuevo", user);
            return login();
        }catch(HttpClientErrorException e){
            return "newAccountError?faces-redirect=true";
        }
    }
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "home.jsf?faces-redirect=true";
        
    }
    
    public String findUsers(){
        UsuarioDTO[] arrayU = template.getForObject(URL+"usuarios/buscarPorNombre/"+userToFind, UsuarioDTO[].class);
        if(arrayU != null)
            usersFound = Arrays.asList(arrayU);
        userToFind = null;
        return "foundUsers?faces-redirect=true";
    }
    
    public String loadWall(){
        if(isLoggedIn()){
            loadNotifications();
            MensajeDTO[] arrayM = template.getForObject(URL+"mensajes/muro", MensajeDTO[].class);
            if(arrayM == null)
                return "home?faces-redirect=true";
            wall = Arrays.asList(arrayM);
            return "";
        }else{
            return "home?faces-redirect=true";
        }
    }
    
    public String newMessage(){
        if(isLoggedIn()){
            try{
                template.put(URL+"mensajes/nuevo", message.getText());
                message.setText("");
                return "wall?faces-redirect=true";
            }catch(HttpClientErrorException e){
                logout();
            }
        }else{
            message.setText("");
            return "home?faces-redirect=true";
        }
        return "home?faces-redirect=true";
    }
    
    public String newComment(long id){
        if(isLoggedIn()){
            try{
                template.put(URL+"mensajes/comentar/"+id, message.getText());
                message.setText("");
                return "wall?faces-redirect=true";
            }catch(HttpClientErrorException e){
                logout();
            }
        }else{
            message.setText("");
            return "home?faces-redirect=true";
        }
        return "home?faces-redirect=true";
    }
        
    public String loadNotifications(){
        PeticionAmistadDTO arrayP[] = template.getForObject(URL+"peticiones/ver", PeticionAmistadDTO[].class);
        if(arrayP != null)
            notifications = Arrays.asList(arrayP);
        hasNotifications = notifications != null && !notifications.isEmpty();
        return "";
    }
    
    public String sendPetition(String u){
        try{
            template.put(URL+"peticiones/enviar/"+u, "");
        }catch(HttpClientErrorException e){
            logout();
        }
        return "";
    }
    
    public String acceptPetition(String u){
        try{
            template.postForObject(URL+"peticiones/aceptar/"+u, "", Object.class);
        }catch(HttpClientErrorException e){
            return logout();
        }
        if(notifications.size() == 1)
            return "wall?faces-redirect=true";
        else
            return "notifications?faces-redirect=true";
    }
    
    public String rejectPetition(String u){
        try{
            template.postForObject(URL+"peticiones/rechazar/"+u, "", Object.class);
        }catch(HttpClientErrorException e){
            return logout();
        }
        if(notifications.size() == 1)
            return "wall?faces-redirect=true";
        else
            return "notifications?faces-redirect=true";
    }
    
    public String likeMessage(long id, String targetUser){
        try{
            template.postForObject(URL+"mensajes/meGusta/"+id, "", Object.class);
        }catch(HttpClientErrorException e){
            return logout();
        }
        return "wall?faces-redirect=true";
    }
}
