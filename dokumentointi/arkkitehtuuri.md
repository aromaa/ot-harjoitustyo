# Sovelluslogiikka

## Tiedon tallennus

<img src="https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/kuvat/sovelluslogiikka_storage.png">

## Tiedon käyttö

<img src="https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/kuvat/sovelluslogiikka_managers.png">

## Käyttäjiin yhdistäminen

Käyttäjiin yhdistämisen yhteydessä täytyy varmistaa heidän "henkilöllisyys", sekä vastaanottaja, että lähettäjä haluavat varmistaa, että molemmat osapuolet ovat ketä he väittävät olevansa

Prosessin ensimmäinen vaihe on pyytää toiselta osapuolelta niin sanottua "haastetta", jonka meidän olisi tarkoitus ratkaista. Kun saamma pyynnön lähettää haasteen, generoimme 2048 bitin kokoisen haasteen ja läheteämme sen vastauksena.

Kun käyttäjä vastaanottaa tämän haasteen heidän on tarkoitus ratkaista se, tässä tapauksessa heidän haasteena on digitaalisesti allekirjoittaa haasteen sisältö heidän yksityisellä avaimella. Tämän jälkeen viestiin vastataan lähettämällä allekirjoitettu haaste, että heidän julkinen avaimensa.

Kun julkinen avain vastaanotetaan, sen avulla voidaan tarkistaa, että kyseisen haasteen sisältö on allekirjoitettu julkisen avaimen yksityisellä avaimella. Jos näin on, voimme sanoa varmasti, että lähettäjä on julkisen avaimen omistaja.

Tämä prosessi mahdollistaa, että käyttäjä voidaan tunnistaa epäluotettavassakin verkossa, ja että sama julkinen avain aina vastaa sitä yhtä tiettyä henkilöä, kunhan ensiksi tämä vahvistetaan.

<img src="https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/kuvat/verification.png">