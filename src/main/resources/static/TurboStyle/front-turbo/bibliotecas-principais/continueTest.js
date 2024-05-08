const getContext = localStorage.getItem("proposta");
console.log(
  "Proposta do site de acordo com o usuário: " + getContext.toString()
);

// const getTotalFlowTime = localStorage.getItem("flowTime");
// console.log(
//   "Tempo de navegação até o momento " +
//     getTotalFlowTime.toString() +
//     " segundos"
// );

const getTimeAndCountClicksFromPreviousPages = () => {
  let time = Number.parseFloat(localStorage.getItem("flowTime"));
  const timeCount = setInterval(() => {
    localStorage.setItem("flowTime", (time += 1));
    console.log("tempo de navegação até o momento: " + time);
  }, 1000);

  return timeCount;
};

getTimeAndCountClicksFromPreviousPages();
