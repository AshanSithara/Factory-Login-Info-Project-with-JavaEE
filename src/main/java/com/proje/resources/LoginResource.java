package com.proje.resources;

import com.proje.dto.LoginDto;
import com.proje.dto.UserDto;
import com.proje.security.CustomAuthenticationProvider;
import com.proje.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/login")
public class LoginResource {

    @Autowired
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public LoginDto loginControl(){

        LoginDto loginDto = new LoginDto();
        //Custom providerdan o anki oturumdan kullanıcı adı çekiliyor ve dto'nun ilgili alanına set ediliyor.
        loginDto.setUsername(CustomAuthenticationProvider.getUsername());
        //Eğer oturum açılmamışsa null dönebileceğinden bu durum kontrol ediliyor.
        if (loginDto.getUsername() != null) {//eğer oturum açılmışsa
            UserDto userDto = userService.findUserWithUsername(loginDto.getUsername());//Username'e göre kullanıcı nesnesi dönüyor.
            //login nesnesinin ilgili alanlarına kullanıcı bilgileri set ediliyor.
            loginDto.setName(userDto.getName());
            loginDto.setRole(userDto.getRoles().getRoleName());
        }
        if (loginDto.getUsername() != null)//Kullanıcı giriş yaptıysa.
            loginDto.setStatus(true);

        else{
            loginDto.setStatus(false);//Kullanıcı giriş yapmadıysa login durumunu false yapıyoruz.
            loginDto.setErrorMessage("Giriş Yapılamadı");
        }

        return loginDto;
    }

}
