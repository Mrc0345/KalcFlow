package com.example.kalcflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalcflow.ui.theme.KalcFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalcFlowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculadoraScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CalculadoraScreen(modifier: Modifier = Modifier) {
    var textoHistorico by remember { mutableStateOf("") }
    var textoResultado by remember { mutableStateOf("0") }
    var valor1 by remember { mutableStateOf(0.0) }
    var operacaoSelecionada by remember { mutableStateOf("") }
    var precisaLimparTela by remember { mutableStateOf(false) }

    fun formatar(valor: Double): String {
        return if (valor % 1.0 == 0.0) {
            valor.toLong().toString()
        } else {
            valor.toString()
        }
    }

    fun clicarNumero(num: String) {
        if (precisaLimparTela || textoResultado == "0") {
            textoResultado = num
            precisaLimparTela = false
        } else {
            textoResultado += num
        }
    }

    fun clicarPonto() {
        if (precisaLimparTela) {
            textoResultado = "0."
            precisaLimparTela = false
        } else if (!textoResultado.contains(".")) {
            textoResultado += "."
        }
    }

    fun clicarOperacao(op: String) {
        valor1 = textoResultado.toDoubleOrNull() ?: 0.0
        operacaoSelecionada = op
        textoHistorico = "${formatar(valor1)} $op"
        precisaLimparTela = true
    }

    fun clicarIgual() {
        if (operacaoSelecionada.isNotEmpty()) {
            val valor2 = textoResultado.toDoubleOrNull() ?: 0.0
            var total = 0.0

            if (operacaoSelecionada == "+") {
                total = valor1 + valor2
            } else if (operacaoSelecionada == "-") {
                total = valor1 - valor2
            } else if (operacaoSelecionada == "*") {
                total = valor1 * valor2
            } else if (operacaoSelecionada == "/") {
                if (valor2 != 0.0) {
                    total = valor1 / valor2
                } else {
                    textoResultado = "Erro"
                    operacaoSelecionada = ""
                    precisaLimparTela = true
                    return
                }
            }

            textoHistorico = "${formatar(valor1)} $operacaoSelecionada ${formatar(valor2)}"
            textoResultado = formatar(total)
            operacaoSelecionada = ""
            precisaLimparTela = true
        }
    }

    fun limparTudo() {
        textoHistorico = ""
        textoResultado = "0"
        valor1 = 0.0
        operacaoSelecionada = ""
        precisaLimparTela = false
    }

    fun trocarSinal() {
        val num = textoResultado.toDoubleOrNull() ?: 0.0
        val resultado = num * -1
        textoResultado = formatar(resultado)
    }

    fun calcularPorcentagem() {
        val num = textoResultado.toDoubleOrNull() ?: 0.0
        val resultado = num / 100
        textoResultado = formatar(resultado)
    }

    val fundoGeral = Color(0xFF1B1E26)
    val corBotoesNumeros = Color(0xFF2E3440)
    val corBotoesTopo = Color(0xFF434C5E)
    val corLaranja = Color(0xFFE67E22)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(fundoGeral)
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        // Painel Superior: Histórico
        Text(
            text = textoHistorico,
            fontSize = 22.sp,
            color = Color.LightGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Painel Principal: Destaque
        Text(
            text = textoResultado,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Linha 1
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MeuBotao("C", corBotoesTopo, Modifier.weight(1f)) { limparTudo() }
            MeuBotao("+/-", corBotoesTopo, Modifier.weight(1f)) { trocarSinal() }
            MeuBotao("%", corBotoesTopo, Modifier.weight(1f)) { calcularPorcentagem() }
            MeuBotao("/", corLaranja, Modifier.weight(1f)) { clicarOperacao("/") }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Linha 2
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MeuBotao("7", corBotoesNumeros, Modifier.weight(1f)) { clicarNumero("7") }
            MeuBotao("8", corBotoesNumeros, Modifier.weight(1f)) { clicarNumero("8") }
            MeuBotao("9", corBotoesNumeros, Modifier.weight(1f)) { clicarNumero("9") }
            MeuBotao("*", corLaranja, Modifier.weight(1f)) { clicarOperacao("*") }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Linha 3
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MeuBotao("4", corBotoesNumeros, Modifier.weight(1f)) { clicarNumero("4") }
            MeuBotao("5", corBotoesNumeros, Modifier.weight(1f)) { clicarNumero("5") }
            MeuBotao("6", corBotoesNumeros, Modifier.weight(1f)) { clicarNumero("6") }
            MeuBotao("-", corLaranja, Modifier.weight(1f)) { clicarOperacao("-") }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Linha 4
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MeuBotao("1", corBotoesNumeros, Modifier.weight(1f)) { clicarNumero("1") }
            MeuBotao("2", corBotoesNumeros, Modifier.weight(1f)) { clicarNumero("2") }
            MeuBotao("3", corBotoesNumeros, Modifier.weight(1f)) { clicarNumero("3") }
            MeuBotao("+", corLaranja, Modifier.weight(1f)) { clicarOperacao("+") }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Linha 5
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MeuBotao("0", corBotoesNumeros, Modifier.weight(2f)) { clicarNumero("0") }
            MeuBotao(".", corBotoesNumeros, Modifier.weight(1f)) { clicarPonto() }
            MeuBotao("=", corLaranja, Modifier.weight(1f)) { clicarIgual() }
        }
    }
}

@Composable
fun MeuBotao(
    rotulo: String,
    corFundo: Color,
    modifier: Modifier = Modifier,
    aoClicar: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(if (rotulo == "0") 2.1f else 1f)
            .clip(RoundedCornerShape(20.dp))
            .background(corFundo)
            .clickable { aoClicar() }
    ) {
        Text(
            text = rotulo,
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculadoraPreview() {
    KalcFlowTheme {
        CalculadoraScreen()
    }
}