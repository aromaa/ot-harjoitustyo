# Testausdokumentti

Ohjelman testaus on tehty yksikkö- ja integraatiotestein JUnitilla sekä järjestelmätason testein.

## Yksikkö- ja integraatiotestaaminen

### Sovelluslogiikka

Automatisoitudut testit huolehtivat siitä, että kriittinen päätoiminnalisuus toimii oikein. Pakkaukset fi.joniaromaa.p2pchat.chat, fi.joniaromaa.p2pchat.event ja fi.joniaromaa.p2pchat.identity testataan läpikotaisin automaattisilla testeillä. Myös käyttäjän "henkilöllisyyden" varmistamiseen käytetty menettely testaan.

### Tiedon tallennus (fi.joniaromaa.p2pchat.storage)

Tiedon tallentamista käsittelevät luokat testataan luomalla muistissa oleva SQLite tietokanata.

### Testikattavuus

Käyttöliittymätiedostoja lukuunottamatta sovelluksen testauksen rivikattavuus on 10% ja haarautumiskattavuus on 90%.

<img src="https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/kuvat/testikattavuus.png">

Testeistä jäi pois luokat, jotka on vastuussa fyysisestä yhteydestä laitteiden välillä. Myös käyttöjärjestelmästä riippuva toiminnallisuus on jätetty pois.

## Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

### Asennus ja konfigurointi

Sovellus on testautta seuraamalla käyttöohjetta Windows-ympäristössä.

## Toiminnallisuus

Kaikki määrittelydokumentin ja käyttöohjeen listaamat toiminnalisuudet on käytö läpi ja todettu toimiviksi. Virheellisiä syötteitä ei voi antaa, eikä ohjelma kaadu missään vaiheessa.