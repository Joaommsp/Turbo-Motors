package br.com.turbomotors.turbomotors.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import br.com.turbomotors.turbomotors.Repositorio.CargoRepositorio;
import br.com.turbomotors.turbomotors.Repositorio.FuncionarioRepositorio;
import br.com.turbomotors.turbomotors.Tabelas.Cargo;
import br.com.turbomotors.turbomotors.Tabelas.Funcionarios;
import br.com.turbomotors.turbomotors.Tabelas.Marca;
import br.com.turbomotors.turbomotors.Tabelas.Tipo;
import br.com.turbomotors.turbomotors.Tabelas.Veiculo;

@RestController
@RequestMapping("/admin")
@Transactional
public class FuncionarioController {

		
	@Autowired
	private CargoRepositorio cargo_service;

	@Autowired
	private FuncionarioRepositorio func_service;
	
	@PostMapping (value = "/editarPerfil", consumes = {"multipart/form-data"})
    @CacheEvict(value = "editarPerfil", allEntries = true)
	public RedirectView salvarPerfil(Funcionarios funcionario, 
                    @RequestParam("imagemFuncionario") final MultipartFile file, 
                    @RequestParam(name = "idCargo", required = false) Long idCargo,
                    RedirectAttributes redirecionarAtributos) throws Exception {
					String nome = file.getOriginalFilename().toLowerCase();
					
					Funcionarios meuFunc = func_service.obterUsuario(funcionario.getUser());
					
					
					if (!(nome.endsWith(".png") || nome.endsWith(".jpg") || nome.endsWith(".jpeg"))) {
					redirecionarAtributos.addFlashAttribute("meuErro", null);
					}
					
					ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
					HttpServletRequest request = attributes.getRequest();
					String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					UUID gerarGuid = UUID.randomUUID();
					String gerarNovoNome = gerarGuid.toString() + '_' + file.getOriginalFilename();
					String cc = baseUrl + "/img/" + gerarNovoNome;
					
					
					
					
					ModelAndView minhaView = new ModelAndView("web/funcionario");
					
					Cargo meuCargo =  cargo_service.procurarPorID(idCargo);
					funcionario.setCargoBean(meuCargo);
					

					boolean statusArquivo = file.isEmpty();
					
					if (!statusArquivo) {
					try {
			        	   System.out.println(gerarNovoNome);
			               byte[] binarioCarro = file.getBytes();
			        	   Path minhaPasta = Paths.get(".");
			               String pasta = minhaPasta.toAbsolutePath() + "src\\main\\webapp\\img\\";
			               String pastaFinal = pasta.replace(".", "").concat(gerarNovoNome);               
			               Files.write(Paths.get(pastaFinal), binarioCarro);
			              

					funcionario.setUrl_anexo(cc);
					funcionario.setFotoFuncionario(binarioCarro);

					
					} catch(IOException e) {
					redirecionarAtributos.addFlashAttribute("meuErro", e.getClass());
					System.out.println("Causa > " + e.getCause());
					return new RedirectView("/admin/funcionario", true);
					}
					}
					
					if(funcionario.getPassword().isEmpty()) {
						funcionario.setPassword(meuFunc.getPassword());
					} else {
						BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();			    
						String senhaOriginal = funcionario.getPassword();
					    String senhaCodificada = encoder.encode(senhaOriginal);
					    funcionario.setPassword(senhaCodificada);					
					}
					
				
				    
				    
				     
				     func_service.save(funcionario);
					
				     
				     redirecionarAtributos.addFlashAttribute("sucessoCarro", funcionario);
					return new RedirectView("/admin/funcionario", true);
					}
	
	@PostMapping (value = "/criarPerfil", consumes = {"multipart/form-data"})
    @CacheEvict(value = "criarPerfil", allEntries = true)
	public RedirectView criarPerfil(Funcionarios funcionario, 
                    @RequestParam("imagemFuncionario") final MultipartFile file, 
                    @RequestParam(name = "idCargo", required = false) Long idCargo,
                    RedirectAttributes redirecionarAtributos) throws Exception {
					String nome = file.getOriginalFilename().toLowerCase();
									
					
					if (!(nome.endsWith(".png") || nome.endsWith(".jpg") || nome.endsWith(".jpeg"))) {
					redirecionarAtributos.addFlashAttribute("meuErro", null);
					}
					
					ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
					HttpServletRequest request = attributes.getRequest();
					String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
					UUID gerarGuid = UUID.randomUUID();
					String gerarNovoNome = gerarGuid.toString() + '_' + file.getOriginalFilename();
					String urlContexto = baseUrl + "/img/" + gerarNovoNome;
					
					
					
					
					ModelAndView minhaView = new ModelAndView("web/CadastrarUsuario");
					
					Cargo meuCargo =  cargo_service.procurarPorID(idCargo);
					funcionario.setCargoBean(meuCargo);
					

					boolean statusArquivo = file.isEmpty();
					
					if (!statusArquivo) {
					try {
			        	   System.out.println(gerarNovoNome);
			               byte[] binarioCarro = file.getBytes();
			        	   Path minhaPasta = Paths.get(".");
			               String pasta = minhaPasta.toAbsolutePath() + "src\\main\\webapp\\img\\";
			               String pastaFinal = pasta.replace(".", "").concat(gerarNovoNome);               
			               Files.write(Paths.get(pastaFinal), binarioCarro);
			              
				
					funcionario.setUrl_anexo(urlContexto);
					funcionario.setFotoFuncionario(binarioCarro);
					
					
					} catch(IOException e) {
					redirecionarAtributos.addFlashAttribute("meuErro", e.getClass());
					System.out.println("Causa > " + e.getCause());
					return new RedirectView("/admin/CadastrarUsuario", true);
					}
					}
					

						BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();			    
						String senhaOriginal = funcionario.getPassword();
					    String senhaCodificada = encoder.encode(senhaOriginal);
					    funcionario.setPassword(senhaCodificada);					
					
				
				
				     
				     func_service.save(funcionario);
					
				     
				     redirecionarAtributos.addFlashAttribute("sucessoCarro", funcionario);
					return new RedirectView("/admin/CadastrarUsuario", true);
					}

		
	
	
}
