package com.proje.security;

import com.proje.dto.UserDto;
import com.proje.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;

@Component
@Provider
public class CustomAuthenticationProvider implements AuthenticationProvider{

    @Autowired
    private UserService userService;

    public static String getUsername() {

        //O anki oturum bilgileri alınıyor.
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        //Eğer oturum varsa kullanıcı adı username degiskenine atanıyor.
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {//Eğer oturum yoksa kullanıcı adı "anonymousUser" döner.
            username = principal.toString();
        }
        //Eğer kullanıcı adı varsayılan değerse null atanır.
        if (username == "anonymousUser")
            username = null;
        return username;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //Formdan girilen kullanıcı adı ve şifre alınıyor.
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        //Girilen kullanıcı adına göre veritabanından kayıt arama işlemi yapılır.
        UserDto userDto = null;

        try{
            userDto = userService.findUserWithUsername(name);
        }catch (Exception e){
            System.out.println("Kullanıcı Bulunamadı");
        }

        if (userDto != null) {//Eğer eşleşme olduysa

            if (userDto.getPassword().equals(password)) {//Eğer girilen şifre eşleşiyorsa

                if(userDto.getOk() != 0){//Hesap Onaylanmışmı
                    //Yeni bir yetki listesi oluşturulur.
                    List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
                    //Yetki listesine rolü eklenir.
                    grantedAuths.add(new SimpleGrantedAuthority(userDto.getRoles().getRoleName()));
                    //Bu kullanıcıya ait oturum için yeni bir token oluşturulur.
                    //İçerisine kullanıcı bilgileri ve yetkileri atanır.
                    Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
                    return auth;
                }
                return null;

            } else
                return null;
        } else
            return null;
    }

    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
