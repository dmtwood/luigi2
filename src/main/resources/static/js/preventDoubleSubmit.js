
// script to avoid double submits
// handled in toevoegen.html
document.querySelector("form").onsubmit = function () {
    this.querySelector("button").disabled=true;
}