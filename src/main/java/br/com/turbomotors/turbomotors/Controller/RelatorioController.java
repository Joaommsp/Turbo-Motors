package br.com.turbomotors.turbomotors.Controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.turbomotors.turbomotors.JasperUtil.IntegrarJasper;

@RestController
@RequestMapping("/admin")
public class RelatorioController {
    
 
    @Autowired
    private IntegrarJasper relatorioservico;
 
    @GetMapping("/gerarRelatorio")
    public void imprimirPDF(@RequestParam(name = "siglaFiltro", required = false) String filtroRelatorio,@RequestParam(name = "nomeRelatorio", required = false) String nomeRelatorio,  HttpServletRequest request, HttpServletResponse  response) throws Exception{
    	
    	UUID guid = UUID.randomUUID(); 
    	String saidaRelatorio = guid + "_" + nomeRelatorio + ".pdf";
        String filtro = filtroRelatorio;
        System.out.println("Entrou aqui");
        byte[] pdf = relatorioservico.gerarRelatorio(filtro, nomeRelatorio, request.getServletContext());
        response.setContentLength(pdf.length);
        response.setContentType("contetion/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue   = String.format("attachment; filename=\"%s\"", saidaRelatorio);
        response.setHeader(headerKey, headerValue);
        response.getOutputStream().write(pdf);
    }
}
