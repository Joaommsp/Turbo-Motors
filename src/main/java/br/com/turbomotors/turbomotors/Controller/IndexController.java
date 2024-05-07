package br.com.turbomotors.turbomotors.Controller;


import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.turbomotors.turbomotors.DTO.MarcasDTO;
import br.com.turbomotors.turbomotors.DTO.UsuarioLogadoDTO;
import br.com.turbomotors.turbomotors.Repositorio.AluguelRepository;
import br.com.turbomotors.turbomotors.Repositorio.CargoRepositorio;
import br.com.turbomotors.turbomotors.Repositorio.CarroCrud;
import br.com.turbomotors.turbomotors.Repositorio.CompraRepositorio;
import br.com.turbomotors.turbomotors.Repositorio.FuncionarioRepositorio;
import br.com.turbomotors.turbomotors.Repositorio.MarcaRepository;
import br.com.turbomotors.turbomotors.Repositorio.TipoRepositorio;
import br.com.turbomotors.turbomotors.Tabelas.Cargo;
import br.com.turbomotors.turbomotors.Tabelas.Funcionarios;
import br.com.turbomotors.turbomotors.Tabelas.Marca;
import br.com.turbomotors.turbomotors.Tabelas.Tipo;
import br.com.turbomotors.turbomotors.Tabelas.Veiculo;


@Controller
@RequestMapping("/admin")
@Transactional
public class IndexController {

    @Autowired
    private CarroCrud servicos;    
    @Autowired
    private MarcaRepository acao_marcas;
    @Autowired
    private TipoRepositorio acao_tipo;
    @Autowired
    private CompraRepositorio compraServices;
    
    @Autowired
	private CargoRepositorio cargo_service;
    @Autowired
    private FuncionarioRepositorio funcacao;
    
    @Autowired
    private AluguelRepository aluguel_service;
    

    @GetMapping({"","login",  "/logout"})
    public String index() { 	
//      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//     String senhaOriginal = "123";
//
//       String senhaCodificada = encoder.encode(senhaOriginal);
//        
//        System.out.print(senhaCodificada);
    	
        return "login";
    }


    @GetMapping("/funcionario")
    public ModelAndView funcionarios(){
    	Authentication autenticados = SecurityContextHolder.getContext().getAuthentication();
    	Funcionarios meuFuncionario = (Funcionarios) autenticados.getPrincipal();
    	
    	List<Cargo> meuCargo = cargo_service.findAll();
    	
    	
    	ModelAndView view = new ModelAndView();
    	view.addObject("meufunc", meuFuncionario);    	
    	view.addObject("cargoslist", meuCargo);
    	view.setViewName("/web/funcionario");
        return view;
    }
    
    
    @GetMapping("/CadastrarUsuario")
    public ModelAndView cadastrarUsuario(){
    	List<Cargo> meuCargo = cargo_service.findAll();


        List<Funcionarios> meuFuncionarios = funcacao.findAll();

        


    	ModelAndView view = new ModelAndView();
    	view.addObject("cargoslist", meuCargo);
        view.addObject("meuFuncionarios", meuFuncionarios);
    	view.setViewName("/web/CadastrarUsuario");
        return view;
    }

    
    
    
    
    
    @GetMapping("/parametros")
    public String parametros(){
        return "/web/parametros";
    }

        @GetMapping("/relatorios")
    public String gerarRelatorios(){
        return "/web/relatorios";
    }

    @GetMapping({"/painel", "/painel.html"})    
    public ModelAndView painel() {
    	
    	Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
    	String username = autenticado.getName();
    	UsuarioLogadoDTO func = funcacao.obterUsuarioLogado(username);


        LocalDate minhaData = LocalDate.now();
        int meuAno = minhaData.getYear();
        int meuMes = minhaData.getMonthValue();

        int mesPassado = meuMes == 1 ? 12 : meuMes - 1;

    	
    	 NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        Double obterVendasMensal = compraServices.obterVendasMensal(meuMes);
        Double obterVendasAnual =  compraServices.obterVendasAnual(meuAno);

        Double obterVendasMensalAnterior = compraServices.obterVendasMensal(mesPassado);
        
        long vendasRealizadas = compraServices.quantidadeDeCarrosVendidos();
        long vendasCanceladas = compraServices.quantidadeDeCarrosCancelados();
         
       
        ModelAndView minhaView = new ModelAndView();

    
       
        String valorMensalAnterior = obterVendasMensalAnterior != null ? formatoMoeda.format(obterVendasMensalAnterior) : "Mês Passado: R$ 0.0";
        String valorMensal = obterVendasMensal != null ? formatoMoeda.format(obterVendasMensal) : "R$ 0.0";
        String valorAnual =  obterVendasAnual != null ? formatoMoeda.format(obterVendasAnual) : "R$ 0.0";
        

        // Renê 16-03 para remover exceção de null pointer exceptin!/


        double porcentagem = 0d;
        if(obterVendasMensal != null && obterVendasMensalAnterior != null) {
             porcentagem = ((obterVendasMensal - obterVendasMensalAnterior) / obterVendasMensalAnterior) * 100;
        } 
               
        System.out.println( porcentagem + "%");
        
        Map<String, Object> dadosVendas = new HashMap<>();
        
        dadosVendas.put("porcentagem", porcentagem + "%");
        dadosVendas.put("mesPassado", "Mês Passado: " + valorMensalAnterior);
        dadosVendas.put("vendasMensal", valorMensal);
        dadosVendas.put("vendasAnual", valorAnual);
        dadosVendas.put("vendasRealizadas", vendasRealizadas);
        dadosVendas.put("vendasCanceladas", vendasCanceladas);


        System.out.println(dadosVendas);

        String primeiroAcesso = func.getPrimeiroAcesso();

        if(primeiroAcesso.equalsIgnoreCase("S") || primeiroAcesso.equals("")) {
            minhaView.addObject("mostrarIntro", func);
            funcacao.updateAcesso(func.getUser(), "N");
        }
            
        minhaView.setViewName("/web/painel");
        minhaView.addObject("dadosVendas", dadosVendas);
        minhaView.addObject("dadosFunc", func);

        
        return minhaView;
    }

    
    @GetMapping( "/listarFinanceiro")
    public ModelAndView listarFincaceiro() {
    	List<Object[]> comprasFinalizada = compraServices.comprasFinalizadas();
    	List<Object[]> alugados = aluguel_service.aluguelDetalhes();
        ModelAndView view = new ModelAndView();
        view.setViewName("/web/listarFinanceiro");
        view.addObject("compraFinalizadas", comprasFinalizada);
        view.addObject("alugados", alugados);
        return view;
    }
    
    
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
    @GetMapping("/login-incorreto")
     public ModelAndView retornarError() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/login");
        view.addObject("erroSenha", view);
        return view;
        
    }

    @GetMapping("/obterMarcasTop")
    public ResponseEntity obterMarcasMaisVendidas() {
        List<MarcasDTO>  compras =  compraServices.marcasCompradas();
  
		return ResponseEntity.ok(compras);
    
    }


    @GetMapping("/carrosMaisComprados")
    public ResponseEntity carrosMaisComprados()  {
        List<MarcasDTO>  tiposMaisComprados =  compraServices.tiposComprados();
        return ResponseEntity.ok(tiposMaisComprados);
    }

    @GetMapping({"/carro.html", "/carro"})
    @CacheEvict(value = "carro", allEntries = true)
    public ModelAndView cadastroCarro(@RequestParam(name = "idCarro", required = false) Long idTipo) {

        System.out.println("----------------- LOG DO SISTEMA -------------------------");
        List<Marca> marcas = acao_marcas.findAll();
        List<Tipo> tipos = acao_tipo.findAll();
        List<Veiculo> carrosDoSistema = servicos.findAll();

        System.out.println(marcas);

        Long maximoDeRegistro = servicos.ultimoRegistro();
       
        

        if (idTipo == null) {
            idTipo = maximoDeRegistro;
        }
        
        ModelAndView minhaView = new ModelAndView("web/carro");
        minhaView.addObject("listaMarcas", marcas);
        minhaView.addObject("idMarca", new Marca()); 
        minhaView.addObject("tiposCarros", tipos);
        minhaView.addObject("carrosDoSistema", carrosDoSistema);
        
        boolean verificarVeiculo = servicos.existsById(idTipo);
       
        if(verificarVeiculo == true) {
        
           Veiculo carroPrincipaVeiculo = servicos.findByIdOrdernado(idTipo);
           minhaView.addObject("carroPadrao", carroPrincipaVeiculo);
           System.out.println( "Carro : " + carroPrincipaVeiculo);

        } else {
        	Veiculo carroPrincipaVeiculo = new Veiculo();
        	minhaView.addObject("carroPadrao", carroPrincipaVeiculo);
        }
              
        return minhaView;
   }
    
    
    
    
    
}
