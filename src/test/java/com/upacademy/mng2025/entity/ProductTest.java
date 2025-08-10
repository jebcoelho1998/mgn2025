package com.upacademy.mng2025.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

  @Test
  void deveCriarProdutoComValoresValidos() {
    Product p = new Product("arroz", 1.5, 23.0, 10.0);
    assertEquals("arroz", p.getId());
    assertEquals(1.5, p.getUnitDiscount());
    assertEquals(23.0, p.getVatPercent());
    assertEquals(10.0, p.getPvp());
  }

  @Test
  void deveLancarSeIdForNuloOuVazio() {
    assertThrows(IllegalArgumentException.class, () -> new Product(null, 1.0, 6.0, 5.0));
    assertThrows(IllegalArgumentException.class, () -> new Product("  ", 1.0, 6.0, 5.0));
  }

  @Test
  void deveAlterarValoresComSetters() {
    Product p = new Product("feijao", 2.0, 6.0, 12.0);
    p.setUnitDiscount(3.0);
    p.setVatPercent(13.0);
    p.setPvp(15.0);
    assertAll(
      () -> assertEquals(3.0, p.getUnitDiscount()),
      () -> assertEquals(13.0, p.getVatPercent()),
      () -> assertEquals(15.0, p.getPvp())
    );
  }
}
