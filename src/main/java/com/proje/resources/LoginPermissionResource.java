package com.proje.resources;

import com.proje.dto.LoginPermissionDto;
import com.proje.dto.UserDto;
import com.proje.model.LoginPermission;
import com.proje.service.LoginPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Path("/loginpermission")
public class LoginPermissionResource {

    @Autowired
    private LoginPermissionService loginPermissionService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveLoginPermission(LoginPermissionDto loginPermissionDto){

        LoginPermission loginPermission;
        try{
            String isError = loginPermissionService.loginPermissionDateControl(loginPermissionDto);
            if(isError != null)
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(isError).build();

            loginPermission = loginPermissionService.saveLoginPermission(loginPermissionDto);
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(loginPermission).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllLoginPermissions(){

        List<LoginPermissionDto> loginPermissionDtos;
        try{
            loginPermissionDtos = loginPermissionService.findAllLoginPermission();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(loginPermissionDtos).build();
    }

    @GET
    @Path("/notOk")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllNotApprovalLoginPermissions(){

        List<LoginPermissionDto> loginPermissionDtos;
        List<LoginPermissionDto> permissionDtos = new ArrayList<LoginPermissionDto>();
        try{
            loginPermissionDtos = loginPermissionService.findAllLoginPermission();
            for(LoginPermissionDto loginPermissionDto:loginPermissionDtos){
                if(loginPermissionDto.getPermissionOk() == 0){
                    permissionDtos.add(loginPermissionDto);
                }
            }
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(permissionDtos).build();
    }

    @GET
    @Path("/notJobSecurity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllNotJobSecurtiyApprovalLoginPermissions(){

        List<LoginPermissionDto> loginPermissionDtos;
        List<LoginPermissionDto> permissionDtos = new ArrayList<LoginPermissionDto>();
        try{
            loginPermissionDtos = loginPermissionService.findAllLoginPermission();
            for(LoginPermissionDto loginPermissionDto:loginPermissionDtos){
                if(loginPermissionDto.getIsJobSecurity() == 0){
                    permissionDtos.add(loginPermissionDto);
                }
            }
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(permissionDtos).build();
    }

    @PUT
    @Path("/notOk/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLoginPermission(LoginPermissionDto loginPermissionDto) {

        LoginPermission loginPermission;
        try {
            loginPermission = loginPermissionService.updateLoginPermission(loginPermissionDto);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(loginPermission).build();
    }

    @PUT
    @Path("/notJobSecurity/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLoginPermissionJobSecurity(LoginPermissionDto loginPermissionDto) {

        LoginPermission loginPermission;
        try {
            loginPermission = loginPermissionService.updateLoginPermission(loginPermissionDto);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(loginPermission).build();
    }

    @GET
    @Path("/company/{companyName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllLoginPermissionWithCompanyName(@PathParam("companyName") String companyName){

        List<LoginPermissionDto> loginPermissionDtos;
        try{
            loginPermissionDtos = loginPermissionService.findAllLoginPermissionByPlace(companyName);
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(loginPermissionDtos).build();
    }
}
