package me.efekos.awakensmponline.utils;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.simpler.translation.TranslateManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String translateDate(Date date){
        String hour = new SimpleDateFormat("HH").format(date);
        String min = new SimpleDateFormat("mm").format(date);
        String day = new SimpleDateFormat("dd").format(date);
        String month = new SimpleDateFormat("MM").format(date);
        String year = new SimpleDateFormat("yyyy").format(date);
        String sec = new SimpleDateFormat("ss").format(date);

        return TranslateManager.translateColors(AwakenSMPOnline.LANG.getString("items.tracking_compass.date-format","%hour%:%minute%, %day%/%month%/%year%")
                .replace("%hour%",hour)
                .replace("%minute%",min)
                .replace("%day%",day)
                .replace("%month%",month)
                .replace("%year%",year)
                .replace("%second%",sec)
        );
    }
}
