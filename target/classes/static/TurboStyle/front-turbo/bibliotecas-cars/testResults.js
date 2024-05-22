const timeResult = Number.parseFloat(localStorage.getItem("flowTime"));
const cliqueResult = Number.parseFloat(localStorage.getItem("countCliks"));



function gerarGuid() {
  const hex = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'a', 'b', 'c', 'd', 'e', 'f'];
  let uuid = '';
  for (let i = 0; i < 4; i++) {
    for (let j = 0; j < 4; j++) {
      if (i === 2 && j === 2) {
        uuid += '4'; // Versão 4
      } else {
        uuid += hex[Math.floor(Math.random() * 16)];
      }
    }
    uuid += '-';
  }
  uuid = uuid.substring(0, uuid.length - 1);
  return uuid;
}

function removeCookie(name) {
  document.cookie = name + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}

function setCookie(name, value, daysToExpire) {
  var expires = "";
  if (daysToExpire) {
    var date = new Date();
    date.setTime(date.getTime() + (daysToExpire * 24 * 60 * 60 * 1000));
    expires = "; expires=" + date.toUTCString();
  }
  document.cookie = name + "=" + value + expires + "; path=/";
}


const showResults = () => {
  let acoesArray = JSON.parse(localStorage.getItem('acoes'));
  var encodedJsonString = encodeURIComponent(JSON.stringify(acoesArray));
  console.log('ecode: ' + encodedJsonString);
  setCookie('jsonAcao', encodedJsonString, 1)
  setCookie("cliques", cliqueResult, 1);
  setCookie("tempoTotal", timeResult, 1);
  localStorage.clear();
  sessionStorage.clear();
}


const reiniciar = () => {
  if(localStorage.getItem('realizouLogin') == 'S') {
    const guid = localStorage.getItem('guid');
    const data = new Date();
    const dataHoraFormatada = `${data.toLocaleDateString()} ${data.toLocaleTimeString()}`;
    const qtdCliques = localStorage.getItem('countClicks');
    const qtdTempo = localStorage.getItem('flowTime');
    // Obter Array
    let acoesArray = JSON.parse(localStorage.getItem('acoes'));
    // Verificar se existe conteudo
    if(acoesArray == null) {
      // Criar array vázio
       acoesArray = []
    }

    // definir um novo json
    const acao = {
      guid: guid,
      data: dataHoraFormatada,
      qtdCliques: qtdCliques,
      qtdTempo: qtdTempo,
      acao: 'Entrou no Perfil'
      }

      localStorage.setItem('realizouLogin', 'N');


      // salvar ja no existente ou no novo
      acoesArray.push(acao);
      console.log(acoesArray)
      localStorage.setItem('acoes', JSON.stringify(acoesArray))

  }

  if(localStorage.getItem('acoes') == null) {
  
  localStorage.clear();
  sessionStorage.clear();
  localStorage.setItem("guid", gerarGuid())
  
  removeCookie("cliques");
  removeCookie("tempoTotal");
  removeCookie("jsonAcao");
  }


}


