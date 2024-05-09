const contextResult = localStorage.getItem("proposta");
const timeResult = Number.parseFloat(localStorage.getItem("flowTime"));

const showResults = () => {
  window.alert(
    "Contexto: " +
      contextResult +
      "\n" +
      "Tempo até comprar ou alugar veículo: " +
      timeResult
  );

  const talvez = window.confirm("quer limpar?");
  if (talvez) {
    // Clear local storage for the current origin
    localStorage.clear();

    // Clear session storage for the current origin (temporary data)
    sessionStorage.clear();
  }
};

if (contextResult != null && timeResult != null) {
  showResults();
}
