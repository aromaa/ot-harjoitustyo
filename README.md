# P2P Chat

Sovelluksen avulla käyttäjät voivat jutella ja mahdollisesti jakaa tiedostoja P2P yhteyden avulla. Käyttäjä voi vastaanottaa ja muodostaa useampia viestejä samanaikaisesti erihenkilöiötä.

## Dokumentaatio

[Arkkitehtuuri](https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Käyttöohje](https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/kayottoohje.md)

[Testausdokumentti](https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/testaus.md)

[Työaikakirjanpito](https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/tuntikirjanpito.md)

[Vaatimusmäärittely](https://github.com/isokissa3/ot-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

## Releaset

[Loppupalautus](https://github.com/isokissa3/ot-harjoitustyo/releases/tag/loppupalautus)

[Viikko 6](https://github.com/isokissa3/ot-harjoitustyo/releases/tag/viikko6)

[Viikko 5](https://github.com/isokissa3/ot-harjoitustyo/releases/tag/viikko5)

### Suoritettavan jarin generointi

Generointi suoritetaan komennolla

```
mvn package
```

Generoi hakemistoon _target_ suoritettavan jar-tiedoston _p2pchat-1.0.0-SNAPSHOT.jar_

### Checkstyle

Checkstylen noudattamisen voi tarkistaa seuraavalla komennolla

```
mvn jxr:jxr checkstyle:checkstyle
```

Checkstylen raporttia voi tarkastella avaamalla selaimella tiedosto _target/site/checkstyle.html_

### Testit

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn test jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### JavaDoc

JavaDocin generointi suoritetaan komennolla

```
mvn javadoc:javadoc
```

Javadocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_