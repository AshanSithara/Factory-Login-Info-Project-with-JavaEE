package com.proje.resources;

import com.proje.dto.RoleDto;
import com.proje.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/roles")
public class RoleResource {

    @Autowired
    RoleService roleService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllRoles(){

        List<RoleDto> roleDtos;
        try{
            roleDtos = roleService.findAll();
        }catch (Exception e){
            System.out.println("Hata : " + e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(roleDtos).build();
    }
}
