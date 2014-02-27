/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.model.Pessoa;
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
public class PessoaFormatter implements Formatter<Pessoa>{
    
    @Autowired
    private PessoaDAO pessoaDAO;

    @Override
    public String print(Pessoa t, Locale locale) {
        return t.getNome();
    }

    @Override
    public Pessoa parse(String string, Locale locale) throws ParseException {
        Long id = new Long(string);
        return pessoaDAO.getById(id);
    }
    
}
