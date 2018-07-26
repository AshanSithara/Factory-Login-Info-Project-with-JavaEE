package com.proje.resources;

import com.proje.dto.PlaceDto;
import com.proje.model.Place;
import com.proje.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/place")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlaceResource {

    @Autowired
    PlaceService placeService;

    @GET
    public Response findAllPlaces(){

        List<PlaceDto> placeDtos;
        try{
            placeDtos = placeService.findAll();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(placeDtos).build();
    }

    @GET
    @Path("/secured")
    public Response findAllPlacesSec(){

        List<PlaceDto> placeDtos;
        try{
            placeDtos = placeService.findAll();
        }catch (Exception e){
            System.out.println("Hata : " + e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(placeDtos).build();
    }

    @POST
    @Path("/secured")
    public Response savePlace(PlaceDto placeDto){

        Place place;
        try{
            String errorMessage = placeService.placeErrorControl(placeDto);
            if(errorMessage !=null)
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();

            place = placeService.savePlace(placeDto);
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(place).build();
    }

    @PUT
    @Path("/secured/{id}")
    public Response updatePlace(PlaceDto placeDto){

        Place place;
        try{
            String errorMessage = placeService.placeErrorControl(placeDto);
            if(errorMessage !=null)
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();

            place = placeService.updatePlace(placeDto);
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(place).build();
    }

    @DELETE
    @Path("/secured/{id}")
    public Response removePlace(@PathParam("id") int id) {

        try {
            placeService.removePlace(id);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }
}
