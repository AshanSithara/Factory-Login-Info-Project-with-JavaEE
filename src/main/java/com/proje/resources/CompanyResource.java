package com.proje.resources;

import com.proje.dto.CompanyDto;
import com.proje.model.Company;
import com.proje.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Component
@Path("/company")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompanyResource {

    @Autowired
    CompanyService companyService;

    @GET
    public Response findAllCompanies(){

        List<CompanyDto> companyDtos;
        try{
            companyDtos = companyService.findAll();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(companyDtos).build();
    }

    @GET
    @Path("/secured")
    public Response findAllCompaniesSec(){

        List<CompanyDto> companyDtos;
        try{
            companyDtos = companyService.findAll();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(companyDtos).build();
    }

    @POST
    @Path("/secured")
    public Response saveCompany(CompanyDto companyDto){

        Company company;
        try{
            String errorMessage = companyService.companyErrorControl(companyDto);
            if(errorMessage != null)
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();

            company = companyService.saveCompany(companyDto);
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(company).build();
    }

    @GET
    @Path("/secured/{companyId}")
    public Response getCompanyWithCompanyId(@PathParam("companyId") int companyId){

        CompanyDto companyDto;
        try{
            companyDto = companyService.findCompanyWithId(companyId);
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(companyDto).build();
    }

    @PUT
    @Path("/secured/{id}")
    public Response updateCompany(CompanyDto companyDto){

        Company company;
        try{
            String errorMessage = companyService.companyErrorControl(companyDto);
            if(errorMessage != null)
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
            company = companyService.updateCompany(companyDto);
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(company).build();
    }

    @DELETE
    @Path("/secured/{id}")
    public Response removeCompany(@PathParam("id") int id) {

        try {
            companyService.removeCompany(id);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }
}
