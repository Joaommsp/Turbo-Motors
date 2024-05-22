const formLogin = document.getElementById("formContainerLogin");
const formCadastro = document.getElementById("formContainerCadastro");
const switchForm = document.querySelector(".switchForm");
const formImage = document.getElementById("loginPopupImg");
const divSalvar = document.getElementById("buttonSalvar");

switchForm.addEventListener("click", () => {
  formLogin.classList.toggle("displayNone");
  formCadastro.classList.toggle("displayNone");

  if (formCadastro.classList.contains("displayNone")) {
    switchForm.textContent = "Crie sua conta";
    formImage.src =
    "/TurboStyle/img/loginpopup-img.jpg";
    formCadastro.style.width="50%"

  } else {

    formCadastro.classList.add('meuForm')
    formCadastro.style.width="110%"

    switchForm.textContent = "Faça Login";
    formImage.src =
      "/TurboStyle/img/signinpopup-img.jpg";
  }
});


const Toast = Swal.mixin({
    toast: true,
    position: "center",
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.onmouseenter = Swal.stopTimer;
      toast.onmouseleave = Swal.resumeTimer;
    }
  });

function mensagemSucesso(mensagem) {
    Toast.fire({
        icon: "success",
        title: mensagem
    });
}

function mensagemAlerta(mensagem) {
    Toast.fire({
        icon: "warning",
        title: mensagem
    });
}


function mensagemError(mensagem) {
    Toast.fire({
        icon: "error",
        title: mensagem
    });
}


/********** API CRIADA 13-03 **********/

function enviarDadosCliente() {

    
    // criar array de ações

    const guid = localStorage.getItem('guid');
    const data = new Date();
    const dataHoraFormatada = `${data.toLocaleDateString()} ${data.toLocaleTimeString()}`;
    const qtdCliques = localStorage.getItem('countClicks');
    const qtdTempo = localStorage.getItem('flowTime');
    let acoesArray = JSON.parse(localStorage.getItem('acoes'));

    // salvar ja existentes
    if(acoesArray == null) {
        acoesArray = []
     }

    const nome = document.getElementById("nome").value;
    const email = document.getElementById("emailCadastro").value;
    const senha = document.getElementById("senhaCadastro").value;
    const cpfCnpj = document.getElementById("cpfCnpj").value;
    const telefone = document.getElementById("telefone").value;


    const logradouro = document.getElementById("logradouro").value;
    const bairro = document.getElementById("bairro").value;
    const numeroCasa = document.getElementById("numeroCasa").value;
    const complemento = document.getElementById("complemento").value;
    const cep = document.getElementById("cep").value;


    const acao = {
        guid: guid,
        data: dataHoraFormatada,
        qtdCliques: qtdCliques,
        qtdTempo: qtdTempo,
        acao: ''
    }


    const dadosCliente = {
        cpfCnpj: cpfCnpj,
        email: email,
        senha: senha,
        telefone: telefone,
        nomeUsuario: nome,
        pesNome: nome,
       enderecos: [{
        bairro: bairro,
        cep: cep,
        complemento: complemento,
        logradouro: logradouro,
        numero: numeroCasa
    }]
    };

     if(nome == "" || email == "" || senha == "" || cpfCnpj == "" || cpfCnpj == "" || telefone == "" || bairro == "" || cep == "" || complemento == "" || numeroCasa == "") {
        mensagemError("Algum campo está nulo ou vázio. Tente novamente");
        
     } else {

        
    const endereco = window.location.origin;
    console.log(dadosCliente);
  
    fetch(endereco + '/cliente/criarCliente', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dadosCliente)
    })
    .then(resposta => {
        if (!resposta.ok) {
            return resposta.text().then(mensagemErro => {
                acao.acao = 'Criação de usuário com erro : ' + mensagemErro
                acoesArray.push(acao);
                localStorage.setItem('acoes', JSON.stringify(acoesArray))

                throw new Error(mensagemErro);
            });
        } else  {
            mensagemSucesso("Informações Salva");
            
            acao.acao = 'Criação de Usuário'
            acoesArray.push(acao);
            localStorage.setItem('acoes', JSON.stringify(acoesArray))


            const timer = setInterval(() => {
                window.location.reload();
                clearInterval(timer)
            }, 4000)

        }
        return resposta
    })
    .then(dadosResposta => {
        console.log('Resposta da API:', dadosResposta);
      
        return JSON.stringify(dadosResposta);
    })
    .catch(erro => {
        Toast.fire({
            icon: "warning",
            title: erro.message
        });
        console.log(erro.message)
    });


     }

  }
  


document.getElementById("criarLogin").addEventListener("click", function() {
    localStorage.setItem('realizouLogin', 'S');
    document.getElementById('formLoginB').submit();
})