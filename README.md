# PokeAPIGame
This game was developed in the context of learning Java during Mindera School.
As a requirement we had to use PokeAPI.
As the learning progressed, the project became a Maven project with some associated SQL.
I took the opportunity to learn new features such as adding colors to the Terminal, accessing the Database and making the necessary requests.
I really enjoyed doing this project, as I was able to apply some prior knowledge and improve my own programming.

# How the game Works?
The game is in the style of Cards, each player receives 4 Pokemon cards according to the type of pokemon that he chooses.<br/>

1 - Flying 🍃<br/>
2 - Bug 🐛<br/>
3 - Ghost 👻<br/>
4 - Fire 🔥<br/>
5 - Water 🌊<br/>
6 - Grass 🍃<br/>

Each card has a number of points based on PokeAPI's "base_experience".
Once we choose the type of pokemons we are going to play the game with, it really starts. The User can perform 7 actions.

1 - Upgrade Card 🔝<br/>
2 - Ask New Card 🃏<br/>
3 - Go Battle ⚔<br/>
4 - See My Cards ♣<br/>
5 - Quit Game 🚪<br/>
6 - Save Game 📂<br/>
7 - Ranking 🥇<br/>

The user can only use 2x the "Upgrade Card 🔝" and 3x the "Ask New Card 🃏"

**Upgrade Card 🔝**<br/>
Increase a card's score, but be careful because if you are unlucky you can decrease your card's score.

**Ask New Card 🃏**<br/>
Replace one of the cards. If you have one bad Card you can request another. You can get one worse or better.

**Go Battle ⚔**<br/>
Playing against the computer he randomly generates a deck every round and each time you advance a round he gets an extra 5 points.

**See My Cards ♣**<br/>
See the four cards in your hand.

**Quit Game 🚪**<br/>
Shutdown Game.

**Save Game 📂**<br/>
Save everything you need about the game to resume later.

**Ranking 🥇**<br/>
Show a RankList with TOP players. Green marker to know where are you.

# Account
Create an account. Log into the account and view the account as well as making the necessary changes. This part is organized with a database, thus applying Maven with the dependency "mysql-connector-java".

At the and I am HAPPY to learn all this things about the world that I love ❤️.
