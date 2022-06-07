# teste-zappts-java
## API  de cartas, decks e jogadores

Essa API tem como objetivo armazenar cartas, nesse caso do jogo Magic The Gathering!
Nesse sistema, primeiramente temos que adicionar um jogador, após esse jogador pode adicionar um ou mais decks e cada deck pode conter N cartas.
As opções dessa API são de ver todas as cartas de todos os jogadores, salvar cartas e deletar cartas.
Na parte de decks, pode-se adicionar um deck, listar todos os decks, buscar um deck específico, deletar um deck e fazer atualização de algum deck.
Já na parte de jogadores, podemos adicionar um jogador, listar todos os jogadores, buscar um jogador específico, deletar um jogador ou atualizar.

Como esse sistema está no perfil de "test", quando rodar a aplicação o banco de dados que armazena as informaçãoes é o H2, sendo que já possui alguns dados 
de jogadores, decks e cartas pré-carregados no sistema. Para acessá-los basta digitar no bronwser localhost:8080/h2-console, caso peça usuário e senha, basta digitar
admin em ambos.
Esse sistema também possuí uma documentação básica com Swagger, para acessar basta digitar no bronwser: localhost:8080/swagger-ui.html se pedir usuário e senha é o mesmo do descrito acima.
