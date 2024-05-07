package br.com.turbomotors.turbomotors.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import br.com.turbomotors.turbomotors.Repositorio.AluguelRepository;
import br.com.turbomotors.turbomotors.Repositorio.CarrinhoRepository;
import br.com.turbomotors.turbomotors.Repositorio.CarroCrud;
import br.com.turbomotors.turbomotors.Repositorio.ClienteRepositorio;
import br.com.turbomotors.turbomotors.Repositorio.CompraRepositorio;
import br.com.turbomotors.turbomotors.Repositorio.EnderecoRepositorio;
import br.com.turbomotors.turbomotors.Repositorio.PagamentoRepositorio;
import br.com.turbomotors.turbomotors.Servico.CookieService;
import br.com.turbomotors.turbomotors.Tabelas.Aluguel;
import br.com.turbomotors.turbomotors.Tabelas.Carrinho;
import br.com.turbomotors.turbomotors.Tabelas.Cliente;
import br.com.turbomotors.turbomotors.Tabelas.Compra;
import br.com.turbomotors.turbomotors.Tabelas.Endereco;
import br.com.turbomotors.turbomotors.Tabelas.Pagamento;
import br.com.turbomotors.turbomotors.Tabelas.Tipo;
import br.com.turbomotors.turbomotors.Tabelas.Veiculo;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/cliente")
public class FrontEndController {
 
	@Autowired
	private ClienteRepositorio cliente;
	@Autowired
	private EnderecoRepositorio enderecoRepositorio;
	@Autowired 
	private CarroCrud carro_acao;
	@Autowired
	private CompraRepositorio compras;
	@Autowired 
	private PagamentoRepositorio pagamentoService;
	@Autowired
	private AluguelRepository aluguelAcao;
	@Autowired
	private CarrinhoRepository carrinhoAcao;
	
	
	@GetMapping("/sair")
	public RedirectView fazerLogout(HttpServletResponse response) {
	    Cookie cookie = new Cookie("usuarioId", null);
	    cookie.setMaxAge(0);
	    cookie.setPath("/");
	    response.addCookie(cookie);
        return new RedirectView("/inicio/", true);

	}
	
	/// Criei documentação seguida > https://reflectoring.io/spring-boot-cookies/
	@PostMapping("/login")
	public RedirectView autenticar(HttpServletResponse response, @RequestParam("email") String usuario, @RequestParam("senha") String Senha, RedirectAttributes redirecionarAtributos) {
		System.out.println("| - Login : " + LocalDate.now());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Cliente usuarios = cliente.findByEmail(usuario);
		boolean senhaCorreta;
		String primeiroAcesso = cliente.primeiroAcesso(usuario);
		if(usuarios == null) {
            redirecionarAtributos.addFlashAttribute("errorLogin", "O e-mail não existe ou está incorreto.");
            return new RedirectView("/inicio/userClient/", true);
		} else {
			senhaCorreta = encoder.matches(Senha, usuarios.getSenha());
		}
		
		  if (senhaCorreta) {
			 CookieService.setCookie(response, "usuarioId", String.valueOf(usuarios.getIdCliente()) , 36000);
			 
			 if(primeiroAcesso.equalsIgnoreCase("S")) {
				redirecionarAtributos.addFlashAttribute("mostrarAjuda", "mostrarAjuda");
				cliente.updateAcesso(usuario);
			 } 
			 redirecionarAtributos.addFlashAttribute("logadoSucesso", "semLogin");
			 return new RedirectView("/inicio/", true);


	        } else {
	        	 redirecionarAtributos.addFlashAttribute("errorLogin", "Senha incorreta!");
	           return new RedirectView("/inicio/userClient/", true);
	        }
	}

	@PostMapping("/criarCliente")
	public ResponseEntity<?> criarClienteNovo(@RequestBody Cliente clienteBody, HttpServletRequest request) {
		
		boolean existeCPF = cliente.existsByCpfCnpj(clienteBody.getCpfCnpj());
		boolean existeEmail= cliente.existsByEmail(clienteBody.getEmail());
		boolean existeTelefone = cliente.existsByTelefone(clienteBody.getEmail());
		boolean existsByNomeUsuario= cliente.existsByTelefone(clienteBody.getNomeUsuario());
		boolean existsByPesNome= cliente.existsByPesNome(clienteBody.getPesNome());

		if (clienteBody == null) {
			throw  new RuntimeException("Sem Dados!");
		}
		if (existeCPF) {
            return ResponseEntity.badRequest().body("{'error': 'Já existe um usuário com este CPF.'}");
        } else if (existeEmail) {
            return ResponseEntity.badRequest().body("{'error': 'Já existe um usuário com este E-mail.'}");
        } else if (existeTelefone) {
            return ResponseEntity.badRequest().body("{'error': 'Já existe um usuário com este Telefone.'}");
        } else if (existsByNomeUsuario) {
            return ResponseEntity.badRequest().body("{'error': 'Já existe um usuário com este Nome de Usuário.'}");
        } else if (existsByPesNome){
            return ResponseEntity.badRequest().body("{'error': 'Já existe um usuário com este usuário'}");
		} else {

			String meuCpfCnpj = clienteBody.getCpfCnpj().replaceAll("[^\\d]", ""); // Remove todos os caracteres não numéricos
			int qtdCaracteres = meuCpfCnpj.length();
			String fisicaJuridica = "F";
			
			if (qtdCaracteres >= 14) {
				fisicaJuridica = "J";
			}

			Cliente meuCliente = new Cliente();
			meuCliente.setCpfCnpj(clienteBody.getCpfCnpj());
			meuCliente.setEmail(clienteBody.getEmail());
			meuCliente.setTelefone(clienteBody.getTelefone());
			meuCliente.setNomeUsuario(clienteBody.getNomeUsuario());
			meuCliente.setFisica(fisicaJuridica);
			meuCliente.setPesNome(clienteBody.getPesNome());
			meuCliente.setSexo("F");

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();			    
			String senhaOriginal = clienteBody.getPassword();
			String senhaCodificada = encoder.encode(senhaOriginal);
			meuCliente.setSenha(senhaCodificada);		
			

			String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			String caminhoNovo = baseUrl + "/img/" +  "profile-woman.png";	
			System.out.println(caminhoNovo);
				
			meuCliente.setUrl_anexo(caminhoNovo);

			Endereco endereco = new Endereco();
			endereco.setBairro("Prencher");
			endereco.setCep("Preencher");

			// obter enderço enviado pelo json
			List<Endereco> meuEnderecoBody = clienteBody.getEnderecos();
			Endereco enderecoFormatado = meuEnderecoBody.get(0);

			List<Endereco> minhaLista = new ArrayList<>();

			minhaLista.add(endereco);
			meuCliente.setEnderecos(minhaLista);

			endereco.setNumero(enderecoFormatado.getNumero());
			endereco.setLogradouro(enderecoFormatado.getLogradouro());
			endereco.setBairro(enderecoFormatado.getBairro());
			endereco.setComplemento(enderecoFormatado.getComplemento());	
			endereco.setCep(enderecoFormatado.getCep());

			cliente.save(meuCliente);

			Cliente clienteTemp = cliente.findByEmail(clienteBody.getEmail());
			endereco.setCliente(clienteTemp);
			enderecoRepositorio.save(endereco);

			System.out.println("Salvo com Sucesso!");
			return  ResponseEntity.ok().build();
		}
	}
	

	
	@PostMapping("/compraCarro") 
	public RedirectView ComprarCarro(HttpServletRequest request, @RequestParam(name = "dataFinal", required = false) String dtFinal, @RequestParam(name = "dataInicial", required = false) String dtInicial, @RequestParam(name = "idCarro", required = false) Long idCarro, RedirectAttributes redirecionarAtributos ) {
		String autenticado = CookieService.getCookie(request, "usuarioId");
		ModelAndView minhaviewSucesso = new ModelAndView("/cliente/checkout");
		ModelAndView minhaviewError = new ModelAndView("/inicio/");

		if (autenticado == null) {
			System.out.println("-----------------------------------------------");
			 redirecionarAtributos.addFlashAttribute("erroAutentic", "erroAutentic");
			System.out.println("Cookie vazio");
			return new RedirectView("/inicio/detalhes/" + idCarro, true);
	    } else {
	    	
	    	System.out.println(autenticado);
			//Instanciar comprador
	    	Long autenticadoID = Long.valueOf(autenticado);
	    	

			Cliente meuCliente = cliente.obterLogado(autenticadoID);
			//Carro comprado
			Veiculo carroComprado = carro_acao.carroPorID(idCarro);
			Tipo meutipo = carroComprado.getTipo();
			BigDecimal valorDias = meutipo.getValorAluguel();
	    	
	    	
	    	
			if(dtFinal == null ||dtFinal.equals("")) {
				LocalDateTime hrCompra = LocalDateTime.now();
				Carrinho minhaCompra = new Carrinho();
				minhaCompra.setCliente(meuCliente);		
				minhaCompra.setVeiculo(carroComprado);
				minhaCompra.setTipoCarrinho("V");
				carrinhoAcao.save(minhaCompra);
			}
			else {
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		        LocalDateTime dataFinal = LocalDateTime.parse(dtFinal, formatter);
		        LocalDateTime dataInicia = LocalDateTime.parse(dtInicial, formatter);
		        Duration diferenca = Duration.between(dataInicia, dataFinal);
		        long dias = diferenca.toDays();
				Carrinho meuAluguel = new Carrinho();
				meuAluguel.setDataInicio(dataInicia);
				meuAluguel.setVeiculo(carroComprado);
				meuAluguel.setCliente(meuCliente);
				meuAluguel.setTipoCarrinho("A");
		        BigDecimal novoValor = BigDecimal.valueOf(dias).multiply(valorDias);
		        carrinhoAcao.save(meuAluguel);
		        
		        
			}
	    	
	    	
			return new RedirectView("/cliente/compras", true);
	    }
		
		
		
	}
	
	
	@GetMapping("/inicio")
	public ModelAndView usuario(HttpServletRequest request) {
		String autenticado = CookieService.getCookie(request, "usuarioId");
		
		System.out.println(autenticado);
		
		ModelAndView minhaviewSucesso = new ModelAndView("/cliente/logado");
		ModelAndView minhaviewError = new ModelAndView("/inicio/");

		if (autenticado == null) {
			
			System.out.println("Cookie vazio");
	        return minhaviewError;
	    } else {
	    	System.out.println("cooki encontrado :" + autenticado);
	        return minhaviewSucesso;
	    }
	}
	
	@GetMapping("/usuario")
	public ModelAndView usuariosautentica(HttpServletRequest request) {
		String autenticado = CookieService.getCookie(request, "usuarioId");
		
		ModelAndView minhaviewSucesso = new ModelAndView("/cliente/user/user");
		ModelAndView minhaviewError = new ModelAndView("/inicio/");

		if (autenticado == null) {
			System.out.println("Cookie vazio");
	        return minhaviewError;
	    } else {
	    	
	    	Long autenticadoID = Long.valueOf(autenticado);
			Cliente obterAutenticado = cliente.obterLogado(autenticadoID);
			List<Object[]> end = enderecoRepositorio.findByEndderecoCliente(autenticadoID);
			List<Object[]> shoppInfoList = cliente.obterShopp(autenticadoID);
			minhaviewSucesso.addObject("autenticado", obterAutenticado);
			minhaviewSucesso.addObject("enderecos", end);
			minhaviewSucesso.addObject("shoppInfoList", shoppInfoList);
	    	System.out.println("cooki encontrado :" + autenticado);
	        return minhaviewSucesso;
	    }
	}
	
	
	@GetMapping("compras")
	public ModelAndView mostrarCheckout(HttpServletRequest request) { 
		String autenticado = CookieService.getCookie(request, "usuarioId");		
		ModelAndView minhaviewSucesso = new ModelAndView("/cliente/checkout");
		ModelAndView viewError = new ModelAndView("/inicio/");
		
		if (autenticado == null) {
			System.out.println("Cookie vazio");
	        return viewError;
	    } else {
	    	Long autenticadoID = Long.valueOf(autenticado);
			Cliente obterAutenticado = cliente.obterLogado(autenticadoID);
			List<Object[]> carrosInfo = carrinhoAcao.carrinhoCliente(autenticadoID);

			if(carrosInfo.isEmpty()) {
				minhaviewSucesso.addObject("carrinhoVazio", "carrinhoVazio");

			}

			List<Object[]> enderecoNome = cliente.obterEnderecoClienteCompra(autenticadoID);
			Double totalCompra  = compras.calcularTotalCompra(autenticadoID);
			minhaviewSucesso.addObject("autenticado", obterAutenticado);
			minhaviewSucesso.addObject("carros", carrosInfo);
			minhaviewSucesso.addObject("enderecoNome", enderecoNome);
			minhaviewSucesso.addObject("totalCompra", totalCompra);
	    	System.out.println("cooki encontrado :" + autenticado);
	        return minhaviewSucesso;
	    }
	}
	
	@PostMapping("criarPagamento")
	public RedirectView pagar(@RequestParam(value = "valorTotal", required = false) BigDecimal ValorTotal, @RequestParam(name = "id_cliente", required = false) Long id_Cliente,  Pagamento pago)  {
		
		System.out.println("ENTROU NO PAGAMENTO");

		Cliente obterCliente = cliente.findByIdCliente(id_Cliente);


		List<Carrinho> meuCarrinho = carrinhoAcao.obterCarrinho(obterCliente);

		for(Carrinho item : meuCarrinho) {
			if(item.getTipoCarrinho().equals("V")) {
				Compra minhaCompra = new Compra();
				minhaCompra.setCliente(item.getCliente());
				minhaCompra.setDataCompra(LocalDateTime.now());
				minhaCompra.setStatus("A");
				minhaCompra.setVeiculo(item.getVeiculo());
				Veiculo meucarro = item.getVeiculo();
				minhaCompra.setValor(meucarro.getCarValor());
				compras.save(minhaCompra);
				carrinhoAcao.delete(item);
			} else {

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		        Duration diferenca = Duration.between(item.getDataInicio(), item.getDataFim());
		        long dias = diferenca.toDays();
				Veiculo meucarro = item.getVeiculo();
				Tipo meuTipo = meucarro.getTipo();
				BigDecimal novoValor = BigDecimal.valueOf(dias).multiply(meuTipo.getValorAluguel());

				Aluguel meuAluguel = new Aluguel();
				meuAluguel.setCliente(item.getCliente());
				meuAluguel.setDataFim(item.getDataFim());
				meuAluguel.setDataInicio(item.getDataInicio());
				meuAluguel.setVeiculo(item.getVeiculo());
				meuAluguel.setValorHora(novoValor);
				meuAluguel.setStatus("A");
				aluguelAcao.save(meuAluguel);
				carrinhoAcao.delete(item);

			}
		}

				
		List<Object[]> compraCliente = compras.comprasDoCliente(id_Cliente);
		List<Object[]> aluguelCliente = aluguelAcao.aluguelCliente(id_Cliente);
		
		Boolean aluguelVazio = aluguelCliente.isEmpty();
		Boolean compraVazia = compraCliente.isEmpty();
		Long idCliente = null;

		
	if(!compraVazia) {	
		for (Object[] compra : compraCliente) {
		    idCliente = ((Number) compra[0]).longValue();
		    Compra comprasClientes = compras.minhaCompra(idCliente);
		    pago.setCompra(comprasClientes);
		    pago.setId_cliente(id_Cliente);
		    LocalDateTime dataAtual = LocalDateTime.now();  
		    pago.setDataPagamento(dataAtual);
		    pago.setValorTotal(comprasClientes.getValor());
		    pago.setValorTotal(ValorTotal);
		    pagamentoService.save(pago);
		}
	}
	
	if(!aluguelVazio) {	

		for (Object[] aluguel : aluguelCliente) {
		    idCliente = ((Number) aluguel[0]).longValue();
		    
		    
		    Aluguel meuAluguel = aluguelAcao.meusAlugueis(idCliente);
		    
		    pago.setAluguel(meuAluguel);
		    pago.setId_cliente(id_Cliente);
		    LocalDateTime dataAtual = LocalDateTime.now();  
		    pago.setDataPagamento(dataAtual);
		    pago.setValorTotal(meuAluguel.getValorHora());
		    pago.setValorTotal(ValorTotal);
		    pagamentoService.save(pago);
		}
	}
		return new RedirectView("/cliente/usuario", true);
			
		
	}

	
   
   @PostMapping(value = "/editarCliente", consumes = {"multipart/form-data"})
   public RedirectView editarClientes(Cliente clientes, 
                                   @RequestParam("imagemCarro") final MultipartFile file,
                                   RedirectAttributes redirecionarAtributos) throws Exception {

	   Cliente meuCliente = cliente.findByIdCliente(clientes.getIdCliente());
      

	   // Esquema para caso  ele nao altere a foto, o front não remova automaticamente

	   if(file.isEmpty()){
			if(meuCliente.getFotoCliente() != null ) {
				clientes.setFotoCliente(meuCliente.getFotoCliente());
				clientes.setUrl_anexo(meuCliente.getUrl_anexo());
			}
		}

		if(clientes.getSenha() == null)  {
			if(meuCliente.getSenha() != null){
				clientes.setSenha(meuCliente.getSenha());
			}
		}
	   
	   
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

       boolean statusArquivo = file.isEmpty();

       if (!statusArquivo) {
           try {
        	 
             
        	   System.out.println(gerarNovoNome);
               byte[] binarioCarro = file.getBytes();
        	   Path minhaPasta = Paths.get(".");
               String pasta = minhaPasta.toAbsolutePath() + "\\src\\main\\webapp\\img\\";
               String pastaFinal = pasta.replace(".", "").concat(gerarNovoNome);               
               Files.write(Paths.get(pastaFinal), binarioCarro);
              
			
			   clientes.setUrl_anexo(urlContexto);
			   clientes.setFotoCliente(binarioCarro); 
               

               
           } catch(IOException e) {
               redirecionarAtributos.addFlashAttribute("meuErro", e.getClass());
               System.out.println("Erro ao importar Arqivo " + e.getMessage() );
               return new RedirectView("/cliente/usuario", true);
           }
       }

       try {
        cliente.save(clientes);
        redirecionarAtributos.addFlashAttribute("sucessoCliente", clientes);

    } catch(DataIntegrityViolationException   e) {
        redirecionarAtributos.addFlashAttribute("erroUnique", clientes);

    }
           return new RedirectView("/cliente/usuario");
   }


   @PostMapping(value = "/editarEndereco")
   public RedirectView editarEndereco(Endereco enderecos, RedirectAttributes redirecionarAtributos, @RequestParam(name = "id_cliente", required = false) Long idCliente) {

		Cliente meuCliente = cliente.findByIdCliente(idCliente);
		enderecos.setCliente(meuCliente);
		enderecoRepositorio.save(enderecos);
        redirecionarAtributos.addFlashAttribute("sucessoCliente", meuCliente);
		return new RedirectView("/cliente/usuario");

   }

	
   
}



    
