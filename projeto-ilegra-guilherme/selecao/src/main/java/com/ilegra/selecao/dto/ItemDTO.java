package com.ilegra.selecao.dto;

import java.math.BigDecimal;

public class ItemDTO {

  private final Long id;
  private final Long quantidade;
  private final BigDecimal precoUnitario;

  /** Construtor da entidade básica de item de venda. */
  public ItemDTO(Long id, Long quantidade, BigDecimal precoUnitario) {
    this.id = id;
    this.quantidade = quantidade;
    this.precoUnitario = precoUnitario;
  }
  
  public Long getId() {
    return id;
  }

  public Long getQuantidade() {
    return quantidade;
  }

  public BigDecimal getPrecoUnitario() {
    return precoUnitario;
  }

  public BigDecimal getPrecoCalculado() {
    return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
  }  
}