package br.com.turbomotors.turbomotors.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.turbomotors.turbomotors.DTO.UsuarioLogadoDTO;
import br.com.turbomotors.turbomotors.Repositorio.FuncionarioRepositorio;

@Controller
@RequestMapping("/admin")
public class UsuarioLogado {
	
	@Autowired
	private FuncionarioRepositorio acao;
	
	
	@GetMapping("/usuarioLogado")
	public ResponseEntity<UsuarioLogadoDTO> obterUserLogado() {
			Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		    UsuarioLogadoDTO func = acao.obterUsuarioLogado(autenticado.getName());
			return ResponseEntity.ok(func);
	}

}
