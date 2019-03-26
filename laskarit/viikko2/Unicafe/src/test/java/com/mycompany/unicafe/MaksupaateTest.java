package com.mycompany.unicafe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class MaksupaateTest {

    private Kassapaate cashRegister;

    @Before
    public void setup() {
        this.cashRegister = new Kassapaate();
    }

    @Test
    public void setupCorrect() {
        assertEquals(100000, this.cashRegister.kassassaRahaa());
        assertEquals(0, this.cashRegister.edullisiaLounaitaMyyty());
        assertEquals(0, this.cashRegister.maukkaitaLounaitaMyyty());
    }

    @Test
    public void testCashBuyCheapEnought() {
        assertEquals(0, this.cashRegister.syoEdullisesti(240));
        assertEquals(100240, this.cashRegister.kassassaRahaa());
        assertEquals(1, this.cashRegister.edullisiaLounaitaMyyty());
    }

    @Test
    public void testCashBuyCheapBroke() {
        assertEquals(239, this.cashRegister.syoEdullisesti(239));
        assertEquals(100000, this.cashRegister.kassassaRahaa());
        assertEquals(0, this.cashRegister.edullisiaLounaitaMyyty());
    }

    @Test
    public void testCashBuyRichEnought() {
        assertEquals(0, this.cashRegister.syoMaukkaasti(400));
        assertEquals(100400, this.cashRegister.kassassaRahaa());
        assertEquals(1, this.cashRegister.maukkaitaLounaitaMyyty());
    }

    @Test
    public void testCashBuyRichBroke() {
        assertEquals(399, this.cashRegister.syoMaukkaasti(399));
        assertEquals(100000, this.cashRegister.kassassaRahaa());
        assertEquals(0, this.cashRegister.maukkaitaLounaitaMyyty());
    }

    @Test
    public void testCardBuyCheapEnought() {
        Maksukortti card = new Maksukortti(240);

        assertTrue(this.cashRegister.syoEdullisesti(card));
        assertEquals(100000, this.cashRegister.kassassaRahaa());
        assertEquals(1, this.cashRegister.edullisiaLounaitaMyyty());
    }

    @Test
    public void testCardBuyCheapBroke() {
        Maksukortti card = new Maksukortti(239);

        assertFalse(this.cashRegister.syoEdullisesti(card));
        assertEquals(100000, this.cashRegister.kassassaRahaa());
        assertEquals(0, this.cashRegister.edullisiaLounaitaMyyty());
    }

    @Test
    public void testCardBuyRichEnought() {
        Maksukortti card = new Maksukortti(400);

        assertTrue(this.cashRegister.syoMaukkaasti(card));
        assertEquals(100000, this.cashRegister.kassassaRahaa());
        assertEquals(1, this.cashRegister.maukkaitaLounaitaMyyty());
    }

    @Test
    public void testCardBuyRichBroke() {
        Maksukortti card = new Maksukortti(399);

        assertFalse(this.cashRegister.syoMaukkaasti(card));
        assertEquals(100000, this.cashRegister.kassassaRahaa());
        assertEquals(0, this.cashRegister.maukkaitaLounaitaMyyty());
    }

    @Test
    public void loadCard() {
        Maksukortti card = new Maksukortti(0);

        this.cashRegister.lataaRahaaKortille(card, 100);

        assertEquals(100100, this.cashRegister.kassassaRahaa());
    }

    @Test
    public void loadCardNegative() {
        Maksukortti card = new Maksukortti(0);

        this.cashRegister.lataaRahaaKortille(card, -100);

        assertEquals(100000, this.cashRegister.kassassaRahaa());
    }
}
