/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.TurmaDAO;
import br.com.ceoestudos.ceogestao.model.Turma;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Afilho
 */
@Component
public class TurmaFormatter implements Formatter<Turma>{
    
    @Autowired
    private TurmaDAO turmaDAO;

    @Override
    public String print(Turma t, Locale locale) {
        return t.toString();
    }

    @Override
    public Turma parse(String string, Locale locale) throws ParseException {
        return turmaDAO.getById(Long.parseLong(string));
    }
    
    
}
