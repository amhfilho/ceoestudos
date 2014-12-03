package com.ceoestudos.ceogestao;

import br.com.ceoestudos.ceogestao.util.Util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author amhfilho
 */
public class UtilTest {
    
    @Test
    public void testeSameDate() throws ParseException{
        Util util = new Util();
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("02/12/2014");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("02/12/2014");
        Assert.assertTrue(util.sameDate(date1, date2));
    }
    
    @Test
    public void testNotSameDate() throws ParseException {
        Util util = new Util();
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("02/12/2014");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("22/12/2014");
        Assert.assertFalse(util.sameDate(date1, date2));
    }
    
    @Test
    public void testBeginDateNotAfterEndDate() throws ParseException {
        Util util = new Util();
        Date begin = new SimpleDateFormat("dd/MM/yyyy").parse("02/12/2014");
        Date end = new SimpleDateFormat("dd/MM/yyyy").parse("22/12/2014");
        Assert.assertFalse(util.after(begin, end));
    }
    
    @Test
    public void testBeginDateAfterEndDate() throws ParseException {
        Util util = new Util();
        Date begin = new SimpleDateFormat("dd/MM/yyyy").parse("02/12/2014");
        Date end = new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2014");
        Assert.assertTrue(util.after(begin, end));
    }
    
    
    @Test(expected = NullPointerException.class)  
    public void testBeginDateNullExpectNullPointer() throws ParseException {
        Util util = new Util();
        Date begin = null;
        Date end = new SimpleDateFormat("dd/MM/yyyy").parse("01/12/2014");
        util.after(begin, end);
    }
}

