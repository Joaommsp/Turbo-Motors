async function buscarNoticiasAutomotivas() {
  const apiKey = "9c6342123d4b4406810a33419c08be23";
  const newsDatefrom = getDateLeastFiveDays();
  const url = `https://newsapi.org/v2/everything?q=automotivo&from=${newsDatefrom}&sortBy=publishedAt&language=pt&apiKey=${apiKey}`;

  try {
    const response = await fetch(url);
    const data = await response.json();

    if (data.status === "ok") {
      console.log(data.articles);
      showNewsOnPage(data.articles);
    } else {
      console.error("Erro ao buscar notícias:", data.message);
    }
  } catch (erro) {
    console.error("Erro na requisição:", erro);
  }
}

const getDateLeastFiveDays = () => {
  const currentDate = new Date();

  const daysInMilliseconds = 5 * 24 * 60 * 60 * 1000;
  const currenteDateLessFiveDays = new Date(
    currentDate.getTime() - daysInMilliseconds
  );

  const formattedDateLessFiveDays = getDateYYYYMMDD(currenteDateLessFiveDays);

  return formattedDateLessFiveDays;
};

function getDateYYYYMMDD(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}/${month}/${day}`;
}

function showNewsOnPage(newsArray) {
  const newsCardsContainer = document.querySelector(".newsCardsContainer");

  for (let i = 0; i < newsArray.length; i++) {
    if (newsArray[i].title == "[Removed]") {
      continue;
    }

    newsCardsContainer.innerHTML += `<div id="newsCard" >
    <img id="newsImage" src="${newsArray[i].urlToImage}" />
    <span id="lastNewsCheck" > <img src="/TurboStyle/img/svg/check-icon.svg" />Últimas notícias</span>
    <span id="newsTitle" >${
      newsArray[i].title != null ? newsArray[i].title : ""
    }</span>
    <p id="newsDescription">${newsArray[i].description} ... </p>
    <a id="newsUrl" href="${
      newsArray[i].url
    }">Acesse aqui <img src="/TurboStyle/img/svg/external-link-icon.svg" /> </a>
    </div>`;
  }
}

buscarNoticiasAutomotivas();
