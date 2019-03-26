package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    @Test
    public void startMoneyCorrect() {
        assertEquals(10, this.kortti.saldo());
        assertEquals("saldo: 0.10", this.kortti.toString());
    }

    @Test
    public void addMoney() {
        this.kortti.lataaRahaa(56);
        assertEquals(66, this.kortti.saldo());
    }

    @Test
    public void removeMoney() {
        assertTrue(this.kortti.otaRahaa(10));
        assertEquals(0, this.kortti.saldo());
    }

    @Test
    public void removeMoneyOver() {
        assertFalse(this.kortti.otaRahaa(11));
        assertEquals(10, this.kortti.saldo());
    }
}
