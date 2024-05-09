$(document).ready(function() {
    // Capturando o elemento select do canal
    var canalSelect = document.getElementById("canal-select");

    // Capturando a div freq-inf
    var freqInfDiv = document.getElementById("freq-inf");

    // Adicionando um ouvinte de evento para o evento change no select do canal
    canalSelect.addEventListener("change", function() {
    console.log("Alteração na Seleção");
    console.log(canalSelect.value);
        // Verificando se a opção selecionada é "Passa-Faixas"
        if (canalSelect.value === "PASSAFAIXAS") {
            // Removendo a classe 'hidden' da div freq-inf
            console.log("Removendo a classe 'hidden'");
            freqInfDiv.classList.remove("hidden");
            freqInfDiv.classList.add("campo");
        } else {
            // Adicionando a classe 'hidden' à div freq-inf
            freqInfDiv.classList.remove("campo");
            freqInfDiv.classList.add("hidden");

        }
    });
});


console.log("Alteração na Seleção");