/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author Afilho
 */
public class PessoaPropertyEditor extends PropertyEditorSupport {

    private final PessoaDAO pessoaDAO;

    public PessoaPropertyEditor(PessoaDAO pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
    }

    @Override
    public void setAsText(String text) {
        Pessoa pessoa = pessoaDAO.getById(Long.parseLong(text));
        setValue(pessoa);
    }
    
    
    @Override
    public String getAsText(){
        return ((Pessoa)getValue()).getIdentificador().toString();
    }
}
