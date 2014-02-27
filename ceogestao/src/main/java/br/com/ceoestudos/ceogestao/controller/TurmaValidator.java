/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.model.SituacaoTurma;
import br.com.ceoestudos.ceogestao.model.Turma;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author amhfilho
 */
@Component
public class TurmaValidator implements Validator {
    private Turma turma;

    @Override
    public boolean supports(Class<?> type) {
        return type.isAssignableFrom(Turma.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        turma = (Turma) o;
        
        validarSituacao(errors);
        validarCargaHoraria(errors);
        validarDiasDaSemana(errors);
        
    }
    
    private void validarDiasDaSemana(Errors errors){
        if(turma.getDiasDaSemana()==null || turma.getDiasDaSemana().length==0){
            errors.rejectValue("diasDaSemana", "TurmaValidator.diasDaSemana",
                "Você deve informar ao menos um dia da semana");
        }
    }
    
    private void validarCargaHoraria(Errors errors){
        //TODO implementar    
    }
        
    private void validarSituacao(Errors errors){
         if (turma.getSituacao().equals(SituacaoTurma.EM_ANDAMENTO)) {
            Calendar inicio = Calendar.getInstance();
            inicio.setTime(turma.getDataInicio());
            Calendar hoje = Calendar.getInstance();
            if(inicio.after(hoje)){
                errors.rejectValue("situacao", "TurmaValidator.situacao",
                "A turma não pode estar em andamento pois a data de início ainda não ocorreu");
            }
        }
    }
    
    

}
