/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceoestudos.ceogestao.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author amhfilho
 */
public class Util {

    public String toString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString(); // stack trace as a string
    }
    
    public String arrayToString(String[] array){
        if(array==null) return null;
        String retorno ="";
        for(String str:array){
            retorno+=str+",";
        }
        return retorno;
    }
    
    public String toString(Set objectSet){
        if(objectSet==null) return null;
        String retorno = "";
        for(Object obj: objectSet){
            retorno+=obj.toString()+"\n";
        }
        return retorno;
    }
    
    public boolean sameDate(Date date1, Date date2){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        if(date1!=null){
           cal1.setTime(date1);
        }
        
        if(date2 != null){
            cal2.setTime(date2);
        }
        
        return( (date1==null && date2 == null) ||
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)&&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
        
    }
    
    /**
     * Compara se begin é posterior a end
     * @param begin
     * @param end
     * @return 
     */
    public boolean after(Date begin,Date end){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(begin);
        cal2.setTime(end);
        return cal1.after(cal2);
    }

}
