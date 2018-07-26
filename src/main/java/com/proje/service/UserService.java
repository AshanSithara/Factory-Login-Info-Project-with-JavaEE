package com.proje.service;

import com.proje.dao.UserDao;
import com.proje.dto.UserDto;
import com.proje.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public User saveUser(UserDto userDto){

        User user;
        try {
            user = new User();
            user.setName(userDto.getName());
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            user.setOk(0);

            userDao.persist(user);

        }catch (Exception e){
            return null;
        }

        return user;
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAllUsers() {

        List<UserDto> userDtoList = new ArrayList<UserDto>();
        try {
            List<User> userList = userDao.findAllUsers();

            for (User user : userList) {
                userDtoList.add(new UserDto(user));
            }
        }catch (Exception e){
            return null;
        }
        return userDtoList;
    }

    @Transactional(readOnly = true)
    public UserDto findUserWithUsername(String username){

        UserDto userDto;
        try {
            User user = userDao.findUserWithUsername(username);
            userDto = new UserDto(user);

        }catch (Exception e){
            return null;
        }
        return userDto;
    }

    @Transactional
    public User updateUser(UserDto userDto) {

        User user;
        try {
            user = userDao.find(userDto.getUserId());

            user.setName(userDto.getName());
            user.setUsername(userDto.getUsername());
            user.setRoles(userDto.getRoles());
            user.setOk(userDto.getOk());
            user.setLoginPermissions(userDto.getLoginPermissions());

            userDao.merge(user);

        }catch (Exception e){
            return null;
        }
        return user;
    }

    public String userErrorControl(UserDto userDto){
        try {
            List<User> users = userDao.findAllUsers();
            for (User user1 :users){
                if(userDto.getUsername().equalsIgnoreCase(user1.getUsername())){
                    return "Bu Kullanıcı Adı Başka Biri Tarafından Kullanılmakta.";
                }
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
