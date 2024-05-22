$(document).ready(function () {
    $("#criar-simulacao").click(function () {
        var categoria = $("#categoria-select").val();
        var frequencia = $("#frequencia").val();
        var canal = $("#canal-select").val();
        var frequenciaCorteSup = $("#frequencia-corte-sup").val();
        var frequenciaCorteInf = $("#frequencia-corte-inf").val();

        if (!frequenciaCorteInf) {
            frequenciaCorteInf = 0; // Define o valor como 0 se estiver vazio
        }

        if (frequenciaCorteSup == 0) {
            frequenciaCorteSup = frequencia; // Iguala a frequencia de corte superior à frequencia se for 0
        }

        $.ajax({
            type: "POST",
            url: "/criar-simulacao",
            data: {
                categoria: categoria,
                frequencia: frequencia,
                canal: canal,
                frequenciaDeCorteSup: frequenciaCorteSup,
                frequenciaDeCorteInf: frequenciaCorteInf
            },
            dataType: "json",
            success: function (response) {
                // Exibir gráficos com Plotly.js
                exibirGraficos(response);
            },
            error: function () {
                alert("Erro ao processar a solicitação");
            }
        });
    });

    // Função para exibir gráficos com Plotly.js
    function exibirGraficos(data) {
        plotGraph('sinal-entrada', 'Sinal de Entrada', data.sinaisEntrada, 'Tempo (segundos)', 'Amplitude', 'lines', 10);
        plotGraph('sinal-saida', 'Sinal de Saída', data.sinaisSaida, 'Tempo (segundos)', 'Amplitude', 'lines', 10);
        plotGraph('modulo-resposta', 'Módulo da Resposta em Frequência', data.respostaModuloCanal, 'Frequência (Hz)', 'Módulo', 'lines', 10);
        plotGraph('fase-resposta', 'Fase da Resposta em Frequência', data.respostaFaseCanal, 'Frequência (Hz)', 'Fase (graus)', 'lines', 10);
        plotGraph('amplitudes', 'Amplitude por Frequência de Entrada', data.amplitudePorFrequenciaEntrada, 'Frequência (Hz)', 'Amplitude', 'bar', 5);
        plotGraph('fases', 'Fase por Frequência de Entrada', data.fasePorFrequenciaEntrada, 'Frequência (Hz)', 'Fase (graus)', 'bar', 5);
        plotGraph('amplitudes-saida', 'Amplitude por Frequência de Saída', data.amplitudePorFrequenciaSaida, 'Frequência (Hz)', 'Amplitude', 'bar', 5);
        plotGraph('fases-saida', 'Fase por Frequência de Saída', data.fasePorFrequenciaSaida, 'Frequência (Hz)', 'Fase (graus)', 'bar', 5);
    }

    function plotGraph(divId, title, data, xTitle, yTitle, type, range) {
        var xValues = Object.keys(data);
        var yValues = Object.values(data);
        var trace;
        if (type === 'lines') {
            trace = {
                x: xValues,
                y: yValues,
                type: 'scatter',
                mode: 'lines',
                name: title
            };
        }
        else {
            trace = {
                x: xValues,
                y: yValues,
                type: 'bar',
                name: title,
                width: 2
            };
        }
        
        var layout = {
            title: title,
            xaxis: {
                title: xTitle
            },
            yaxis: {
                title: yTitle
            }
        };

        if (range > 0) {
            layout.xaxis.range = [0, (xValues[xValues.length - 1] - xValues[0]) / range];
        }

        Plotly.newPlot(divId, [trace], layout);
        document.getElementById(divId).classList.add("grafico-onda");
    }
});

function exibeCorteInf() {
    var canal = document.getElementById("canal-select").value;
    console.log(canal);
    if (canal === "PASSAFAIXAS") {
        document.getElementById("freq-inf").hidden = false;
        document.getElementById("freq-inf").classList.add("campo");
        document.getElementById("label-freq-corte").innerText = "Frequência de Corte Superior em kHz (1kHz - 100kHz)";
    }
    else
    {
        document.getElementById("freq-inf").classList.remove("campo");
        document.getElementById("freq-inf").hidden = true;
        document.getElementById("label-freq-corte").innerText = "Frequência de Corte em kHz (1kHz - 100kHz)";
    }
};