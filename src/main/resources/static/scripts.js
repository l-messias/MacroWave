function exibeCorteInf() {
    let canal = document.getElementById("canal-select").value;
    console.log(canal);
    if (canal === "PASSAFAIXAS") {
        document.getElementById("freq-inf").hidden = false;
        document.getElementById("freq-inf").classList.add("campo");
        document.getElementById("label-freq-corte").innerText = "Frequência de Corte Superior em kHz (1kHz - 100kHz)";
    }
    else {
        document.getElementById("freq-inf").classList.remove("campo");
        document.getElementById("freq-inf").hidden = true;
        document.getElementById("label-freq-corte").innerText = "Frequência de Corte em kHz (1kHz - 100kHz)";
    }
};

function redirectToSimulation() {
    window.location.href = '/simulacao';
}