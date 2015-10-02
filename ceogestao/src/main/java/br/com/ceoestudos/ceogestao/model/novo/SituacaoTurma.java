package br.com.ceoestudos.ceogestao.model.novo;

/**
 *
 * @author amhfilho
 */
public enum SituacaoTurma {
    
    
    NAO_CONFIRMADA("Não Confirmada"),
    CONFIRMADA("Confirmada"),
    EM_ANDAMENTO("Em andamento"),
    CANCELADA("Cancelada");
    
    private String nome;

    SituacaoTurma(String nome){
        this.nome = nome;
    }
    
    @Override
    public String toString(){
        return this.nome;
    }
    
    public String getNome(){
        return nome;
    }

}
