const formLogin = document.getElementById("formContainerLogin");
const formCadastro = document.getElementById("formContainerCadastro");
const switchForm = document.querySelector(".switchForm");
const formImage = document.getElementById("loginPopupImg");

switchForm.addEventListener("click", () => {
  formLogin.classList.toggle("displayNone");
  formCadastro.classList.toggle("displayNone");

  if (formCadastro.classList.contains("displayNone")) {
    switchForm.textContent = "Crie sua conta";
    formImage.src =
    "/TurboStyle/img/loginpopup-img.jpg";
  } else {
    switchForm.textContent = "Faça Login";
    formImage.src =
      "/TurboStyle/img/signinpopup-img.jpg";
  }
});


/********** API CRIADA 13-03 **********/

function enviarDadosCliente() {
    const nome = document.getElementById("nome").value;
    const email = document.getElementById("emailCadastro").value;
    const senha = document.getElementById("senhaCadastro").value;
    const cpfCnpj = document.getElementById("cpfCnpj").value;
    const telefone = document.getElementById("telefone").value;
  
    const dadosCliente = {
        cpfCnpj: cpfCnpj,
        email: email,
        senha: senha,
        telefone: telefone,
        nomeUsuario: nome,
        pesNome: nome,
    };
  
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
              Toast.fire({
                icon: "success",
                title: "Cadastrado com sucesso. Acesse seu perfil e edite suas informações e endereço!"
            });
                throw new Error(mensagemErro);
            });
        } else  {
            Toast.fire({
                icon: "success",
                title: "Cadastrado com sucesso. Acesse seu perfil e edite suas informações e endereço!"

            });

            const timer = setInterval(() => {
                window.location.reload();
                clearInterval(timer)
            }, 4000)

        }
        return resposta
    })
    .then(dadosResposta => {
        console.log('Resposta da API:', dadosResposta);
      
        return dadosResposta;
    })
    .catch(erro => {
        Toast.fire({
            icon: "warning",
            title: erro.message
        });
        console.error('Erro ao enviar os dados:', erro);
    });
  }
  