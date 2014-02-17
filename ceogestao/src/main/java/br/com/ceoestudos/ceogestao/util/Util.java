/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceoestudos.ceogestao.util;

import java.io.PrintWriter;
import java.io.StringWriter;
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

}
