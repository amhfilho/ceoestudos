/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceoestudos.ceogestao;

import br.com.ceoestudos.ceogestao.model.Turma;
import br.com.ceoestudos.ceogestao.util.Util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.junit.Test;

/**
 *
 * @author amhfilho
 */
public class TurmaTest {

    @Test
    public void testeFormatacaoDiasDaSemana() {
        Turma turma = new Turma();
        Assert.assertEquals("", turma.getDiasDaSemanaFormatados());
        String[] diasDaSemana = new String[]{"SEG", "TER", "QUA"};
        turma.setDiasDaSemana(diasDaSemana);
        String retorno = turma.getDiasDaSemanaFormatados();
        Assert.assertEquals("SEG,TER,QUA", retorno);

    }

    @Test
    public void testeDateFormat() {
        try {
            SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
            Date date = hourFormat.parse("23:00");

            Assert.assertEquals("23:00", hourFormat.format(date));
        } catch (ParseException ex) {
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testeCargaHoraria() {
        try {
            Turma turma = criarTurma();
            DateTime dateTimeInicio = new DateTime(turma.getCalendarInicioAulas());
            int horaTermino = turma.getCalendarFimAulas().get(Calendar.HOUR_OF_DAY);
            int minutoTermino = turma.getCalendarFimAulas().get(Calendar.MINUTE);
            DateTime dateTimeFim = new DateTime(turma.getCalendarInicioAulas())
                    .withHourOfDay(horaTermino)
                    .withMinuteOfHour(minutoTermino);
            System.out.println("Minutes.minutesBetween: "+Minutes.minutesBetween(dateTimeInicio, dateTimeFim).getMinutes());
            
        } catch (ParseException ex) {
            Assert.fail(new Util().toString(ex));
        }
    }
    
    @Test
    public void testeMinutosAula(){
        try {
            Turma turma = criarTurma();
            Assert.assertEquals(540, turma.getMinutosAula());
            
        } catch (ParseException ex) {
            Assert.fail(new Util().toString(ex));
        }    
    }
    
    @Test
    public void testeNumeroAulasTurma(){
        try {
            Turma turma = criarTurma();
            Assert.assertEquals(6, turma.getNumeroDeAulasTurma());
            
        } catch (ParseException ex) {
            Assert.fail(new Util().toString(ex));
        }
    }
    
    private Turma criarTurma() throws ParseException {
        Turma turma = new Turma();
        turma.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").parse("03/02/2014"));
        turma.setHoraInicio(new SimpleDateFormat("HH:mm").parse("09:00"));
        turma.setDataFim(new SimpleDateFormat("dd/MM/yyyy").parse("17/02/2014"));
        turma.setHoraFim(new SimpleDateFormat("HH:mm").parse("18:00"));
        turma.setDiasDaSemana(new String[]{"SEG","QUA","SEX"});
        return turma;
    }

    @Test
    public void testeCriacaoCalendarDatas() {
        try {
            Turma turma = criarTurma();
            
            Calendar inicio = turma.getCalendarInicioAulas();
            Calendar fim = turma.getCalendarFimAulas();

            Assert.assertEquals(3, inicio.get(Calendar.DAY_OF_MONTH));
            Assert.assertEquals(1, inicio.get(Calendar.MONTH));
            Assert.assertEquals(2014, inicio.get(Calendar.YEAR));
            Assert.assertEquals(9, inicio.get(Calendar.HOUR_OF_DAY));
            Assert.assertEquals(0, inicio.get(Calendar.MINUTE));

            Assert.assertEquals(17, fim.get(Calendar.DAY_OF_MONTH));
            Assert.assertEquals(1, fim.get(Calendar.MONTH));
            Assert.assertEquals(2014, fim.get(Calendar.YEAR));
            Assert.assertEquals(18, fim.get(Calendar.HOUR_OF_DAY));
            Assert.assertEquals(0, fim.get(Calendar.MINUTE));

        } catch (ParseException ex) {
            Assert.fail(new Util().toString(ex));
        }

    }

}
