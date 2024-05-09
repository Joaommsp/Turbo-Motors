// Teste de fluxo vindo da página inicial

const getContext = localStorage.getItem("proposta");
console.log(
  "Proposta do site de acordo com o usuário: " + getContext.toString()
);

const getTimeAndCountClicks = () => {
  let time = Number.parseFloat(localStorage.getItem("flowTime"));
  const timeCount = setInterval(() => {
    localStorage.setItem("flowTime", (time += 1));
    console.log("tempo de navegação até o momento: " + time);
  }, 1000);

  return timeCount;
};

getTimeAndCountClicks();

//Controle POPUOP de login

let main = document.querySelector("main");
let navBar = document.getElementById("navBar");
let navBarContent = document.getElementById("navBarContent");
let loginbtn = document.getElementById("loginBnt");
let popUp = document.getElementById("loginPopup");
loginbtn.addEventListener("click", () => {
  popUp.classList.remove("displayNone");
  main.classList.add("blur");
  navBar.classList.add("blur");
  main.classList.add("noPointerEvents");
  navBar.classList.add("noPointerEvents");
  blockRow();
});

let closeLogin = document.getElementById("closeLogin");
closeLogin.addEventListener("click", () => {
  popUp.classList.add("displayNone");
  main.classList.remove("blur");
  navBar.classList.remove("blur");
  main.classList.remove("noPointerEvents");
  navBar.classList.remove("noPointerEvents");
  unBlockRow();
});

function blockRow() {
  document.body.style.overflow = "hidden";
}

function unBlockRow() {
  document.body.style.overflow = "auto";
}

// LOGIN COM GOOGLE PARA USUARIO

function handleCredentialResponse(response) {
  localStorage.setItem("googleResponseToken", response.credential);

  location.reload();
}

window.onload = function () {
  google.accounts.id.initialize({
    client_id:
      "119707003559-1u7c4dlec2s9f34q5dlo476vkejhmdc7.apps.googleusercontent.com",
    callback: handleCredentialResponse,
  });

  google.accounts.id.renderButton(
    document.getElementById("buttonDiv"),
    {
      theme: "filled_blue",
      size: "large",
      type: "standard",
      shape: "rectangular",
      text: "signin_with",
      size: "large",
      locale: "pt-PT",
      logo_alignment: "left",
    } // customization attributes
  );

  google.accounts.id.prompt(); // also display the One Tap dialog
};

const responseToken = localStorage.getItem("googleResponseToken");

if (responseToken) {
  loginbtn.remove();

  let data = jwt_decode(responseToken);
  console.log(data);
  popUp.classList.add("loginNone");
  main.classList.remove("blur");
  navBar.classList.remove("blur");
  main.classList.remove("noPointerEvents");
  navBar.classList.remove("noPointerEvents");
  unBlockRow();

  {
    /* <div class="collapse navbar-collapse h " id="navbarNav">
              <ul class="navbar-nav">
                  <li class="nav-item">
                    <a class="nav-link" href="#" ></a>
                  </li>
                </ul>
              </div> */

    let divUser = document.createElement("div");
    divUser.classList.add("collapse", "navbar-collapse", "h");
    divUser.id = "navbarNav";

    let divUserFormat = document.createElement("div");
    divUserFormat.id = "userInfos";
    divUser.appendChild(divUserFormat);

    let divUserUl = document.createElement("ul");
    divUserUl.classList.add("navbar-nav");
    navBarContent.appendChild(divUser);

    function createNavItem(element) {
      const liElement = document.createElement("li");
      liElement.classList.add("nav-item");

      if (element) {
        liElement.appendChild(element);
      }
      return liElement;
    }

    let userNameData = data.name;
    let userName = document.createElement("a");
    userName.textContent = userNameData;
    userName.id = "userName";
    userName.classList.add("nav-link", "text-white");
    let userNameLi = createNavItem(userName);
    divUserUl.appendChild(userNameLi);

    let userImgData = data.picture;
    let userImg = document.createElement("img");
    userImg.src = userImgData;
    userImg.id = "userImg";
    let userImgLi = createNavItem(userImg);
    divUserUl.appendChild(userImgLi);

    divUserFormat.appendChild(divUserUl);
  }
} else {
  // O usuário não está logado
  // Atualizar a interface do usuário para refletir que o usuário não está logado
}

function logout() {
  // Remover o token de resposta do localStorage
  localStorage.removeItem("googleResponseToken");
  location.reload();

  // Atualizar a interface do usuário para refletir que o usuário não está logado
}

if (responseToken) {
  let dropdowMenu = document.createElement("div");
  dropdowMenu.id = "dropdowMenu";

  let iconsDiv = document.createElement("div");
  iconsDiv.id = "iconsDiv";

  let profileIcon = document.createElement("img");
  profileIcon.src = "../../public/profile-icon.svg";
  let settingsIcon = document.createElement("img");
  settingsIcon.src = "../../public/settings-icon.svg";
  let helpIcon = document.createElement("img");
  helpIcon.src = "../../public/help-icon.svg";
  let logoutIcon = document.createElement("img");
  logoutIcon.src = "../../public/logout-icon.svg";

  iconsDiv.append(profileIcon, settingsIcon, helpIcon, logoutIcon);

  let linksDiv = document.createElement("div");
  linksDiv.id = "linksDiv";

  let myProfile = document.createElement("a");
  myProfile.textContent = "Meu Perfil";
  myProfile.href = "/turbo_motors/pages/user/user.html";
  myProfile.id = "myProfile";
  let settings = document.createElement("a");
  settings.textContent = "Configurações";
  let help = document.createElement("a");
  help.textContent = "Ajuda";

  let logoutBtn = document.createElement("a");
  logoutBtn.id = "logoutBtn";
  logoutBtn.textContent = "Sair";
  logoutBtn.addEventListener("click", logout);

  let closeDDIcon = document.createElement("img");
  closeDDIcon.src = "../../public/close-icon.svg";
  closeDDIcon.id = "closeDropDown";

  linksDiv.append(myProfile, settings, help, logoutBtn, closeDDIcon);

  dropdowMenu.append(iconsDiv, linksDiv);

  main.appendChild(dropdowMenu);

  let userImg = document.getElementById("userImg");
  userImg.addEventListener("click", openDropdow);
  closeDDIcon.addEventListener("click", closeDropDow);

  function openDropdow() {
    dropdowMenu.style.height = "200px";
  }

  function closeDropDow() {
    dropdowMenu.style.height = "0";
  }
}
