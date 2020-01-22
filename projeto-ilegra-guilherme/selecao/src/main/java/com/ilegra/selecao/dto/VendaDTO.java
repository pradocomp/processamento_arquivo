package com.ilegra.selecao.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.ilegra.selecao.interfaces.Entity;

public class VendaDTO implements Entity {

  private final Long id;
  private final List<ItemDTO> itens;
  private final String nomeVendedor;

  /** Construtor da entidade básica da venda. */
  public VendaDTO(Long id, List<ItemDTO> itens, String nomeVendedor) {
    this.id = id;
    this.nomeVendedor = nomeVendedor;
    this.itens = itens;
  }

  public Long getId() {
    return id;
  }

  public String getNomeVendedor() {
    return nomeVendedor;
  }  

  public List<ItemDTO> getItens() {
    return Collections.unmodifiableList(itens);
  }

  /** Valor total da venda. */
  public BigDecimal getValorTotalCalculado() {
    return itens.stream()
        .map((item) -> item.getPrecoCalculado())
        .reduce((itemA, itemB) -> itemA.add(itemB)).get();
  }
  
}