package com.proje.utility;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Utility {

    public String getSimpleDateFormat(Date date){
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    public String getSimpleHourFormat(Date date){
        return new SimpleDateFormat("HH:mm").format(date);
    }
}
