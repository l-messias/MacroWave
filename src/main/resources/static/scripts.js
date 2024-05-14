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

/*
    $(document).ready(function () {
        // Função para debugar os mapas
        function debugMap(nomeMapa, mapa) {
            console.log("[" + nomeMapa + "]");
            var contador = 0;
            for (var chave in mapa) {
                if (contador >= 10) break;
                console.log("Chave: " + chave + " Valor: " + mapa[chave]);
                contador++;
            }
        }

        // Função para gerar um gráfico
        function gerarGrafico(id, data, layout) {
            Plotly.newPlot(id, data, layout);
        }

        // Função para debugar todos os mapas
        function debug(data) {
            debugMap("Amplitudes", data.amplitudes);
            debugMap("Fases", data.fases);
            debugMap("ModuloResposta", data.moduloResposta);
            debugMap("FaseResposta", data.faseResposta);
            debugMap("AmplitudesSaida", data.amplitudesSaida);
            debugMap("FasesSaida", data.fasesSaida);
        }

        // Função para gerar todos os gráficos
        function grafico(data) {
            gerarGrafico('sinal-entrada', [{ x: Object.keys(data.amplitudes), y: Object.values(data.amplitudes), type: 'scatter', mode: 'lines', name: 'Amplitude' }, { x: Object.keys(data.fases), y: Object.values(data.fases), type: 'scatter', mode: 'lines', name: 'Fase' }], { title: 'Sinal de Entrada', xaxis: { title: 'Frequência (Hz)' }, yaxis: { title: 'Amplitude/Fase' } });
            gerarGrafico('amplitudes', [{ x: Object.keys(data.amplitudes), y: Object.values(data.amplitudes), type: 'bar', name: 'Amplitude' }], { title: 'Amplitudes das Componentes de Frequência do Sinal de Entrada', xaxis: { title: 'Harmônica' }, yaxis: { title: 'Amplitude' } });
            gerarGrafico('fases', [{ x: Object.keys(data.fases), y: Object.values(data.fases), type: 'bar', name: 'Fase' }], { title: 'Fases das Componentes de Frequência do Sinal de Entrada', xaxis: { title: 'Harmônica' }, yaxis: { title: 'Fase (rad)' } });
            gerarGrafico('modulo-resposta', [{ x: Object.keys(data.moduloResposta), y: Object.values(data.moduloResposta), type: 'scatter', mode: 'lines', name: 'Módulo' }], { title: 'Módulo da Resposta em Frequência do Canal', xaxis: { title: 'Frequência (Hz)' }, yaxis: { title: 'Módulo' } });
            gerarGrafico('fase-resposta', [{ x: Object.keys(data.faseResposta), y: Object.values(data.faseResposta), type: 'scatter', mode: 'lines', name: 'Fase' }], { title: 'Fase da Resposta em Frequência do Canal', xaxis: { title: 'Frequência (Hz)' }, yaxis: { title: 'Fase (rad)' } });
            gerarGrafico('amplitudes-saida', [{ x: Object.keys(data.amplitudesSaida), y: Object.values(data.amplitudesSaida), type: 'bar', name: 'Amplitude' }], { title: 'Amplitudes das Componentes de Frequência do Sinal de Saída', xaxis: { title: 'Harmônica' }, yaxis: { title: 'Amplitude' } });
            gerarGrafico('fases-saida', [{ x: Object.keys(data.fasesSaida), y: Object.values(data.fasesSaida), type: 'bar', name: 'Fase' }], { title: 'Fases das Componentes de Frequência do Sinal de Saída', xaxis: { title: 'Harmônica' }, yaxis: { title: 'Fase (rad)' } });
        }

        $("#criar-simulacao").click(function () {
            // Coleta os dados do formulário
            var categoria = $("#categoria-select").val();
            var frequencia = $("#frequencia").val();
            var canal = $("#canal-select").val();
            var frequenciaDeCorteSup = $("#frequencia-corte-sup").val();
            var frequenciaDeCorteInf = $("#frequencia-corte-inf").val();

            // Envia os dados para o controlador via AJAX
            $.ajax({
                type: "POST",
                url: "/criar-simulacao",
                data: {
                    categoria: categoria,
                    frequencia: frequencia,
                    canal: canal,
                    frequenciaDeCorteSup: frequenciaDeCorteSup,
                    frequenciaDeCorteInf: frequenciaDeCorteInf
                },
                success: function (data) {
                    // Debug dos mapas
                    debug(data);

                    // Gerar gráficos
                    grafico(data);
                },
                error: function () {
                    alert("Ocorreu um erro ao processar a solicitação.");
                }
            });
        });
    });

    */