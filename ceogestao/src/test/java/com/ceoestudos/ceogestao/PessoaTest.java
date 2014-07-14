package com.ceoestudos.ceogestao;

import br.com.ceoestudos.ceogestao.model.TipoPessoa;
import org.junit.Assert;
import org.junit.Test;

public class PessoaTest {
    @Test
    public void tipoPessoaTest(){
        Assert.assertEquals(TipoPessoa.ALUNO, TipoPessoa.valueOf("ALUNO"));
        Assert.assertEquals(TipoPessoa.PACIENTE, TipoPessoa.valueOf("PACIENTE"));
        Assert.assertEquals(TipoPessoa.PROFESSOR, TipoPessoa.valueOf("PROFESSOR"));
        
    }
}
