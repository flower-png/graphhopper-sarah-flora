package com.graphhopper.storage;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntsRefTest {

    /**
     * Vérifier que isValid() rejette un tableau null dans le constructeur
     * en lançant IllegalStateException avec message "ints is null"
     * ints=null pour tableau null, offset=0 et length=0 pour ne pas interférer
     * avec les autres branches
     */
    @Test
    public void IntsRef_isValid_nullIntsArray_throwsIllegalStateException() {
        try {
            new IntsRef(null, 0, 0);
        } catch (IllegalStateException ex) {
            assertEquals("ints is null", ex.getMessage());
        }
    }

    /**
     * Vérifier que isValid() rejette longueur négative dans le constructeur
     * en lançant IllegalStateException avec message commençant par "length is negative:"
     * ints=[] et offset=0 pour ne pas interférer avec les autres branches 
     * et length=-1 pour longueur négative
     */
    @Test
    public void IntsRef_isValid_negativeLength_throwsIllegalStateException() {
        try {
            new IntsRef(new int[]{}, 0, -1);
        } catch (IllegalStateException ex) {
            assertTrue(ex.getMessage().startsWith("length is negative:"));
        }
    }

    /**
     * Vérifier que isValid() rejette length > ints.length dans le constructeur
     * en lançant IllegalStateException avec message commençant par "length is out of bounds:"
     * ints=[] et length=5 pour dépasser la longueur du tableau (5 > 0) et
     * offset=0 pour ne pas interférer avec les autres branches
     */
    @Test
    public void IntsRef_isValid_lengthOutOfBounds_throwsIllegalStateException() {
        try {
            new IntsRef(new int[]{}, 0, 5);
        } catch (IllegalStateException ex) {
            assertTrue(ex.getMessage().startsWith("length is out of bounds:"));
        }
    }

    /**
     * Vérifier que isValid() rejette offset négatif dans le constructeur
     * en lançant IllegalStateException avec message commençant par "offset is negative:"
     * ints=[] et length=0 pour ne pas interférer avec les autres branches
     * et offset entre -1 et -10 pour offset négatif
     */
    @Test
    public void IntsRef_isValid_negativeOffset_throwsIllegalStateException() {
        try {
            Faker faker = new Faker();
            new IntsRef(new int[]{}, faker.number().numberBetween(-10, -1), 0);
        } catch (IllegalStateException ex) {
            assertTrue(ex.getMessage().startsWith("offset is negative:"));
        }
    }

    /**
     * Vérifier que isValid() rejette offset > ints.length dans le constructeur
     * en lançant IllegalStateException avec message commençant par "offset out of bounds:"
     * length=0 pour ne pas interférer avec les autres branches
     * ints=[] et offset entre 5 et 10 pour dépasser la longueur du tableau (5 > 0)
     */
    @Test
    public void IntsRef_isValid_offsetOutOfBounds_throwsIllegalStateException() {
        try {
            Faker faker = new Faker();
            new IntsRef(new int[]{}, faker.number().numberBetween(5, 10), 0);
        } catch (IllegalStateException ex) {
            assertTrue(ex.getMessage().startsWith("offset out of bounds:"));
        }
    }

    /**
     * Vérifier que isValid() rejette offset+length > ints.length dans le constructeur
     * en lançant IllegalStateException avec message commençant par "offset+length out of bounds:"
     * ints=[1], offset=1 et length=1 pour que leur somme dépasse la longueur du tableau (1+1=2 > 1)
     */
    @Test
    public void IntsRef_isValid_offsetPlusLengthOutOfBounds_throwsIllegalStateException() {
        try {
            new IntsRef(new int[]{1}, 1, 1);
        } catch (IllegalStateException ex) {
            assertTrue(ex.getMessage().startsWith("offset+length out of bounds:"));
        }
    }

     /**
     * Vérifier que le constructeur IntsRef(int capacity) refuse capacity=0
     * en lançant IllegalArgumentException avec message contenant "Use instance EMPTY instead of capacity 0"
     * capacity=0 pour tester cette condition
     */
    @Test
    public void IntsRef_capacity0_throwsIllegalArgumentException() {
        try {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new IntsRef(0));
            assertTrue(ex.getMessage().contains("Use instance EMPTY instead of capacity 0"));
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("Use instance EMPTY instead of capacity 0"));
        }
    }

}
