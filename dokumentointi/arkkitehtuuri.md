# Sovelluslogiikka

## Käyttäjiin yhdistäminen

Käyttäjiin yhdistämisen yhteydessä täytyy varmistaa heidän "henkilöllisyys", sekä vastaanottaja, että lähettäjä haluavat varmistaa, että molemmat osapuolet ovat ketä he väittävät olevansa

Prosessin ensimmäinen vaihe on pyytää toiselta osapuolelta niin sanottua "haastetta", jonka meidän olisi tarkoitus ratkaista. Kun saamma pyynnön lähettää haasteen, generoimme 2048 bitin kokoisen haasteen ja läheteämme sen vastauksena.

Kun käyttäjä vastaanottaa tämän haasteen heidän on tarkoitus ratkaista se, tässä tapauksessa heidän haasteena on digitaalisesti allekirjoittaa haasteen sisältö heidän yksityisellä avaimella. Tämän jälkeen viestiin vastataan lähettämällä allekirjoitettu haaste, että heidän julkinen avaimensa.

Kun julkinen avain vastaanotetaan, sen avulla voidaan tarkistaa, että kyseisen haasteen sisältö on allekirjoitettu julkisen avaimen yksityisellä avaimella. Jos näin on, voimme sanoa varmasti, että lähettäjä on julkisen avaimen omistaja.

Tämä prosessi mahdollistaa, että käyttäjä voidaan tunnistaa epäluotettavassakin verkossa, ja että sama julkinen avain aina vastaa sitä yhtä tiettyä henkilöä, kunhan ensiksi tämä vahvistetaan.

<img src="https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/kuvat/verification.png">

## Tiedon tallennus

Tiedon tallennus on luotu mahdollisimman venyväksi ja helposti muotoiltavaksi. Päällimäisin rajapinta mahdollistaa eri dao rajapintojen haun. Storage rajapinta voi olla mikä tahansa tiedosto tyyppiä käsittelevä ohjelmisto, joka luo omaa rajapintaa käyttäen suuremman tason dao rajapinnat.

<img src="https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/kuvat/sovelluslogiikka_storage.png">

## Tiedon käyttö (Chat)

Tiedon lataaminen ja käsitteleminen on tehty helpoksi ja mahdollisimman optimoidusti. Chat Manageriin on yhdistetty Chat Conversation Manager, joka pitää yllä kaikkia keskusteltuja välimuistissa. Jos jotain keskustelua ei löydy välimuistista se haetaan Conversation Daosta tai mahdollisesti luodaan uusi.

Myös Contact Identity Manager on osa Chat Manageria, joka ylläpitää Cotnacts Daosta löytyvien tietojen välimuistia.

Chat Manager itse pitää välimuistissa Storage rajapintaa, että tämän hetkistä käyttäjää

<img src="https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/kuvat/sovelluslogiikka_managers.png">