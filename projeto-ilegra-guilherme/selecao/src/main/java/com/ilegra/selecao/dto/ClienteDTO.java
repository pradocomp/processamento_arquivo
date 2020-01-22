package com.ilegra.selecao.dto;

import com.ilegra.selecao.interfaces.Entity;

public class ClienteDTO implements Entity {

  private final String cnpj;
  private final String nome;
  private final String areaNegocio;

  /** Construtor da entidade básica de cliente. */
  public ClienteDTO(String cnpj, String nome, String areaNegocio) {
    this.cnpj = cnpj;
    this.nome = nome;
    this.areaNegocio = areaNegocio;
  }

  public String getCnpj() {
    return cnpj;
  }

  public String getNome() {
    return nome;
  }  

  public String getAreaNegocio() {
    return areaNegocio;
  }
  
}