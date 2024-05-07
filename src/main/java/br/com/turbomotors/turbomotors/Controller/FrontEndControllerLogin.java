package br.com.turbomotors.turbomotors.Controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import br.com.turbomotors.turbomotors.Repositorio.CarroCrud;
import br.com.turbomotors.turbomotors.Repositorio.ClienteRepositorio;
import br.com.turbomotors.turbomotors.Servico.CookieService;
import br.com.turbomotors.turbomotors.Tabelas.Cliente;
import br.com.turbomotors.turbomotors.Tabelas.Veiculo;

@Controller
@RequestMapping("/inicio")
@Transactional
public class FrontEndControllerLogin {
	@Autowired
	private ClienteRepositorio cliente;
	@Autowired 
	private CarroCrud carros;
	@Autowired 
	private CarroCrud servicosCarros;

	@GetMapping("/userClient")
	public ModelAndView showUserClientPage() {
		System.out.println("Entrou");
		return new ModelAndView("userClient.html");
	}
	@GetMapping("detalhes/{id}")
	public ModelAndView detalhesCarro(HttpServletRequest request,@PathVariable Long id) {
		String autenticado = CookieService.getCookie(request, "usuarioId");
			
		ModelAndView minhaPagina = new ModelAndView("carros");
		List<Object[]> carrosVitrine = carros.obterVeiculos(id);

		if(autenticado != null) {
		
			Long autenticadoID = Long.valueOf(autenticado);
			Cliente obterAutenticado = cliente.obterLogado(autenticadoID);
			minhaPagina.addObject("autenticado", obterAutenticado);

			
		}

		minhaPagina.addObject("meuCarro", carrosVitrine);
		return minhaPagina;
	
	}

	@GetMapping("/login")
	public ModelAndView loginusuario(){ 
		return new ModelAndView("web/perfil.html");
	}
	
	
	@GetMapping("")
	public ModelAndView inicioIndex(HttpServletRequest request) {				
		String autenticado = CookieService.getCookie(request, "usuarioId");
		ModelAndView view = new ModelAndView("inicio");
		
		Path minhaPasta = Paths.get(".");
				
		if(autenticado == null) {
			return view;
		} else {			
			Long autenticadoID = Long.valueOf(autenticado);
			Cliente obterAutenticado = cliente.obterLogado(autenticadoID);
			view.addObject("autenticado", obterAutenticado);
			return view;
			
		}
	
	}
	
	
	@GetMapping("carros")
	public ModelAndView mostrarCarros(@RequestParam(name = "model", required = false) String modelo, HttpServletRequest request) {				
		String autenticado = CookieService.getCookie(request, "usuarioId");
		ModelAndView view = new ModelAndView("/cliente/cars/cars");
		
		if(modelo != null) {
			System.out.println("ENTROU NO IF > " + modelo);
			List<Object[]> carroTipos = carros.obterCarrosTipo(modelo);
			view.addObject("carroTipos", carroTipos);
		}
		
		if(autenticado == null) {
			return view;
		} else {		
			System.out.println("Entrou no aba de Detahes, Cookie do Autenticado : " + autenticado);
			Long autenticadoID = Long.valueOf(autenticado);
			Cliente obterAutenticado = cliente.obterLogado(autenticadoID);
			view.addObject("autenticado", obterAutenticado);
			return view;
			
		}
	
	}


	@GetMapping("/filtro")
	public RedirectView getMethodName(@RequestParam String nome, RedirectAttributes redirecionarAtributos) {
		List<Veiculo> minhaLista = carros.obterCarrosDisponiveis(nome);
		if(!(minhaLista.isEmpty())) {
			redirecionarAtributos.addFlashAttribute("carroTipos", minhaLista.toString());
			return new RedirectView("/inicio/carros", true);
		}
		return new RedirectView("/inicio/carros", true);
	}
	

	@GetMapping("/ajuda")
	public ModelAndView showHelpPage(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("help.html");

		String autenticado = CookieService.getCookie(request, "usuarioId");

		if(autenticado == null) {
			return view;
		} else {			
			Long autenticadoID = Long.valueOf(autenticado);
			Cliente obterAutenticado = cliente.obterLogado(autenticadoID);
			view.addObject("autenticado", obterAutenticado);
			return view;
			
		}
	}

	
	@GetMapping("/obrigado")
	public ModelAndView showFormSubmit(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("formSubmit.html");

		String autenticado = CookieService.getCookie(request, "usuarioId");

		if(autenticado == null) {
			return view;
		} else {			
			Long autenticadoID = Long.valueOf(autenticado);
			Cliente obterAutenticado = cliente.obterLogado(autenticadoID);
			view.addObject("autenticado", obterAutenticado);
			return view;
			
		}
	}
	
	
}



    
