# P2P Chat

Sovelluksen avulla käyttäjät voivat jutella ja mahdollisesti jakaa tiedostoja P2P yhteyden avulla. Käyttäjä voi vastaanottaa ja muodostaa useampia viestejä samanaikaisesti erihenkilöiötä.

## Dokumentaatio

[Arkkitehtuuri](https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Vaatimusmäärittely](https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

## Releaset

[Viikko 5](https://github.com/isokissa3/ot-harjoitustyo/releases/tag/viikko5)

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Generointi suoritetaan komennolla

```
mvn package
```

Generoi hakemistoon _target_ suoritettavan jar-tiedoston _p2pchat-1.0.0-SNAPSHOT.jar_
