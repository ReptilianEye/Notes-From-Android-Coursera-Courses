package com.example.testing

import org.junit.Assert.*
import org.junit.Test

class ProductTest {

    @Test
    fun `validate discount when amount less than 5`() {
        val product = Product("Spaghetti", 20.00, 3)
        product.applyDiscount(20)
        assertEquals(16.00, product.price, 0.001)
    }

    @Test
    fun `validate no discount when amount more than 5`() {
        val product = Product(
            "Steak", 30.00, 8
        )
        product.applyDiscount(20)
        assertEquals(30.00, product.price, 0.001)
    }

    @Test
    fun `validate not discount when product out of stock`() {
        val product = Product(
            "Lasagne", 10.00, 0
        )
        product.applyDiscount(20)
        assertEquals(10.00, product.price, .001)
    }
}