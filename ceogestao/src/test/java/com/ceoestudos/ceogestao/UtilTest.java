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
}
