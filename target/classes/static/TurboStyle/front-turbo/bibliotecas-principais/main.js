$(document).ready(function(){
  $(".slider").bxSlider({
    auto: true,
    mode: "horizontal",
    touchEnabled: false,
    controls: false,
    speed: 1000,
    pause: 5000
  });
});
$(document).ready(function(){
  $(".showroom").bxSlider({
    auto: true,
    mode: "horizontal",
    touchEnabled: false,
    controls: false,
    speed: 450
  });
});

// BOTÕES PARA ABRI A PÁGINA COM OS VEÍCULOS DA CATEGORIA 

let sedanBtn = document.getElementById('sedanBtn')
let mediumSedanBtn = document.getElementById('mediumSedanBtn')
let compactBtn = document.getElementById('compactBtn')
let hatchBtn = document.getElementById('hatchBtn')
let pickUpBtn = document.getElementById('pickUpBtn')
let suvBtn = document.getElementById('suvBtn')

// sedanBtn.addEventListener('click', sedanPage)
// mediumSedanBtn.addEventListener('click', mediumSedanPage)
// compactBtn.addEventListener('click', compactPage)
// hatchBtn.addEventListener('click', hatchPage)
// pickUpBtn.addEventListener('click', pickUpPage)
// suvBtn.addEventListener('click', suvPage)

//Controle POPUOP de login

let main = document.querySelector('main')
let navBar = document.getElementById('navBar')
let navBarContent = document.getElementById('navBarContent')
let loginbtn = document.getElementById('loginBnt')


   /* =========== indo para a página com os veículos usando o botão ver mais ===== */

   let seeMoreBtn = document.querySelectorAll('.seeMoreBtn a')

   seeMoreBtn.forEach((e) => {
    e.href = 'cars/cars.html'
   })
 



// const apiUrl = 'http://localhost:8080/carrosOdernados'


// const options = {
//   method: 'GET', 
//   headers: {
//     'Content-Type': 'application/json', 
//   },

// };


// fetch(apiUrl, options)
//   .then(response => {
//     if (!response.ok) {
//       throw new Error(`Erro na requisição: ${response.status}`);
//     }
//     return response.json();
//   })
//   .then(data => {
//     console.log('Dados recebidos:', data);
//   })
//   .catch(error => {
//     console.error('Erro durante a requisição:', error);
//     // Adicione este bloco para obter mais informações sobre o erro
//     if (error.response) {
//       // O servidor respondeu com um status diferente de 2xx
//       console.error('Status da resposta:', error.response.status);
//       console.error('Mensagem da resposta:', error.response.statusText);
//     } else if (error.request) {
//       // A requisição foi feita, mas não houve resposta
//       console.error('Sem resposta do servidor');
//     } else {
//       // Algo aconteceu ao configurar a requisição
//       console.error('Erro ao configurar a requisição:', error.message);
//     }
//   });