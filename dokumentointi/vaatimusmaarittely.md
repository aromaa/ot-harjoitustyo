# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla käyttäjät voivat keskustella keskenään P2P yhteyden avulla. Keskustelut talletetaan lokaalisesti SQLite tietokantaan. 

## Käyttäjät

Sovelluksen ensimmäisen avuksen yhteydessä luodaan private-public key pair, jonka avulla muut käyttäjät voivat "tunnistaa" käyttäjät toisistaan perustuen tallentamineen "kontakteihin"

## Perusversion tarjoama toiminnallisuus

### Sovelluksen ensimmäisen avaus

- luodaan private-public key pair

- käyttäjältä pyydetään heidän kutsumanimeä
	- kutsumanimi ei ole uniikki
	- kutsumanimi täytyy olla vähintään yhden merkin ja enintään 32
	- saa sisältää vain kirjaimia, numeroita ja joitakin erikoismerkkejä
	
- kutsumanimen valinnan jälkeen voi yhdistää johonkin käyttäjään

### Sovelluksen avaus jatkossa ja toiminnallisuus

- käytetään luotua private-public key pairia

- käyttäjän edelliset keskustelut ladataan tietokannasta

- käyttäjä voi aloittaa uuden keskustelun tai jatkaa vanhasta

## Jatkokehitysideoita

Perusversion jälkeen järjestelmää täydennetään ajan salliessa esim. seuraavilla toiminnallisuuksilla

- kuvien ja tiedostojen lähettäminen
- minipelejä (kivipaperisakset, tictactoe)
- alkeellinen ryhmä keskustelu