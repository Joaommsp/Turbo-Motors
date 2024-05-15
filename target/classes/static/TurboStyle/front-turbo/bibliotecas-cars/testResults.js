const timeResult = Number.parseFloat(localStorage.getItem("flowTime"));
const cliqueResult = Number.parseFloat(localStorage.getItem("countCliks"));

const showResults = () => {
  window.alert(
    "Quantidade de cliques: " +
      cliqueResult +
      " clique(s)" +
      "\n" +
      "Tempo até comprar ou alugar veículo: " +
      timeResult +
      " segundos(s)"
  );

  const resetTest = window.confirm("Deseja enviar o resultado do teste atual?");
  if (resetTest) {
    // Clear local storage for the current origin
    localStorage.clear();

    // Clear session storage for the current origin (temporary data)
    sessionStorage.clear();
  }
};

showResults();
