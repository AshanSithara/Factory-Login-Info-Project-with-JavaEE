package com.proje.resources;

import com.proje.dto.UserDto;
import com.proje.model.User;
import com.proje.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Autowired
    UserService userService;


    //Hesabı onaylanmayan kullanıcıları getir
    @GET
    @Path("/accountApproval")
    public Response getNotAccountApprovalUser(){

        List<UserDto> userDto;
        List<UserDto> userDtoList = new ArrayList<UserDto>();

        try{
            userDto = userService.findAllUsers();

            for(UserDto userDto1: userDto){
                if(userDto1.getOk() == 0){
                    userDtoList.add(userDto1);
                }
            }

        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(userDtoList).build();
    }

    @GET
    @Path("/{userName}")
    public Response getUserWithUsername(@PathParam("userName") String userName){

        UserDto userDto;
        try{
            userDto = userService.findUserWithUsername(userName);
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(userDto).build();
    }

    @GET
    public Response findAllUsers(){

        List<UserDto> userDtoList;
        try{
            userDtoList = userService.findAllUsers();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(userDtoList).build();
    }


    @POST
    public Response saveUser(UserDto userDto){

        User user;
        try{
            String errorMessage = userService.userErrorControl(userDto);
            if(errorMessage != null)
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
            user = userService.saveUser(userDto);
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(user).build();
    }

    @PUT
    @Path("/accountApproval/{id}")
    public Response updateUser(UserDto userDto) {

        User user;
        try {
            user = userService.updateUser(userDto);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(user).build();
    }

}
