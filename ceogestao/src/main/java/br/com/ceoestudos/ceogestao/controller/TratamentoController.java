package br.com.ceoestudos.ceogestao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TratamentoController {
    
    @RequestMapping("novoTratamento")
    public String novoTratamento(){
        return "formTratamento";
    }
    
}
