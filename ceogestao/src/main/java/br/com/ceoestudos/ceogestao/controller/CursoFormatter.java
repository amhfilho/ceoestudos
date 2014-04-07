package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.CursoDAO;
import br.com.ceoestudos.ceogestao.model.Curso;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

/**
 *
 * @author amhfilho
 */
@Component
public class CursoFormatter implements Formatter<Curso>{
    
    @Autowired
    private CursoDAO cursoDAO;

    @Override
    public String print(Curso curso, Locale locale) {
        return curso.getNome();
    }

    @Override
    public Curso parse(String string, Locale locale) throws ParseException {
        if(string!=null && !string.equals("")){
            Long id = new Long(string);
            return cursoDAO.getById(id);
        } else {
            return null;
        }
        
    }
    
}
