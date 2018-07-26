package com.proje.service;

import com.proje.dao.LoginControlDao;
import com.proje.dto.LoginControlDatesDto;
import com.proje.dto.LoginControlDto;
import com.proje.dto.ReportDto;
import com.proje.model.LoginControl;
import com.proje.model.LoginControlDates;
import com.proje.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LoginControlService {

    @Autowired
    private LoginControlDao loginControlDao;

    @Autowired
    private Utility utility;

    @Transactional
    public LoginControl saveLoginControl(LoginControlDto loginControlDto){

        LoginControl loginControl;
        try {
            loginControl = new LoginControl();
            loginControl.setUser(loginControlDto.getUser());
            loginControlDao.persist(loginControl);

        }catch (Exception e){
            return null;
        }
        return loginControl;
    }

    @Transactional(readOnly = true)
    public List<LoginControlDto> findAllWithCompanyName(String companyName){

        List<LoginControlDto> loginControlDtos = new ArrayList<LoginControlDto>();
        try {
            List<LoginControl> loginControls = loginControlDao.findLoginControlWithUsername(companyName);
            LoginControlDto loginControl1;
            for(LoginControl loginControl:loginControls){
                Date nowDate = new Date();
                Date[] days = getDaysOfWeek(nowDate, Calendar.getInstance().getFirstDayOfWeek());

                List<LoginControlDates> loginControlDatesList = loginControl.getLoginControlDates();
                LoginControlDatesDto loginControlDatesDto;

                //Kontrol tablosunda önceki tarihteki kayıtların gözükmesini istemiyoruz
                if(days[0].getTime() <= loginControl.getEntryDate().getTime() + 86400000) {

                    loginControl1 = new LoginControlDto();

                    loginControl1.setLoginContolId(loginControl.getLoginContolId());
                    loginControl1.setUser(loginControl.getUser());
                    loginControl1.setEntryDate(loginControl.getEntryDate());
                    loginControl1.setOutDate(loginControl.getOutDate());
                    loginControl1.setJobSecOk(loginControl.getJobSecOk());
                    loginControl1.setCompanyName(loginControl.getCompanyName());
                    loginControl1.setLoginHour(loginControl.getLoginHour());
                    loginControl1.setOutHour(loginControl.getOutHour());

                    boolean kontrol;
                    for (Date date : days) {
                        kontrol = true;
                        loginControlDatesDto = new LoginControlDatesDto();
                        for (LoginControlDates loginControlDates1 : loginControlDatesList) {

                            if (utility.getSimpleDateFormat(date).equalsIgnoreCase(utility.getSimpleDateFormat(loginControlDates1.getControlDate()))) {
                                if(loginControlDates1.getControlDate() != null)
                                    loginControlDatesDto.setControlDate(utility.getSimpleDateFormat(loginControlDates1.getControlDate()));
                                if(loginControlDates1.getLoginHour() != null)
                                    loginControlDatesDto.setLoginHour(utility.getSimpleHourFormat(loginControlDates1.getLoginHour()));
                                if(loginControlDates1.getOutHour() != null)
                                    loginControlDatesDto.setOutHour(utility.getSimpleHourFormat(loginControlDates1.getOutHour()));
                                kontrol = false;
                                break;
                            }
                        }
                        if (kontrol) {
                            loginControlDatesDto.setControlDate(null);
                        }
                        loginControl1.addControlDate(loginControlDatesDto);
                    }
                    loginControlDtos.add(loginControl1);
                }
            }

        }catch (Exception e){
            return null;
        }
        return loginControlDtos;
    }

    @Transactional
    public Boolean updateLoginControl(int loginControld,Date loginHour,Date outHour){

        try{
            LoginControl loginControl = loginControlDao.find(loginControld);
            List<LoginControlDates> list1 = loginControl.getLoginControlDates();

            List<LoginControlDates> güncelListe = new ArrayList<LoginControlDates>();
            LoginControlDates lcd;
            for (LoginControlDates loginControlDates : list1){

                boolean kontrol = true;

                if(utility.getSimpleDateFormat(loginControlDates.getControlDate()).equalsIgnoreCase(utility.getSimpleDateFormat(new Date()))){
                    lcd = new LoginControlDates();
                    lcd.setControlDate(new Date());
                    if(loginHour != null)lcd.setLoginHour(loginHour);
                    if(outHour != null)lcd.setOutHour(outHour);
                    güncelListe.add(lcd);
                    kontrol = false;
                }

                if(kontrol){
                    güncelListe.add(loginControlDates);
                }
            }
            loginControl.setLoginControlDates(güncelListe);
            loginControlDao.merge(loginControl);

        }catch (Exception e){
            System.out.println("Hata(LoginControlService) : " + e);
        }
        return true;
    }


    @Transactional
    public List<ReportDto> getReport(String companyName, Date firstDate, Date secondDate){

        List<ReportDto> reportDtos = new ArrayList<ReportDto>();

        try {
            List<LoginControl> loginControls = loginControlDao.findLoginControlWithUsername(companyName);

            for(LoginControl loginControl: loginControls){
                Calendar c2 = Calendar.getInstance();
                c2.setTime(firstDate);

                Calendar c3 = Calendar.getInstance();
                c3.setTime(secondDate);

                List<LoginControlDates> loginControlDatesList = loginControl.getLoginControlDates();
                ReportDto reportDto;

                for (LoginControlDates loginControlDates:loginControlDatesList){
                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(loginControlDates.getControlDate());

                    if((c2.before(c1) && c1.before(c3)) || (c2.compareTo(c1)==0)){

                        reportDto = new ReportDto();
                        reportDto.setPersonelName(loginControl.getUser().getName());
                        reportDto.setControlDate(loginControlDates.getControlDate());
                        reportDto.setLoginHour(loginControlDates.getLoginHour());
                        reportDto.setOutHour(loginControlDates.getOutHour());

                        reportDtos.add(reportDto);
                    }
                }
            }

        }catch (Exception e){
            return null;
        }
        return reportDtos;
    }

    private Date[] getDaysOfWeek(Date refDate, int firstDayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(refDate);
        calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
        Date[] daysOfWeek = new Date[7];
        for (int i = 0; i < 7; i++) {
            daysOfWeek[i] = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return daysOfWeek;
    }

    public String controlLoginAndOutHours(Date loginDate,Date outDate){
        if(outDate != null && loginDate != null)
            if(loginDate.getTime() >= outDate.getTime()) return "Giriş Saati Çıkış Saatinden İleride Olamaz";

        return null;
    }

}
