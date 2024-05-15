const getTimeAndCountClicksFromPreviousPages = () => {
  let time = Number.parseFloat(localStorage.getItem("flowTime"));
  let clicks = Number.parseFloat(localStorage.getItem("countCliks"));

  document.addEventListener("click", () => {
    localStorage.setItem("countCliks", (clicks += 1));
  });

  const timeCount = setInterval(() => {
    localStorage.setItem("flowTime", (time += 1));
    console.log(
      "tempo de navegação até o momento: " +
        time +
        " segundos(s)\n" +
        " Quantidade de cliques até o momento: " +
        clicks +
        " clique(s)"
    );
  }, 1000);

  return timeCount;
};

getTimeAndCountClicksFromPreviousPages();
