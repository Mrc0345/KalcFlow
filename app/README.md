# KalcFlow 📱

Aplicativo de calculadora moderno e intuitivo desenvolvido nativamente para Android utilizando **Kotlin** e **Jetpack Compose**, com foco em precisão aritmética e interface inspirada em diretrizes contemporâneas de design (*dark theme*).

---

## 📌 Funcionalidades

- **Operações Aritméticas Básicas:**
    - Soma (`+`)
    - Subtração (`-`)
    - Multiplicação (`*`)
    - Divisão (`/`) com tratamento contra divisão por zero
- **Operações Especiais:**
    - Inversão de sinal (`+/-`)
    - Cálculo de porcentagem direta (`%`)
    - Limpeza e reset de memória (`C`)
    - Ponto decimal para cálculos fracionários (`.`)
- **Interface Reativa:**
    - Exibição da expressão em andamento no painel superior (histórico).
    - Exibição do resultado atual com destaque tipográfico.
    - Formatação inteligente que oculta o `.0` desnecessário em números inteiros.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** [Kotlin](https://kotlinlang.org/)
- **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Componentes:** Material 3
- **Gerenciamento de Estado:** `remember` e `mutableStateOf`

---

## 📂 Estrutura Principal

```text
app/src/main/java/com/example/kalcflow/
│
├── MainActivity.kt        # Interface declarativa (Compose) e controle de estado
└── ui/theme/              # Tema, tipografia e configurações de cores