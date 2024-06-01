$(document).ready(function () {
    $("#criar-simulacao").click(function () {

        let categoria = $("#categoria-select").val();
        let frequencia = $("#frequencia").val();
        let canal = $("#canal-select").val();
        let frequenciaCorteSup = $("#frequencia-corte-sup").val();
        let frequenciaCorteInf = $("#frequencia-corte-inf").val();
        let valido = validarDados();
        document.getElementById("loadingDots").style.display = "flex";
        if (!frequenciaCorteInf) {
            frequenciaCorteInf = 0; // Define o valor como 0 se estiver vazio
        }

        if (frequenciaCorteSup == 0) {
            frequenciaCorteSup = frequencia; // Iguala a frequencia de corte superior à frequencia se for 0
        }


        if (valido) {

            document.getElementById("borda-mw").hidden = false;
            document.getElementById("borda-mw").classList.add("borda-mw");
            document.getElementById("borda-mw").classList.add("borda-mw-simul");
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
                    document.getElementById("loadingDots").style.display = "none";
                    exibirGraficos(response);
                },
                error: function () {
                    document.getElementById("loadingDots").style.display = "none";

                    alert("Erro ao processar a solicitação");
                }
            });
        }
    })
});

function validarDados() {
    let msgPreencha = 'Preencha todos os campos!';
    let msgValor = 'Insira valores entre 1 e 100!';
    let msgMaiorQue = 'A Frequência de Corte Superior deve ser maior que a Frequência de Corte Inferior!'
    let frequencia = document.getElementById("frequencia").value
    let frequenciaCorteSup = document.getElementById("frequencia-corte-sup").value;
    let frequenciaCorteInf = document.getElementById("frequencia-corte-inf").value;
    let canal = document.getElementById("canal-select").value;

    if (frequencia == '' || frequenciaCorteSup == '') {
        alert(msgPreencha);
        return false;
    }

    if (frequenciaCorteSup < 1 || frequenciaCorteSup > 100 || frequencia < 1 || frequencia > 100) {
        alert(msgValor);
        return false;
    }

    if (canal == 'PASSAFAIXAS') {
        if (frequenciaCorteInf == '') {
            alert(msgPreencha);
            return false;
        }
        if (frequenciaCorteInf < 1 || frequenciaCorteInf > 100) {
            alert(msgValor);
            return false;
        }
        if (frequenciaCorteSup < frequenciaCorteInf) {
            alert(msgMaiorQue);
            return false;
        }
    }

    return true;
}

// Função para exibir gráficos com Plotly.js
function exibirGraficos(data) {
    plotGraph('sinal-entrada', 'Sinal de Entrada', data.sinaisEntrada, 'Tempo (ms)', 'Amplitude (Volts)', 'lines', { x: 10, y: 1.2 });
    plotGraph('sinal-saida', 'Sinal de Saída', data.sinaisSaida, 'Tempo (ms)', 'Amplitude (Volts)', 'lines', { x: 10, y: 1.2 });
    plotGraph('modulo-resposta', 'Módulo da Resposta em Frequência', data.respostaModuloCanal, 'Frequência (kHz)', 'Módulo', 'lines', { x: 0, y: 0 });
    plotGraph('fase-resposta', 'Fase da Resposta em Frequência', data.respostaFaseCanal, 'Frequência (kHz)', 'Fase (graus)', 'lines', { x: 0, y: 0 });
    plotGraph('amplitudes', 'Amplitude por Frequência de Entrada', data.amplitudePorFrequenciaEntrada, 'Frequência (kHz)', 'Amplitude (Volts)', 'bar', { x: 0, y: 0 });
    plotGraph('fases', 'Fase por Frequência de Entrada', data.fasePorFrequenciaEntrada, 'Frequência (kHz)', 'Fase (graus)', 'bar', { x: 0, y: 0 });
    plotGraph('amplitudes-saida', 'Amplitude por Frequência de Saída', data.amplitudePorFrequenciaSaida, 'Frequência (kHz)', 'Amplitude (Volts)', 'bar', { x: 0, y: 0 });
    plotGraph('fases-saida', 'Fase por Frequência de Saída', data.fasePorFrequenciaSaida, 'Frequência (kHz)', 'Fase (graus)', 'bar', { x: 0, y: 0 });
}

function plotGraph(divId, title, data, xTitle, yTitle, type, range) {
    let xValues = Object.keys(data);
    let yValues = Object.values(data);
    let cor = 'hsl(225, 30%, 70%)'; // Cor das linhas ou bordas das barras
    let corTexto = 'hsl(0, 0%, 100%)'; // Cor do texto
    let corBarra = 'hsl(225, 25%, 50%, 0.2)'; // Cor de preenchimento das barras
    let trace;
    let fonte = 'Roboto, sans-serif';

    if (type === 'lines') {
        trace = {
            x: xValues,
            y: yValues,
            type: 'scatter',
            mode: 'lines',
            name: title,
            line: {
                color: cor
            }
        };
    } else {
        trace = {
            x: xValues,
            y: yValues,
            type: 'bar',
            name: title,
            marker: {
                color: cor,
                line: {
                    color: cor,
                    width: 2.0
                }
            }
        };
    }

    let layout = {
        title: title,
        xaxis: {
            title: xTitle,
            showgrid: false, // Esconde o grid do eixo X
            zeroline: true, // Mostra a linha central do eixo X
            zerolinecolor: corBarra, // Cor da linha central do eixo X
            ticksuffix: '  ' // Adiciona espaço extra após os rótulos dos ticks no eixo X
        },
        yaxis: {
            title: yTitle,
            showgrid: false,
            zeroline: true,
            zerolinecolor: corBarra,
            ticksuffix: '  ' // Adiciona espaço extra após os rótulos dos ticks no eixo Y
        },
        width: 930,
        height: 400,
        paper_bgcolor: 'rgba(255,255,255,0)', // Define o fundo do gráfico como transparente
        plot_bgcolor: 'rgba(0,0,0,0)', // Define a área do traçado como transparente
        font: {
            family: fonte,
            size: 14,
            color: corTexto // Define a cor do texto dos títulos dos eixos e do título do gráfico
        }
    };


    if (range.x > 0) {
        layout.xaxis.range = [0, (xValues[xValues.length - 1] - xValues[0]) / range.x];
    }

    if (range.y > 0) {
        layout.yaxis.range = [-range.y, range.y];
    }

    Plotly.newPlot(divId, [trace], layout);
    document.getElementById(divId).classList.add("grafico-onda");
}