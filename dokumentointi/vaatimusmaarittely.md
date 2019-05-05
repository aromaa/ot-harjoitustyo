# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla käyttäjät voivat keskustella keskenään P2P yhteyden avulla.

## Käyttäjät

Sovelluksen ensimmäisen avuksen yhteydessä luodaan private-public key pair, jonka avulla muut käyttäjät voivat "tunnistaa" käyttäjät toisistaan. Käyttäjät tallenetaan public-keyn perusteella "kontakteihin".

## Toteutettu toiminnallisuus

### Sovelluksen ensimmäisen avaus

- luodaan private-public key pair

- käyttäjältä pyydetään heidän kutsumanimeä
	- kutsumanimi ei ole uniikki
	- kutsumanimi täytyy olla vähintään yhden merkin ja enintään 32
	
- kutsumanimen valinnan jälkeen voi yhdistää johonkin käyttäjään

### Sovelluksen avaus jatkossa

- käytetään luotua private-public key pairia

- käyttäjä voi aloittaa keskustelun muodostamalla yhteyden toiseen käyttäjään

- käyttäjä voi myös etsiä muita käyttäjiä lähiverkosta, käyttäen discover ominaisuutta

## Jatkokehitysideoita

Perusversion jälkeen järjestelmää täydennetään ajan salliessa esim. seuraavilla toiminnallisuuksilla

- keskustelujen lataus tietokannasta ohjelmaa avatessa
	- tunnista mitkä viestit on lähetetty, ja mitkä odottaa vastaanottajan uudelleenyhdistämistä
- kuvien ja tiedostojen lähettäminen
- minipelejä (kivipaperisakset, tictactoe)
- alkeellinen ryhmä keskustelu