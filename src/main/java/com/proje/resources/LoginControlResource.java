package com.proje.resources;

import com.proje.dto.LoginControlDto;
import com.proje.dto.ReportDto;
import com.proje.dto.UserDto;
import com.proje.model.Company;
import com.proje.model.LoginControl;
import com.proje.service.LoginControlService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Path("/logincontrol")
public class LoginControlResource {

    @Autowired
    LoginControlService loginControlService;

    @GET
    @Path("/company")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllLoginControlsWithCompanyName(@QueryParam("companyName") String companyName){

        List<LoginControlDto> loginControlDtos;
        try{
            loginControlDtos = loginControlService.findAllWithCompanyName(companyName);

        }catch (Exception e){
            System.out.println("Hata : " + e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(loginControlDtos).build();
    }

    @PUT
    @Path("/company/{loginContolId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLoginControl(@PathParam("loginContolId") int loginControlId,
                                       @HeaderParam("loginHour") Date loginHour,
                                       @HeaderParam("outHour") Date outHour){

        try{
            String errorMessage = loginControlService.controlLoginAndOutHours(loginHour,outHour);

            if(errorMessage != null)
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();

            loginControlService.updateLoginControl(loginControlId,loginHour,outHour);
        }catch (Exception e){
            System.out.println("Hata : " + e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return  Response.ok(true).build();
    }

    @GET
    @Path("/secured")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public UserDto secured(){

        return null;
    }

    @GET
    @Path("/secured/report")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReport(@QueryParam("companyName") String companyName,
                              @QueryParam("firstDate") String firstDate,
                              @QueryParam("secondDate") String secondDate){

        List<ReportDto> reportDtos;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date date1 = sdf.parse(firstDate);
            Date date2 = sdf.parse(secondDate);

            reportDtos = loginControlService.getReport(companyName,date1,date2);

        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.ok(reportDtos).build();
    }

    @GET
    @Path("/secured/export")
    @Produces("application/vnd.ms-excel")
    public Response exportExcel(@QueryParam("companyName") String companyName,
                                @QueryParam("firstDate") String firstDate,
                                @QueryParam("secondDate") String secondDate){

        List<ReportDto> reportDtos;

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date date1 = sdf.parse(firstDate);
            Date date2 = sdf.parse(secondDate);

            reportDtos = loginControlService.getReport(companyName,date1,date2);

            String[] columns = {"Personel", "Tarih", "Giriş Saati", "Çıkış Saati"};


            //Verilerin yazılacağı dosya tanımlanıyor.
            final File file = new File("login-report.xls");

            // Bir çalışma kitabı oluştur
            Workbook workbook = new XSSFWorkbook();


            CreationHelper createHelper = workbook.getCreationHelper();
            // Create a Sheet
            Sheet sheet = workbook.createSheet("Report");

            // Başlık hücreleri için font ayarları
            Font headerFont = workbook.createFont();
            headerFont.setBoldweight((short)1);//????????????????????????
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.RED.getIndex());

            // Create a CellStyle with the font
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Create a Row
            Row headerRow = sheet.createRow(0);

            // Creating cells
            for(int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Create Cell Style for formatting Date
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
            CellStyle hourCellStyle = workbook.createCellStyle();
            hourCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("HH:mm"));

            int rowNum = 1;
            for(ReportDto reportDto: reportDtos) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0)
                        .setCellValue(reportDto.getPersonelName());

                Cell controlDate = row.createCell(1);
                controlDate.setCellValue(reportDto.getControlDate());
                controlDate.setCellStyle(dateCellStyle);

                if(reportDto.getLoginHour() != null){
                    Cell loginHour = row.createCell(2);
                    loginHour.setCellValue(reportDto.getLoginHour());
                    loginHour.setCellStyle(hourCellStyle);
                }else{
                    Cell loginHour = row.createCell(2);
                    loginHour.setCellValue("Giriş Yok");
                    loginHour.setCellStyle(hourCellStyle);
                }

                if(reportDto.getOutHour() != null){
                    Cell outHour = row.createCell(3);
                    outHour.setCellValue(reportDto.getOutHour());
                    outHour.setCellStyle(hourCellStyle);
                }else{
                    Cell outHour = row.createCell(3);
                    outHour.setCellValue("Çıkış Yok");
                    outHour.setCellStyle(hourCellStyle);
                }
            }

            // Resize all columns to fit the content size
            for(int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try {
                //Tanımlanan dosya için diske erişim sağlanıyor.
                FileOutputStream out = new FileOutputStream(file);
                //Veriler dosyaya yazılıyor.
                workbook.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Başarılı cevabı gönderiliyor ve dosya response'un entity'sine set ediliyor.
            Response.ResponseBuilder responseBuilder = Response.ok(file);
            //Kullanıcıya gönderilen dosya ismi belirleniyor.
            responseBuilder.header("Content-Disposition", "attachment; filename=login-report.xls");

            return responseBuilder.build();


        }catch (Exception e){
            System.out.println("Hata : " + e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


}
