package br.com.ceoestudos.ceogestao.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ceoestudos.ceogestao.dao.CursoDAO;
import br.com.ceoestudos.ceogestao.dao.PessoaDAO;
import br.com.ceoestudos.ceogestao.model.Curso;
import br.com.ceoestudos.ceogestao.model.Pessoa;
import br.com.ceoestudos.ceogestao.model.TipoPessoa;

/**
 *
 * @author amhfilho
 */
@Controller

public class PessoaController {

    private final Logger LOGGER = Logger.getLogger(getClass());

    @Autowired
    private PessoaDAO pessoaDAO;
    @Autowired
    private CursoDAO cursoDAO;

    private TipoPessoa tipoPessoa = TipoPessoa.PROFESSOR;
    
    @ModelAttribute("todosOsCursos")
    public List<Curso> getCursos() {
        return cursoDAO.listarTodos();
    }

    @RequestMapping(value = "clientes")
    public String pesquisarClientes(Model model, String pesquisa, String resultado) {
        List<Pessoa> lista = pessoaDAO.listarPorNome(pesquisa, TipoPessoa.ALUNO);
        lista.addAll(pessoaDAO.listarPorNome(pesquisa, TipoPessoa.PACIENTE));
        lista.addAll(pessoaDAO.listarPorNome(pesquisa, TipoPessoa.LISTA_ESPERA));
        model.addAttribute("pessoas", lista);
        return resultado;
    }

    @RequestMapping(value = "interessados")
    public String pesquisarListaEspera(Model model, String pesquisa, String resultado){
        tipoPessoa = TipoPessoa.LISTA_ESPERA;
        model.addAttribute("tipoPessoa", "interessado");
        List<Pessoa> lista = pessoaDAO.listarPorNome(pesquisa, tipoPessoa);
        model.addAttribute("pessoas", lista);
        if (resultado != null && !resultado.equals("")) {
            return resultado;
        }
        return "interessados";
    }
    
    @RequestMapping(value = "alunos")
    public String pesquisarAlunos(Model model, String pesquisa, String resultado) {
        tipoPessoa = TipoPessoa.ALUNO;
        model.addAttribute("tipoPessoa", "aluno");
        List<Pessoa> lista = pessoaDAO.listarPorNome(pesquisa, tipoPessoa);
        model.addAttribute("pessoas", lista);
        if (resultado != null && !resultado.equals("")) {
            return resultado;
        }
        return "alunos";
    }

    @RequestMapping(value = "pacientes")
    public String pesquisarPacientes(Model model, String pesquisa, String resultado) {
        tipoPessoa = TipoPessoa.PACIENTE;
        model.addAttribute("tipoPessoa", "paciente");
        List<Pessoa> lista = pessoaDAO.listarPorNome(pesquisa, tipoPessoa);
        model.addAttribute("pessoas", lista);
        if (resultado != null && !resultado.equals("")) {
            return resultado;
        }
        return "pacientes";
    }

    @RequestMapping(value = "professores")
    public String pesquisarProfessores(Model model, String pesquisa, String resultado) {
        tipoPessoa = TipoPessoa.PROFESSOR;
        model.addAttribute("tipoPessoa", "professor");
        List<Pessoa> lista = pessoaDAO.listarPorNome(pesquisa, tipoPessoa);
        model.addAttribute("pessoas", lista);
        if (resultado != null && !resultado.equals("")) {
            return resultado;
        }
        return "professores";
    }

    @RequestMapping(value = "novoaluno")
    public String novoAluno(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        model.addAttribute("tipoPessoa", "ALUNO");
        return "formAluno";
    }

    @RequestMapping(value = "novopaciente")
    public String novoPaciente(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        model.addAttribute("tipoPessoa", "PACIENTE");
        return "formPaciente";
    }

    @RequestMapping(value = "novoprofessor")
    public String novoProfessor(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        model.addAttribute("tipoPessoa", "PROFESSOR");
        return "formProfessor";
    }
    
    @RequestMapping(value = "novointeressado")
    public String novoInteressado(Model model) {
        model.addAttribute("pessoa", new Pessoa());
        model.addAttribute("tipoPessoa", "LISTA_ESPERA");
        return "formInteressado";
    }

    @Transactional
    @RequestMapping(value = "salvarPessoa", method = RequestMethod.POST)
    public String salvarPessoa(@Valid Pessoa pessoa, BindingResult result, Model model, String tipoPessoa) {

        if (!result.hasErrors()) {
            try {
                if (pessoa.getCro() == null) {
                    pessoa.setUfCro(null);
                }
                pessoa.setTipo(TipoPessoa.valueOf(tipoPessoa));
                if (pessoa.getIdentificador() == null || pessoa.getIdentificador() == 0) {
                    pessoaDAO.adicionar(pessoa);
                } else {
                    pessoaDAO.atualizar(pessoa);
                }
                model.addAttribute("SUCCESS_MESSAGE", "Pessoa gravada com sucesso!");

            } catch (RuntimeException e) {
                model.addAttribute("ERROR_MESSAGE", e.getMessage());
                LOGGER.error(e);
            }
        }
        String retorno = "";
        model.addAttribute("pessoa", pessoa);
        model.addAttribute("tipoPessoa", tipoPessoa);
        if (tipoPessoa.equals("ALUNO")) {
            retorno = "formAluno";
        } else if (tipoPessoa.equals("PACIENTE")) {
            retorno = "formPaciente";
        } else if (tipoPessoa.equals("PROFESSOR")) {
            retorno = "formProfessor";
        } else if (tipoPessoa.equals("LISTA_ESPERA")) {
            retorno = "formInteressado";
        }
        return retorno;
    }

    @RequestMapping("editarPessoa")
    public String editarPessoa(@RequestParam String id, Model model) {
        try {
            Long identificador = new Long(id);
            Pessoa pessoa = pessoaDAO.getById(identificador);
            model.addAttribute("pessoa", pessoa);
            model.addAttribute("tipoPessoa", pessoa.getTipo().name());
            String retorno = "";
            if (pessoa.getTipo().name().equals("ALUNO")) {
                retorno = "formAluno";
            } else if (pessoa.getTipo().name().equals("PACIENTE")) {
                retorno = "formPaciente";
            } else if (pessoa.getTipo().name().equals("PROFESSOR")) {
                retorno = "formProfessor";
            } else if (pessoa.getTipo().name().equals("LISTA_ESPERA")) {
                retorno = "formInteressado";
            }
            return retorno;
        } catch (RuntimeException e) {
            LOGGER.error(e);
            model.addAttribute("ERROR_MESSAGE", e.getMessage());
            return "pessoas";
        }
    }

    @Transactional
    @RequestMapping("excluirPessoa")
    public String excluirPessoa(@RequestParam String id, Model model) {
        Long identificador = new Long(id);
        try {
            pessoaDAO.excluir(identificador);

        } catch (RuntimeException e) {
            LOGGER.error(e);
            model.addAttribute("ERROR_MESSAGE", e.getMessage());
            model.addAttribute("pessoa", pessoaDAO.getById(identificador));
            return "formPessoa";
        }
        if (tipoPessoa.name().equals("ALUNO")) {
            return "redirect:alunos.html";
        } else if (tipoPessoa.name().equals("PACIENTE")) {
            return "redirect:pacientes.html";
        } else if (tipoPessoa.name().equals("PROFESSOR")) {
            return "redirect:professores.html";
        } else if (tipoPessoa.name().equals("LISTA_ESPERA")) {
            return "redirect:interessados.html";
        } else {
            return null;
        }
    }
    
    @Transactional
    @RequestMapping(value="cadastro/interessados", method=RequestMethod.POST)
    public ResponseEntity<Void> cadastroInteressados(@RequestParam(name="your-name",required=true) String name,
    		@RequestParam(name="your-email",required=true) String email,
    		@RequestParam(name="cpf",required=true) String cpf,
    		@RequestParam(name="cro",required=true) String cro,
    		@RequestParam(name="menu-339",required=true) String curso,
    		@RequestParam(name="telefone",required=false) String telefone,
    		@RequestParam(name="celular",required=false) String celular,
    		@RequestParam(name="endereco",required=false) String endereco,
    		@RequestParam(name="cidade",required=false) String cidade,
    		@RequestParam(name="bairro",required=false) String bairro,
    		@RequestParam(name="estado",required=false) String estado,
    		@RequestParam(name="cep",required=false) String cep){
    	
    	LOGGER.info(name+","+cpf+","+cro+","+curso+","+email+","+telefone+","+celular+","+endereco+","+cidade+","+bairro+","+estado+","+cep);
    	
    	Pessoa pessoa = new Pessoa();
    	pessoa.setTipo(TipoPessoa.LISTA_ESPERA);
    	pessoa.setNome(name);
    	pessoa.setBairro(bairro);
    	pessoa.setCidade(cidade);
    	pessoa.setCpf(cpf);
    	pessoa.setCro(cro);
    	pessoa.setEmail(email);
    	pessoa.setTelefoneCelular(celular);
    	pessoa.setTelefoneComercial(telefone);
    	pessoa.setTelefoneResidencial(telefone);
    	pessoa.setEstado(estado);
    	pessoa.setCep(cep);
    	
    	List<Curso> cursos = cursoDAO.listarPorNome(curso);
    	if(cursos!=null && !cursos.isEmpty()){
    		pessoa.setCursoInteresse(cursos.get(0));
    	} else{
    		LOGGER.warn("Curso "+curso+" não cadastrado!");
    		pessoa.setContato(curso);
    	}
    	
    	try{
    		pessoaDAO.adicionar(pessoa);
    		LOGGER.info("Cadastro realizado com sucesso");
    	} catch (Exception e){
    		LOGGER.error("Erro ao receber cadastro do site: "+e.getMessage());
    		e.printStackTrace();
    	}
    	
    	return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
