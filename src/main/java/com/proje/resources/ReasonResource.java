package com.proje.resources;

import com.proje.dto.IntroductionReasonDto;
import com.proje.model.IntroductionReason;
import com.proje.service.ReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/reason")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReasonResource {

    @Autowired
    ReasonService reasonService;

    @GET
    public Response findAllReasons(){

        List<IntroductionReasonDto> reasonDtos;
        try{
            reasonDtos = reasonService.findAll();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(reasonDtos).build();
    }

    @GET
    @Path("/secured")
    public Response findAllReasonsSec(){

        List<IntroductionReasonDto> reasonDtos;
        try{
            reasonDtos = reasonService.findAll();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(reasonDtos).build();
    }

    @POST
    @Path("/secured")
    public Response saveReason(IntroductionReasonDto reasonDto){

        IntroductionReason reason;
        try{
            String errorMessage = reasonService.reasonErrorControl(reasonDto);
            if(errorMessage != null)
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();

            reason = reasonService.saveReason(reasonDto);
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(reason).build();
    }

    @PUT
    @Path("/secured/{id}")
    public Response updateReason(IntroductionReasonDto reasonDto){

        IntroductionReason reason;
        try{
            String errorMessage = reasonService.reasonErrorControl(reasonDto);
            if(errorMessage != null)
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();

            reason = reasonService.updateReason(reasonDto);
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(reason).build();
    }

    @DELETE
    @Path("/secured/{id}")
    public Response removeReason(@PathParam("id") int id) {

        try {
            reasonService.removeReason(id);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }
}
