console.log('Executando JavaScript Geral....');


document.addEventListener("DOMContentLoaded", function(event) {
   
  const showNavbar = (toggleId, navId, bodyId, headerId) =>{
  const toggle = document.getElementById(toggleId),
  nav = document.getElementById(navId),
  bodypd = document.getElementById(bodyId),
  headerpd = document.getElementById(headerId)
  
  // Validate that all variables exist
  if(toggle && nav && bodypd && headerpd){
  toggle.addEventListener('click', ()=>{
  // show navbar
  nav.classList.toggle('show')
  // change icon
  toggle.classList.toggle('bx-x')
  // add padding to body
  bodypd.classList.toggle('body-pd')
  // add padding to header
  headerpd.classList.toggle('body-pd')
  })
  }
  }
  
  showNavbar('header-toggle','nav-bar','body-pd','header')
  
  /*===== LINK ACTIVE =====*/
  const linkColor = document.querySelectorAll('.nav_link')
  
  function colorLink(){
  if(linkColor){
  linkColor.forEach(l=> l.classList.remove('active'))
  this.classList.add('active')
  }
  }
  linkColor.forEach(l=> l.addEventListener('click', colorLink))
  
   // Your code to run since DOM is loaded and ready
  });


function mostrarSave() {
  const btnContainer = document.getElementById('btnContainer-bef');
  const buttonSave = document.querySelector('.button-save');
  btnContainer.style.display = 'none';
  buttonSave.style.display = 'block';
  desbloquearForm()

  return false;
}


function mostrarEdit() {
  const btnContainer = document.getElementById('btnContainer-bef');
  const buttonSave = document.querySelector('.button-save');
  btnContainer.style.display = 'none';
  buttonSave.style.display = 'block';
  desbloquearFormEdit()

  return false;
}

function desbloquearFormEdit() {
  const elementosBloqueados = document.querySelectorAll('.bloqueado');
  // Método da net para desabilitar o pointerEvents. 
  elementosBloqueados.forEach(elemento => {
    elemento.style.pointerEvents = 'auto';
  });
}



function desbloquearForm() {
  const elementosBloqueados = document.querySelectorAll('.bloqueado');

  var formulario = document.getElementById("formularioRegistrosJS");

  // Limpa cada campo do formulário
  for (var i = 0; i < formulario.elements.length; i++) {
    if (formulario.elements[i].type !== "button" && formulario.elements[i].type !== "submit") {
      formulario.elements[i].value = "";
    }
  }
  // Método da net para desabilitar o pointerEvents. 
  elementosBloqueados.forEach(elemento => {
    elemento.style.pointerEvents = 'auto';
  });
}


function retornarDivCadastro() {
  // ao contrario da outra. 
  const btnContainer = document.getElementById('btnContainer-bef');
  const buttonSave = document.querySelector('.button-save');
  const elementosBloqueados = document.querySelectorAll('.bloqueado');
  // Método da net para desabilitar o pointerEvents. 
  elementosBloqueados.forEach(elemento => {
    elemento.style.pointerEvents = 'none';
  });


  btnContainer.style.display = 'block';
  buttonSave.style.display = 'none';
  document.getElementById("formularioRegistrosJS").reset();
  document.getElementById('image-preview').reset();

  return false;
}



// Documentação https://getbootstrap.com/docs/5.0/components/toasts/ -- REMOVIDO
function MostrarAviso() {

  let usrLogado = document.getElementById('usuarioLogado').textContent;
  let mensagemNotificacao = document.getElementById('avisoMensagem');
  var dataAtual = new Date();
  var horaAtual = dataAtual.getHours();
  var mensagemHoras;
  let displayStyle = window.getComputedStyle(mensagemNotificacao).display;
  var mensagemFinal;
  if(displayStyle == 'none') {
     mensagemFinal = 'Sem notificação no momento!'

  }else {

  if (horaAtual >= 5 && horaAtual < 12) {
    mensagemHoras = "Bom dia";
  } else if (horaAtual >= 12 && horaAtual < 18) {
    mensagemHoras = "Boa tarde";
  } else {
    mensagemHoras = "Boa noite";
  }

   mensagemFinal = mensagemHoras + " " + usrLogado.replace("Usuário: ", "")

}


  const Toast = Swal.mixin({
    toast: true,
    position: "bottom-end",
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.onmouseenter = Swal.stopTimer;
      toast.onmouseleave = Swal.resumeTimer;
    }
  });
  Toast.fire({
    icon: "info",
    title: mensagemFinal
  });

  avisoMensagem.style.display='none'






}

function showImagePreview() {
  const input = document.getElementById('car_imagem');
  const preview = document.getElementById('adicionarImagem');
  console.log(input.files[0]);
  if (input.files && input.files[0]) {
    const reader = new FileReader();
    reader.onload = function (e) {
      preview.innerHTML = `<img src="${e.target.result}" class="img-thumbnail" width="200" />`;
    };

    reader.readAsDataURL(input.files[0]);
  } else {
    preview.innerHTML = '';
  }
}

function showImagePreview() {
  const input = document.getElementById('car_imagem');
  const preview = document.getElementById('adicionarImagem');
  console.log(input.files[0]);
  if (input.files && input.files[0]) {
    const reader = new FileReader();
    reader.onload = function (e) {
      preview.innerHTML = `<img src="${e.target.result}" class="img-thumbnail" width="200" />`;
    };

    reader.readAsDataURL(input.files[0]);
  } else {
    preview.innerHTML = '';
  }
}

function showImagePreviewFuncionario() {
  const input = document.getElementById('imagemFuncionario');
  const preview = document.getElementById('adicionarImagem');
  console.log(input.files[0]);
  if (input.files && input.files[0]) {
    const reader = new FileReader();
    reader.onload = function (e) {
      preview.innerHTML = `<img src="${e.target.result}" class="img-thumbnail" width="200" />`;
    };

    reader.readAsDataURL(input.files[0]);
  } else {
    preview.innerHTML = '';
  }
}



// Documentação do SweetAlert2 https://sweetalert2.github.io/
function confirmSubmit(action) {
  Swal.fire({
    title: 'Você tem certeza?',
    text: "Essa ação é irreversível!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Sim, confirmar!'
  }).then((result) => {
    if (result.isConfirmed) {
      // document.getElementById("seuFormId").action = action;
      // document.getElementById("seuFormId").submit();
      Swal.fire(
        'Sucesso!',
        'O Registro foi deletado com sucesso.!',
        'success'
      )
    }
  });
}

// https://bootstrap-menu.com/detail-sidebar-nav-collapse.html
document.addEventListener("DOMContentLoaded", function () {
  document.querySelectorAll('.sidebar .nav-link').forEach(function (element) {

    element.addEventListener('click', function (e) {

      let nextEl = element.nextElementSibling;
      let parentEl = element.parentElement;

      if (nextEl) {
        e.preventDefault();
        let mycollapse = new bootstrap.Collapse(nextEl);

        if (nextEl.classList.contains('show')) {
          mycollapse.hide();
        } else {
          mycollapse.show();
          // find other submenus with class=show
          var opened_submenu = parentEl.parentElement.querySelector('.submenu.show');
          // if it exists, then close all of them
          if (opened_submenu) {
            new bootstrap.Collapse(opened_submenu);
          }
        }
      }
    }); // addEventListener
  }) // forEach
});
// DOMContentLoaded  end



// Função para exibir um alert ao clicar no ícone de menu
function menu() {

  var menu = document.querySelector('.barraLateral');

  var retorno = menu.getAttribute('style');
  console.log(retorno);
  if (retorno == 'display: none;') {
    menu.style.display = 'block'
  } else {
    menu.style.display = 'none'
  }
}

// Função para verificar se é um dispositivo móvel
function isMobileDevice() {
  return window.innerWidth <= 767;
}

// Função para abrir/fechar a barra lateral
function toggleSidebar() {
  const sidebar = document.querySelector('.barraLateral');
  const isMobile = isMobileDevice();
  var computedStyle = window.getComputedStyle(sidebar);
  var displayValue = computedStyle.getPropertyValue('display');
  console.log(displayValue);

  // Adiciona ou remove a classe "fixa" com base no tamanho da tela
  if (isMobile) {
    sidebar.style.display = '';
    sidebar.classList.toggle('fixa');
    console.log('Mobile Starting....');
  }

  if (!isMobile && displayValue !== 'none') {
    sidebar.style.display = 'none';
  }

  if (!isMobile && displayValue == 'none') {
    sidebar.style.display = 'block';

  }

  sidebar.classList.toggle('aberta');
}


function salvarMarca() {
  $('#cadastrarMarcaModal').modal('hide');
}

function salvarColumns() {
  $('#cadastrarColumnsModal').modal('hide');
}

async function executarFuncaoAssincrona(formulario, idInput) {
  const meuForm = document.getElementById(formulario);
  const nomeDoTipo = document.getElementById(idInput).value;
  const valorTipo = document.getElementById('valor_aluguel').value;
  console.log(valorTipo);
  const nomeExceptionDiv = document.getElementById("classError");
  const divPrincipal = document.getElementById("divModalPrinicpal");

  const Toast = Swal.mixin({
    toast: true,
    position: "center",
    showConfirmButton: true,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.onmouseenter = Swal.stopTimer;
      toast.onmouseleave = Swal.resumeTimer;
    }
  });

  try {
    var texto = idInput == 'mar_nomeCadastro' ? "Marca" : "Tipo"
    var url = idInput == 'mar_nomeCadastro' ? '/admin/cadastrarMarcas' : "/admin/cadastrarTipo";


    var corpoReqst;

    if (idInput == "mar_nomeCadastro") {
      corpoReqst = JSON.stringify({ marNome: nomeDoTipo })
    } else {
      corpoReqst = JSON.stringify({ tipoNome: nomeDoTipo, valorAluguel: valorTipo })
      console.log(corpoReqst)

    }


    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: corpoReqst
    });

    console.log(response)
    if (response.status === 200) {
      Toast.fire({
        icon: "success",
        title: texto + " cadastrado com sucesso!"
      });

    } else {
      throw new Error(texto + " já existente no sistema.");
    }
  } catch (error) {
    Toast.fire({
      icon: "warning",
      title: error
    });
  }
}
function deletarCarro(idCarro) {
  console.log('Excluindo Carro: ' + idCarro);
  const url = '/admin/deletar/' + idCarro;

  fetch(url, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      // Add other headers if needed
    },
    // Add request body if needed
  })
    .then(response => {
      console.log('Status da resposta:', response.status);
      return response.json();
    })
    .then(data => {
      console.log('Dados da resposta:', data);

      const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: toast => {
          toast.onmouseenter = Swal.stopTimer;
          toast.onmouseleave = Swal.resumeTimer;
        },
      });
      Toast.fire({
        icon: 'success',
        title: 'Carro excluído com sucesso.',
      });

      if (!response.ok) {
        throw new Error('Erro ao excluir o carro');
      }
      console.log('Carro excluído com sucesso:', data);
    })
    .catch(error => {
      console.error('Erro:', error);
    });
}

function confirmacao() {

  const obterID = document.getElementById('id_carro').value;
  const nomeCarro = document.getElementById('car_nome').value;

  Swal.fire({
    title: "Excluir",
    text: "Deseja Realmente excluir : " + nomeCarro + ' ?',
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Sim, delete!"
  }).then((result) => {
    if (result.isConfirmed) {
      deletarCarro(obterID);
    }
  });

}


function obterUsuarioLogado() {
  const apiUrl = '/admin/usuarioLogado';
  fetch(apiUrl)
    .then(response => response.json())
    .then(data => {
      var user = data.user;
      var urlFoto = data.urlAnexo;
      var cargo = data.nomeCargo;
      preencherDadosUsuario(user, urlFoto, cargo);
    })

}
function preencherDadosUsuario(user, url, cargo) {
  document.getElementById('meuUser').innerHTML = `${user} <i class="bi bi-caret-down-fill"></i>`;

  document.getElementById('usuarioLogado').innerText = `${user}`;
  document.getElementById('fotoUsuario').src = url;
}

obterUsuarioLogado();

/* FUNCIONAMENTO DO DROPDOW DA PAGINA FUNCIONARIO */

let dropDowBtn = document.getElementById('dropDowBtn')
let dropDowElement = document.getElementById('dropDowMenu')
let closeDrop = document.getElementById('closeDropDow')

dropDowBtn.addEventListener('click', () => {
  openDropdow()
})

closeDrop.addEventListener('click', closeDropDow)

function openDropdow() {
  dropDowElement.style.height = '200px'
  if (window.innerWidth > 760) {
    const sidebar = document.querySelector('.barraLateral');
    sidebar.classList.add('aberta');
  }
}

function closeDropDow() {
  dropDowElement.style.height = '0'
}
