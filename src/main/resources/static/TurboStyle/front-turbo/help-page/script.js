const answerContainer = document.querySelectorAll(".answerContainer")
const answerCardControl = document.querySelectorAll(".questionControl")

answerCardControl.forEach((control, index) => {
  control.addEventListener("click" , () => {

    control.classList.toggle('invertImage')
    answerContainer[index].classList.toggle('cardClosed')

  })
});

const helpForm = document.querySelector(".helpForm") 

helpForm.addEventListener("submit", () => {

  const timeToClosePage = setInterval(() => {
    window.close()

    console.log("CUZAO")

    clearInterval(timeToClosePage)
  }, 3000);

})
