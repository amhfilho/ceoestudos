package br.com.ceoestudos.ceogestao.controller;

import br.com.ceoestudos.ceogestao.dao.ProcedimentoDAO;
import br.com.ceoestudos.ceogestao.model.Procedimento;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author Afilho
 */
public class ProcedimentoPropertyEditor extends PropertyEditorSupport {
    
    private final ProcedimentoDAO pDAO;

    public ProcedimentoPropertyEditor(ProcedimentoDAO pDAO) {
        this.pDAO = pDAO;
    }
    
    @Override
    public void setAsText(String text){
        if(text.equals("")){
            text = "0";
        }
        setValue(pDAO.getById(Long.parseLong(text)));
    }
    
    @Override
    public String getAsText(){
        Procedimento p = (Procedimento)getValue();
        if(p!=null){
            return p.getId().toString();
        } else {
            return null;
        }
    }
    
}
