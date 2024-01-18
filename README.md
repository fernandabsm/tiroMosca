# Tiro e Mosca

### Tiro e Mosca foi um jogo desenvolvido como parte avaliativa da disciplina Redes de Computadores do curso de Engenharia de Computação da UFOP. 
### A ideia é unir estratégia e sorte para adivinhar o número escolhido pelo oponente. Cada jogador escolhe um número de 3 dígitos distintos e o objetivo é adivinhar primeiro o número escolhido pelo adversário.
### O resultado de cada rodada é a indicação se o jogador teve algum acerto. Caso tenha acertado um dígito, mas na posição errada, é indicado 1 tiro. Caso tenha acertado um dígito na posição certa, é indicada 1 mosca. Por exemplo, o número escolhido pelo jogador A foi 123 e o jogador B teve como palpite o número 253. Será indicado que ele obteve 1 tiro e 1 mosca. Essas dicas são dadas a cada rodada, até que alguém adivinhe o número do oponente.

### O jogo conta com um modo multiplayer, que permite jogar online contra um outro jogador e um modo local, em que o usuário joga contra o computador.

# Protótipo do jogo
[![Visualizar no Figma](https://github.com/fernandabsm/tiroMosca/assets/74023503/384f7a53-42d1-40a8-8ab1-095cae8b615f)](https://www.figma.com/embed?embed_host=share&url=https%3A%2F%2Fwww.figma.com%2Ffile%2Fcth2B1uN9BhH3fW9awkWCg%2FTiro-e-Mosca%3Ftype%3Ddesign%26node-id%3D0%253A1%26mode%3Ddesign%26t%3DfUyZQxTMjmQ6q5Kg-1)

# Como executar

### ⚠ É recomendo que se utilize a IDE Intellij Ultimate para executar o programa, pois ela te dará suporte aos arquivos CSS e ao JavaFX. Você pode conseguir uma licença gratuita usando o email estudantil. [Veja aqui!](https://www.jetbrains.com/community/education/#students)

### 1. Execute o arquivo GameServer
<br>&nbsp;&nbsp;&nbsp;📁 com.tiromosca.network.connection.server</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;📄 GameServer

### Isso inicializará o servidor. Na linha 62, é estabelecido o ip do host como o IP local ("127.0.0.1"). Para jogar online entre 2 computadores diferentes, é necessário estabeler um IP host válido, disponível para conexão.

### 2. Abra e altere o arquivo PlayerClient
<br>&nbsp;&nbsp;&nbsp;📁 com.tiromosca.network.connection.client</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;📄 PlayerClient

### Na linha 85, altere para o mesmo IP definido no servidor.

### 3. Execute o arquivo HelloApplication
<br>&nbsp;&nbsp;&nbsp;📁 com.tiromosca.network</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;📄 HelloApplication

### Isso inicializará a interface do jogo. O jogador pode escolher jogar o modo multiplayer ou o modo singleplayer. Caso queira jogar apenas o modo contra o PC, não é necessário executar os passos anteriores.

