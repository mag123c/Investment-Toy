let signInCon;
let xBtn;
let mainCon;
let navbar;
document.addEventListener("DOMContentLoaded", function() {
    signInCon = document.querySelector('.main-con');
    xBtn = document.querySelector('.X-btn');
    mainCon = document.querySelector('.container-fluid');
    navbar = document.querySelector('.navbar');

    xBtn.addEventListener('click', signInModalOff);
});

function signInModal() {
    mainCon.style.pointerEvents = "none";
    navbar.style.pointerEvents = "none";
    document.body.classList.add("modal");
    signInCon.classList.add("show");
}

function signInModalOff() {
    mainCon.style.pointerEvents = "auto";
    navbar.style.pointerEvents = "auto";
    document.body.classList.remove("modal");
    signInCon.classList.toggle("show");
}