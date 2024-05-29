$(document).ready(function () {
    $("#criar-simulacao").click(function () {
        let categoria = $("#categoria-select").val();
        let frequencia = $("#frequencia").val();
        let canal = $("#canal-select").val();
        let frequenciaCorteSup = $("#frequencia-corte-sup").val();
        let frequenciaCorteInf = $("#frequencia-corte-inf").val();

        let valido = validarDados();

        if (!frequenciaCorteInf) {
            frequenciaCorteInf = 0; // Define o valor como 0 se estiver vazio
        }

        if (frequenciaCorteSup == 0) {
            frequenciaCorteSup = frequencia; // Iguala a frequencia de corte superior à frequencia se for 0
        }


        if (valido) {
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
        }
    })});

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
        plotGraph('sinal-entrada', 'Sinal de Entrada', data.sinaisEntrada, 'Tempo (segundos)', 'Amplitude', 'lines', { x: 10, y: 1.2 });
        plotGraph('sinal-saida', 'Sinal de Saída', data.sinaisSaida, 'Tempo (segundos)', 'Amplitude', 'lines', { x: 10, y: 1.2 });
        plotGraph('modulo-resposta', 'Módulo da Resposta em Frequência', data.respostaModuloCanal, 'Frequência (Hz)', 'Módulo', 'lines', { x: 10, y: 0 });
        plotGraph('fase-resposta', 'Fase da Resposta em Frequência', data.respostaFaseCanal, 'Frequência (Hz)', 'Fase (graus)', 'lines', { x: 10, y: 0 });
        plotGraph('amplitudes', 'Amplitude por Frequência de Entrada', data.amplitudePorFrequenciaEntrada, 'Frequência (Hz)', 'Amplitude', 'bar', { x: 1.5, y: 0 });
        plotGraph('fases', 'Fase por Frequência de Entrada', data.fasePorFrequenciaEntrada, 'Frequência (Hz)', 'Fase (graus)', 'bar', { x: 1.5, y: 0 });
        plotGraph('amplitudes-saida', 'Amplitude por Frequência de Saída', data.amplitudePorFrequenciaSaida, 'Frequência (Hz)', 'Amplitude', 'bar', { x: 1.5, y: 0 });
        plotGraph('fases-saida', 'Fase por Frequência de Saída', data.fasePorFrequenciaSaida, 'Frequência (Hz)', 'Fase (graus)', 'bar', { x: 1.5, y: 0 });
    }

    function plotGraph(divId, title, data, xTitle, yTitle, type, range) {
        let xValues = Object.keys(data);
        let yValues = Object.values(data);
        let cor = 'hsl(0 60% 35%)';
        let trace;
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
            };
        }

        let layout = {
            title: title,
            xaxis: {
                title: xTitle
            },
            yaxis: {
                title: yTitle
            },
            width: 930,
            height: 400,
            paper_bgcolor: 'rgba(0,0,0,0)',  // Define o fundo do gráfico como transparente
            plot_bgcolor: 'rgba(0,0,0,0)'    // Define a área do traçado como transparente
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