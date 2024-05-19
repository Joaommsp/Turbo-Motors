package br.com.turbomotors.turbomotors.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import br.com.turbomotors.turbomotors.Repositorio.CarroCrud;
import br.com.turbomotors.turbomotors.Repositorio.MarcaRepository;
import br.com.turbomotors.turbomotors.Repositorio.TipoRepositorio;
import br.com.turbomotors.turbomotors.Tabelas.Marca;
import br.com.turbomotors.turbomotors.Tabelas.Tipo;
import br.com.turbomotors.turbomotors.Tabelas.Veiculo;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class CarrosController {

    @Autowired
    private MarcaRepository acao_marcas;

    @Autowired
    private CarroCrud service;

    @Autowired
    private MarcaRepository serviceMarca;

    @Autowired
    private TipoRepositorio serviceipo;

    @Autowired
    private TipoRepositorio acao_tipo;

    @PostMapping(value = "/salvarCarro", consumes = { "multipart/form-data" })
    @CacheEvict(value = "salvarCarro", allEntries = true)
    public RedirectView criarCarros(Veiculo carros,
            @RequestParam("imagemCarro") final MultipartFile file,
            @RequestParam(name = "idd_marca", required = false) Long idMarca,
            @RequestParam(name = "idTipo", required = false) Long idTipo,
            @RequestParam(name = "disponivel", required = false) String Disponivel,
            RedirectAttributes redirecionarAtributos) throws Exception {

        String nome = file.getOriginalFilename().toLowerCase();

        if (!(nome.endsWith(".png") || nome.endsWith(".jpg") || nome.endsWith(".jpeg"))) {
            redirecionarAtributos.addFlashAttribute("meuErro", null);
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();

        UUID gerarGuid = UUID.randomUUID();
        String gerarNovoNome = gerarGuid.toString() + '_' + file.getOriginalFilename();

        String urlContexto = baseUrl + "/img/" + gerarNovoNome;

        ModelAndView minhaView = new ModelAndView("web/carro");
        Tipo meuTipo = serviceipo.findByVeiculosIdtipo(idTipo);
        carros.setTipo(meuTipo);

        Marca minhaMarca = serviceMarca.findByVeiculosPorId(idMarca);
        carros.setMarca(minhaMarca);
        boolean statusArquivo = file.isEmpty();

        if (!statusArquivo) {
            try {
                /***
                 * Author: Renê
                 * Obs: Modified on 09/03 to automatically get the file path using java.io
                 * instead of java.nio, and adapting the upload method.
                 */
                System.out.println(gerarNovoNome);
                byte[] binarioCarro = file.getBytes();
                Path minhaPasta = Paths.get(".");
                String pasta = minhaPasta.toAbsolutePath() + "src\\main\\webapp\\img\\";
                String pastaFinal = pasta.replace(".", "").concat(gerarNovoNome);
                Files.write(Paths.get(pastaFinal), binarioCarro);

                carros.setCarUrl(urlContexto);
                carros.setCarImagem(binarioCarro);
                carros.setCar_nome_arquivo(gerarNovoNome);

            } catch (IOException e) {
                redirecionarAtributos.addFlashAttribute("meuErro", e.getClass());
                System.out.println("Causa > " + e.getCause());
                return new RedirectView("/admin/carro.html", true);
            }
        }

        try {
            service.save(carros);
            redirecionarAtributos.addFlashAttribute("sucessoCarro", "Carro");

        } catch (DataIntegrityViolationException e) {
            redirecionarAtributos.addFlashAttribute("erroUnique",
                    "Atenção! A placa fornecida já está associado em um registro. Por favor, utilize outra");
            System.out.println(e.getMessage());
        }
        return new RedirectView("/admin/carro.html", true);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.ok("{\"retorno\": \"Deletado com Sucesso\"}");
        } catch (EmptyResultDataAccessException e) {
            String jsonString = "{\"retorno\":  \"Não existe esse ID, verifique e tente novamente\"}";
            System.out.println(jsonString);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonString);
        }
    }

    @PutMapping("/editar")
    public Veiculo editar(@RequestBody Veiculo carros) {
        return carros;
    }

    @PostMapping("/cadastrarMarcas")
    public ResponseEntity<?> cadastroMarcas(@RequestBody Marca nomeTeste) {
        String nomeDaMarca = nomeTeste.getMarNome();
        System.out.println(nomeTeste);
        Marca marcas = new Marca();
        marcas.setMarNome(nomeDaMarca);

        try {
            serviceMarca.save(marcas);
            return ResponseEntity.status(HttpStatus.OK).body("OK");

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar a marca: " + e.getMessage());
        }
    }

    @PostMapping("/cadastrarTipo")
    public ResponseEntity<?> cadastroTipo(@RequestBody Tipo tipoCarro) {
        Tipo tiposTipo = new Tipo();

        tiposTipo.setTipoNome(tipoCarro.getTipoNome());
        tiposTipo.setValorAluguel(tipoCarro.getValorAluguel());

        try {
            String minisculo = tipoCarro.getTipoNome().toLowerCase();

            if (serviceipo.existsByTipoNome(tipoCarro.getTipoNome()) || serviceipo.existsByTipoNome(minisculo)) {
                throw new DataIntegrityViolationException("EXISTENTE");
            }

            serviceipo.save(tiposTipo);

            return ResponseEntity.status(HttpStatus.OK).body("OK");

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar a marca: " + e.getMessage());
        }
    }

    @GetMapping("/obterCarros")
    public ResponseEntity<List<Veiculo>> obterCarrosAPI() {

        List<Veiculo> carros = service.findAll();
        System.out.print(carros);
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/carros/editar/{id}")
    public ModelAndView editarCarro(@PathVariable Long id) {
        Veiculo veiculoID = service.findByIdOrdernado(id);
        List<Marca> marcas = acao_marcas.findAll();
        List<Tipo> tipos = acao_tipo.findAll();
        ModelAndView minhaView = new ModelAndView();
        minhaView.addObject("listaMarcas", marcas);
        minhaView.addObject("idMarca", new Marca());
        minhaView.addObject("tiposCarros", tipos);
        minhaView.setViewName("/web/carros/editar");
        minhaView.addObject("carroEdit", veiculoID);
        System.out.println(veiculoID);
        return minhaView;
    }

    @PostMapping(value = "/editarCArro", consumes = { "multipart/form-data" })
    public RedirectView editarCArro(Veiculo carros,
            @RequestParam("imagemCarro") final MultipartFile file,
            @RequestParam(name = "idd_marca", required = false) Long idMarca,
            @RequestParam(name = "idTipo", required = false) Long idTipo,
            RedirectAttributes redirecionarAtributos) throws Exception {
        String urlRedirect = "carros/editar/" + carros.getIdCarro();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();

        Veiculo infoCarros = service.findByIdOrdernado(idTipo);

        UUID gerarGuid = UUID.randomUUID();
        String gerarNovoNome = gerarGuid.toString() + '_' + file.getOriginalFilename();

        String urlContexto = "";

        Boolean existeImagem;
        try {
            byte[] imageData = infoCarros.getCarImagem();
            existeImagem = imageData != null;
        } catch (NullPointerException e) {
            // Tratar a exceção (ex: log de erro, imagem padrão)
            existeImagem = false;
        }

        if (carros.getCar_nome_arquivo() == null) {
            if (existeImagem) {
                carros.setCar_nome_arquivo(infoCarros.getCar_nome_arquivo());
                carros.setCarImagem(infoCarros.getCarImagem());
                carros.setCarUrl(infoCarros.getCarUrl());
            } else {
                urlContexto = baseUrl + "/img/" + gerarNovoNome;
            }
        } 

        System.out.println("URL Contexto: " + urlContexto);

        String nome = file.getOriginalFilename().toLowerCase();

        if (!(nome.endsWith(".png") || nome.endsWith(".jpg") || nome.endsWith(".jpeg"))) {
            redirecionarAtributos.addFlashAttribute("meuErro", null);
        }

        ModelAndView minhaView = new ModelAndView("web/carro");
        Tipo meuTipo = serviceipo.findByVeiculosIdtipo(idTipo);
        carros.setTipo(meuTipo);

        Marca minhaMarca = serviceMarca.findByVeiculosPorId(idMarca);
        carros.setMarca(minhaMarca);
        boolean statusArquivo = file.isEmpty();

        if (!statusArquivo) {
            try {

                System.out.println(gerarNovoNome);
                byte[] binarioCarro = file.getBytes();
                Path minhaPasta = Paths.get(".");
                String pasta = minhaPasta.toAbsolutePath() + "src\\main\\webapp\\img\\";
                String pastaFinal = pasta.replace(".", "").concat(gerarNovoNome);
                Files.write(Paths.get(pastaFinal), binarioCarro);

                System.out.println("Entrou no Status Arquivo");
                System.out.println("URL Contexto no banco :> " + urlContexto);
                carros.setCarUrl(urlContexto);
                carros.setCarImagem(binarioCarro);
                carros.setCar_nome_arquivo(gerarNovoNome);

            } catch (IOException e) {
                redirecionarAtributos.addFlashAttribute("meuErro", e.getClass());
                System.out.println("Erro ao importar Arqivo " + e.getMessage());
                return new RedirectView(urlRedirect, true);
            }
        }

        try {
            service.save(carros);
            redirecionarAtributos.addFlashAttribute("sucessoCarro", carros);

        } catch (DataIntegrityViolationException e) {
            redirecionarAtributos.addFlashAttribute("erroUnique",
                    "Atenção! A placa fornecida já está associado em um registro. Por favor, utilize outra ");

        }
        return new RedirectView(urlRedirect);
    }

}
