package com.proje.service;

import com.proje.dao.LoginControlDao;
import com.proje.dao.LoginPermissionDao;
import com.proje.dto.LoginPermissionDto;
import com.proje.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LoginPermissionService {

    @Autowired
    private LoginPermissionDao loginPermissionDao;

    @Autowired
    private LoginControlDao loginControlDao;

    @Transactional
    public LoginPermission saveLoginPermission(LoginPermissionDto loginPermissionDto){

        LoginPermission loginPermission;

        try {
            loginPermission = new LoginPermission();
            loginPermission.setPermissionOk(0);
            loginPermission.setDateOfEntry(loginPermissionDto.getDateOfEntry());
            loginPermission.setDateOfOut(loginPermissionDto.getDateOfOut());
            loginPermission.setLoginHours(loginPermissionDto.getLoginHours());
            loginPermission.setOutHours(loginPermissionDto.getOutHours());
            loginPermission.setCompanyId(loginPermissionDto.getCompanyId());
            loginPermission.setUsers(loginPermissionDto.getUsers());
            loginPermission.setPlaces(loginPermissionDto.getPlaces());
            loginPermission.setIntroductionReasons(loginPermissionDto.getIntroductionReasons());
            loginPermission.setPersonelName(loginPermissionDto.getPersonelName());
            loginPermission.setIsJobSecurity(loginPermissionDto.getIsJobSecurity());

            loginPermissionDao.persist(loginPermission);

        }catch (Exception e){
            return null;
        }

        return loginPermission;
    }

    @Transactional(readOnly = true)
    public List<LoginPermissionDto> findAllLoginPermission() {

        List<LoginPermissionDto> loginPermissionDtos = new ArrayList<LoginPermissionDto>();

        try {
            List<LoginPermission> loginPermissions = loginPermissionDao.findAllLoginPermission();
            String deneme = "" + loginPermissions;
            for (LoginPermission loginPermission : loginPermissions) {

                LoginPermissionDto loginPermissionDto = new LoginPermissionDto(loginPermission);
                loginPermissionDto.setUsers(loginPermission.getUsers());
                loginPermissionDto.setPlaces(loginPermission.getPlaces());
                loginPermissionDto.setIntroductionReasons(loginPermission.getIntroductionReasons());
                loginPermissionDtos.add(loginPermissionDto);
            }

        }catch (Exception e){
            return null;
        }
        return loginPermissionDtos;
    }

    @Transactional
    public LoginPermission updateLoginPermission(LoginPermissionDto loginPermissionDto) {

        LoginPermission loginPermission;
        try {
            loginPermission = loginPermissionDao.find(loginPermissionDto.getPermissionId());
            String deneme = "" + loginPermission;
            loginPermission.setPermissionOk(loginPermissionDto.getPermissionOk());
            loginPermission.setIsJobSecurity(loginPermissionDto.getIsJobSecurity());

            if(loginPermissionDto.getPermissionOk() != 0) {

                LoginControl loginControl = null;
                LoginControlDates loginControlDates;

                List<User> users = loginPermissionDto.getUsers();

                for (User user : users) {

                    boolean kontrol = false;
                    try {
                        loginControl = loginControlDao.findWithLoginPermissionId(loginPermission.getPermissionId(),user.getUserId());
                    }catch (Exception e){
                    }

                    if(loginControl == null){
                        loginControl = new LoginControl();
                        kontrol = true; //Kontrol true ise ekleme false ise güncelleme işlemi yap.
                    }

                    loginControl.setUser(user);
                    Date entryDate = loginPermission.getDateOfEntry();
                    Date outDate = loginPermission.getDateOfOut();
                    loginControl.setEntryDate(entryDate);
                    loginControl.setOutDate(outDate);
                    loginControl.setJobSecOk(loginPermission.getIsJobSecurity());
                    loginControl.setCompanyName(loginPermission.getCompanyId());
                    loginControl.setLoginHour(loginPermission.getLoginHours());
                    loginControl.setOutHour(loginPermission.getOutHours());
                    loginControl.setLoginPermissionId(loginPermission.getPermissionId());

                    Calendar c = Calendar.getInstance();

                    int i = 0;
                    while (entryDate.getTime() <= outDate.getTime()) {
                        if(kontrol){
                            loginControlDates = new LoginControlDates();
                            c.setTime(entryDate);
                            loginControlDates.setControlDate(c.getTime());
                            c.add(Calendar.DATE, 1);
                            entryDate = c.getTime();
                            loginControl.addControlDate(loginControlDates);
                        }

                        else{
                            loginControlDates = loginControl.getLoginControlDates().get(i);
                            c.setTime(entryDate);
                            loginControlDates.setControlDate(c.getTime());
                            c.add(Calendar.DATE, 1);
                            entryDate = c.getTime();
                        }

                        i++;
                    }

                    if(kontrol)
                        loginControlDao.persist(loginControl);
                    else
                        loginControlDao.merge(loginControl);

                    loginControl = null;
                }
            }
            loginPermissionDao.merge(loginPermission);
        }catch (Exception e){
            return null;
        }
        return loginPermission;
    }

    @Transactional(readOnly = true)
    public List<LoginPermissionDto> findAllLoginPermissionByPlace(String companyName) {

        List<LoginPermissionDto> loginPermissionDtos = new ArrayList<LoginPermissionDto>();
        try {
            List<LoginPermission> loginPermissions = loginPermissionDao.findAllLoginPermission();
            String deneme = "" + loginPermissions;

            for (LoginPermission loginPermission : loginPermissions) {

                String company = loginPermission.getCompanyId();

                if(company.equalsIgnoreCase(companyName)){
                    LoginPermissionDto loginPermissionDto = new LoginPermissionDto(loginPermission);
                    loginPermissionDto.setUsers(loginPermission.getUsers());
                    loginPermissionDto.setIntroductionReasons(loginPermission.getIntroductionReasons());
                    loginPermissionDto.setPlaces(loginPermission.getPlaces());
                    loginPermissionDtos.add(loginPermissionDto);
                }
            }
        }catch (Exception e){
            return null;
        }
        return loginPermissionDtos;
    }

    public String loginPermissionDateControl(LoginPermissionDto loginPermissionDto){

        if(loginPermissionDto.getDateOfEntry() == null){
            return "Giriş Tarihi Geçerli Değil";
        }else if(loginPermissionDto.getDateOfOut() == null){
            return "Çıkış Tarihi Geçerli Değil";
        }else if(loginPermissionDto.getLoginHours() == null){
            return "Giriş Saati Geçerli Değil";
        }else if(loginPermissionDto.getOutHours() == null){
            return "Çıkış Saati Geçerli Değil";
        }else if(loginPermissionDto.getLoginHours().getTime() >= loginPermissionDto.getOutHours().getTime()){
            return "Giriş Saati Çıkış Saatinden İlerde Olamaz";
        }

        Date nowDate = new Date();
        Date dateOfEntry = loginPermissionDto.getDateOfEntry();//Giriş Tarihi
        Date dateOfOut = loginPermissionDto.getDateOfOut();//Çıkış Tarihi

        if(nowDate.getTime() - 5000 >= dateOfEntry.getTime()){//5 sn ye çıkarttık çünkü bulunduğu gün içinde giriş izni oluştarabilir.
            return "Giriş Tarihi Geçmiş Bir Tarih Olamaz.";
        }

        if(dateOfOut.getTime()<dateOfEntry.getTime()){
            return "Giriş Tarihi Çıkış Tarihinden İlerde Olamaz";
        }

        long fark = dateOfOut.getTime() - dateOfEntry.getTime();
        long gun = fark/(1000*60*60*24);

        if(gun > 7){
            return "Giriş ve Çıkış Tarihi Arası Bir Haftadan Uzun Olamaz";
        }
        return null;
    }
}
