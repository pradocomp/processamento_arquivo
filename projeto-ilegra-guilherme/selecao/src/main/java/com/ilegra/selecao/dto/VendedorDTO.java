package com.ilegra.selecao.dto;

import java.math.BigDecimal;

import com.ilegra.selecao.interfaces.Entity;

public class VendedorDTO implements Entity {

  private final String cpf;
  private final String nome;
  private final BigDecimal salario;

  /** Construtor da entidade básica de vendedor. */
  public VendedorDTO(String cpf, String nome, BigDecimal salario) {
    this.cpf = cpf;
    this.nome = nome;
    this.salario = salario;
  }

  public String getCpf() {
    return cpf;
  }

  public String getNome() {
    return nome;
  }  

  public BigDecimal getSalario() {
    return salario;
  }  
}