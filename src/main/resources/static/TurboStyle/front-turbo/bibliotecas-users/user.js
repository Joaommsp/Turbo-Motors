
//Controle POPUOP de login

let main = document.querySelector('main')
let navBar = document.getElementById('navBar')
let navBarContent = document.getElementById('navBarContent')
let loginbtn = document.getElementById('loginBnt')
let popUp = document.getElementById('loginPopup')
loginbtn.addEventListener('click', () => {
  popUp.classList.remove('displayNone')
  main.classList.add('blur')
  navBar.classList.add('blur')
  main.classList.add('noPointerEvents')
  navBar.classList.add('noPointerEvents')
  blockRow()
})

let closeLogin = document.getElementById('closeLogin')
closeLogin.addEventListener('click', () => {
  popUp.classList.add('displayNone')
  main.classList.remove('blur')
  navBar.classList.remove('blur')
  main.classList.remove('noPointerEvents')
  navBar.classList.remove('noPointerEvents')
  unBlockRow()
})

function blockRow() {
  document.body.style.overflow = 'hidden'
}

function unBlockRow() {
  document.body.style.overflow = 'auto'
}

// LOGIN COM GOOGLE PARA USUARIO 

function handleCredentialResponse(response) {

  localStorage.setItem('googleResponseToken', response.credential);
  
  location.reload();
}

window.onload = function () {
  
  google.accounts.id.initialize({
    client_id: "119707003559-1u7c4dlec2s9f34q5dlo476vkejhmdc7.apps.googleusercontent.com",
    callback: handleCredentialResponse
  })

  google.accounts.id.renderButton(
    document.getElementById("buttonDiv"),
    { theme: "filled_blue",
     size: "large" ,
     type:"standard",
     shape:"rectangular",
     text:"signin_with",
     size:"large",
     locale:"pt-PT",
     logo_alignment:"left"
    }  
  );

  google.accounts.id.prompt(); 
  
}

const responseToken = localStorage.getItem('googleResponseToken');

  if (responseToken) {
    loginbtn.remove()

    let data = jwt_decode(responseToken)
    console.log(data)
    popUp.classList.add('loginNone')
    main.classList.remove('blur')
    navBar.classList.remove('blur')
    main.classList.remove('noPointerEvents')
    navBar.classList.remove('noPointerEvents')
    unBlockRow()
  
  {/* <div class="collapse navbar-collapse h " id="navbarNav">
              <ul class="navbar-nav">
                  <li class="nav-item">
                    <a class="nav-link" href="#" ></a>
                  </li>
                </ul>
              </div> */
  
    let divUser = document.createElement('div')
    divUser.classList.add('collapse', 'navbar-collapse', 'h')
    divUser.id = "navbarNav"
  
    let divUserFormat = document.createElement('div')
    divUserFormat.id = 'userInfos'
    divUser.appendChild(divUserFormat)
  
    let divUserUl = document.createElement('ul')
    divUserUl.classList.add('navbar-nav')
    navBarContent.appendChild(divUser)
    
    function createNavItem(element) {
      const liElement = document.createElement('li');
      liElement.classList.add('nav-item');
  
      if (element) {
        liElement.appendChild(element);
      }
      return liElement;
    }
    
    let userNameData = data.name
    let userName = document.createElement('a')
    userName.textContent = userNameData
    userName.id = "userName"
    userName.classList.add('nav-link','text-white');
    let userNameLi = createNavItem(userName)
    divUserUl.appendChild(userNameLi)
  
    let userImgData = data.picture
    let userImg = document.createElement('img')
    userImg.src = userImgData
    userImg.id = "userImg"
    let userImgLi = createNavItem(userImg)
    divUserUl.appendChild(userImgLi)
  
    divUserFormat.appendChild(divUserUl)
    } 

  } else {
    // O usuário não está logado
    // Atualizar a interface do usuário para refletir que o usuário não está logado
  }

  function logout() {
    // Remover o token de resposta do localStorage
    localStorage.removeItem('googleResponseToken');
    location.reload();
   
    // Atualizar a interface do usuário para refletir que o usuário não está logado
   }

   if (responseToken) {
    let dropdowMenu = document.createElement('div')
    dropdowMenu.id = 'dropdowMenu'

    let iconsDiv = document.createElement('div')
    iconsDiv.id ='iconsDiv'

    let profileIcon = document.createElement('img')
    profileIcon.src = '../../public/profile-icon.svg'
    let settingsIcon = document.createElement('img')
    settingsIcon.src = '../../public/settings-icon.svg'
    let helpIcon = document.createElement('img')
    helpIcon.src = '../../public/help-icon.svg'
    let logoutIcon = document.createElement('img')
    logoutIcon.src = '../../public/logout-icon.svg'

    iconsDiv.append(profileIcon, settingsIcon, helpIcon, logoutIcon)

    let linksDiv = document.createElement('div')
    linksDiv.id = 'linksDiv'

    let myProfile = document.createElement('a')
    myProfile.textContent = 'Meu Perfil'
    myProfile.href = "#"
    myProfile.id = "myProfile"
    let settings = document.createElement('a')
    settings.textContent = 'Configurações'
    let help = document.createElement('a')
    help.textContent = 'Ajuda'

    let logoutBtn = document.createElement('a')
    logoutBtn.id = 'logoutBtn'
    logoutBtn.textContent = "Sair"
    logoutBtn.addEventListener('click', logout)

    let closeDDIcon = document.createElement('img')
    closeDDIcon.src = '../../public/close-icon.svg'
    closeDDIcon.id = 'closeDropDown'

    linksDiv.append(myProfile, settings, help, logoutBtn, closeDDIcon)

    dropdowMenu.append(iconsDiv, linksDiv)

    main.appendChild(dropdowMenu)

    let userImg = document.getElementById('userImg')
    userImg.addEventListener('click' , openDropdow)
    closeDDIcon.addEventListener('click', closeDropDow)

    function openDropdow() {
      dropdowMenu.style.height = '200px'  
        }
    
        function closeDropDow() {
          dropdowMenu.style.height = '0'
    
   }
  }
   
  if(responseToken) {

    let data = jwt_decode(responseToken)
    console.log(data)

    let userContainer = document.getElementById('userContainer')
    let userImgData = data.picture

    let userImg = document.getElementById('userMainImg')
    userImg.classList.add('img-fluid') 
    userImg.src = userImgData

    let userNameContent = document.getElementById('usarName')
    let usarNameData = data.name
    userNameContent.innerText = usarNameData

    let userEmailContent = document.getElementById('userEmail')
    let usarEmailData = data.email
    userEmailContent.innerText = usarEmailData

    if ('geolocation' in navigator) {
    
      navigator.geolocation.getCurrentPosition(function(position) {

          const latitude = position.coords.latitude;
          const longitude = position.coords.longitude;
  
          console.log(`Latitude: ${latitude}, Longitude: ${longitude}`);
  

      }, function(error) {
       
          switch (error.code) {
              case error.PERMISSION_DENIED:
                  console.error("Permissão para obter localização negada pelo usuário.");
                  break;
              case error.POSITION_UNAVAILABLE:
                  console.error("Informações de localização não disponíveis.");
                  break;
              case error.TIMEOUT:
                  console.error("Tempo expirado ao obter localização.");
                  break;
              case error.UNKNOWN_ERROR:
                  console.error("Erro desconhecido ao obter localização.");
                  break;
          }
      });
  } else {
      console.error("Geolocalização não é suportada neste navegador.");
  }

  }

  let inputUserImage = document.getElementById('userImgUpload')

  function checkFileType(input) {
    var fileName = input.value;
    var idxDot = fileName.lastIndexOf(".") + 1;
    var extFile = fileName.substr(idxDot, fileName.length).toLowerCase();
    if (extFile=="jpg" || extFile=="jpeg" || extFile=="png"){
       // arquivo aceito
       return true;
    } else {
       alert("Apenas imagens no formato jpeg/png!");
       input.value='';
       return false;
    }
  }
  
  console.log(inputUserImage)
  
 /* BARRA DE NAVEGAÇÃO DO "MEU PERFIL" */

  let myinfos = document.getElementById("navPessoalInfos")
  let myadress = document.getElementById("navAdress")
   let kart = document.getElementById('navkart')

  let userForm = document.getElementById('userForm')
  let adressForm = document.getElementById('adressForm')
  let kartForm = document.getElementById('kartForm')

  window.onload = function() {
    adressForm.classList.add('displayNone')
    kartForm.classList.add('displayNone')
   };


  myinfos.addEventListener('click', () => {
    if(userForm.classList.contains('displayNone') || !userForm.classList.contains('displayNone')) {
      userForm.classList.remove('displayNone')
      adressForm.classList.add('displayNone')
      kartForm.classList.add('displayNone')
    }
  })

  myadress.addEventListener('click', () => {
    if(adressForm.classList.contains('displayNone') || !adressForm.classList.contains('displayNone')) {
      adressForm.classList.remove('displayNone')
      userForm.classList.add('displayNone')
      kartForm.classList.add('displayNone')
    }
  })
  
 kart.addEventListener('click', () => {
    if(kartForm.classList.contains('displayNone') || !kartForm.classList.contains('displayNone')) {
      kartForm.classList.remove('displayNone')
      adressForm.classList.add('displayNone')
      userForm.classList.add('displayNone')
    }
  })