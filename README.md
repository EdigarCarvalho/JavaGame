# Jogo "O Jogo mais incrível"

Este é um jogo desenvolvido como parte de um trabalho por Edigar de Almeida Carvalho, Ian Silva dos Santos e Luciano Amorim de Sousa. O jogo é uma implementação simples e divertida usando Java e a biblioteca Swing.

## Como Jogar

O objetivo do jogo é acumular pontos ao evitar colisões com inimigos em movimento na tela. Aqui estão as principais funcionalidades e controles:

- Use as setas direcionais para mover o jogador horizontalmente e verticalmente.
- A pontuação é incrementada ao evitar colisões com inimigos em movimento.
- Cada colisão reduz a quantidade de vidas disponíveis.
- Se todas as vidas forem perdidas, o jogo exibirá a tela de fim de jogo.
- Pressione a tecla ESPAÇO para reiniciar o jogo após o fim de uma partida.

## Recursos e Gráficos

- O plano de fundo do jogo é uma imagem de fundo (background.jpeg).
- O jogador é representado por uma animação de ciclos (sprites armazenados em /ciclo).
- Inimigos são representados por sprites de inimigos (sprites armazenados em /inimigo).
- Um pássaro animado (bird-flap-animation.gif) está presente no jogo.

## Controles Adicionais

- O jogo responde ao movimento do mouse para controlar um pássaro adicional na tela.
- A tecla ESPAÇO reinicia o jogo quando todas as vidas são perdidas.

## Desenvolvimento Técnico

- O jogo é desenvolvido em Java usando a biblioteca Swing para a interface gráfica.
- Um `SwingWorker` é utilizado para movimentar o pássaro continuamente.
- Imagens são carregadas a partir de arquivos usando a classe `ImageIO`.
- Controles do teclado e do mouse são implementados para interação do jogador.
- O jogo possui manipulação de colisões e controle de vidas.

## Como Executar

Certifique-se de ter uma máquina Java instalada. Compile e execute o código usando um ambiente de desenvolvimento Java ou a linha de comando.

```bash
javac Game.java
java Game
```

## Dependências

O jogo depende da biblioteca Swing para a interface gráfica e do Java para a execução. Certifique-se de ter uma versão compatível do Java instalada.

## Observações

Este jogo foi desenvolvido como parte de um trabalho acadêmico e é destinado apenas para fins educacionais e de demonstração.

## Gameplay

![Captura de Tela (1)](https://github.com/EdigarCarvalho/JavaGame/assets/106999716/dc6e928f-658d-4a44-85df-82f5b19ad807)

Player
![ciclo1](https://github.com/EdigarCarvalho/JavaGame/assets/106999716/1d335764-fdc6-4d4c-bf4e-209455544ce8)

Player2
![bird-flap-animation](https://github.com/EdigarCarvalho/JavaGame/assets/106999716/abb930e7-b2c8-481a-aea7-b2b5ffc19e65)

Enemy
![inimigo2](https://github.com/EdigarCarvalho/JavaGame/assets/106999716/e0f07fd0-2e81-47e7-8ea5-40737f66ecc8)

