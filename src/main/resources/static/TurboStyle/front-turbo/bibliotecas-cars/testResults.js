const timeResult = Number.parseFloat(localStorage.getItem("flowTime"));
const cliqueResult = Number.parseFloat(localStorage.getItem("countCliks"));

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

  setCookie("cliques", cliqueResult, 1);
  setCookie("tempoTotal", timeResult, 1);
  localStorage.clear();
  sessionStorage.clear();
}


const reiniciar = () => {
  removeCookie("cliques");
  removeCookie("tempoTotal");
}


