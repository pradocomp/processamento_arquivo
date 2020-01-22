package com.ilegra.selecao.enums;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ilegra.selecao.dto.ClienteDTO;
import com.ilegra.selecao.dto.VendaDTO;
import com.ilegra.selecao.dto.ItemDTO;
import com.ilegra.selecao.dto.VendedorDTO;
import com.ilegra.selecao.interfaces.Entity;
import com.ilegra.selecao.services.constants.Constantes;

public enum EntityEnum {

  VENDEDOR("001") {
    @Override
    public VendedorDTO criaRegistro(final String linha) {
      final String[] registro = linha.split(Constantes.DELIMITER);
      return new VendedorDTO(registro[1], registro[2], new BigDecimal(registro[3]));
    }
  },
  CLIENTE("002") {
    @Override
    public ClienteDTO criaRegistro(final String linha) {
      final String[] registro = linha.split(Constantes.DELIMITER);
      return new ClienteDTO(registro[1], registro[2], registro[3]);
    }
  },
  VENDA("003") {
    @Override
    public VendaDTO criaRegistro(final String linha) {
      final String[] registro = linha.split(Constantes.DELIMITER);
      return new VendaDTO(new Long(registro[1]), criaItens(registro[2]), registro[3]);
    }
  };

  private final String codigo;
  public abstract Entity criaRegistro(final String linha);

  EntityEnum(final String codigo) {
    this.codigo = codigo;
  }

  public String getCodigo() {
    return this.codigo;
  }

  /**
   * Método que obtém os itens separadamente
   * @param grupoItens
   * @return
   */
  protected List<ItemDTO> criaItens(String grupoItens) {
    String[] listaItens = grupoItens.replaceAll("[\\[\\]]", "").split(",");
    
    List<String[]> itens = Arrays.stream(listaItens)
        .map(item -> item.split("-")).collect(Collectors.toList());
  
    return itens.stream()
        .map(item -> new ItemDTO(Long.valueOf(item[0]), 
            Long.valueOf(item[1]), new BigDecimal(item[2])))
        .collect(Collectors.toList());
  }

  /**
   * Método que obtem o enum com o código inicial de cada registro
   * @param codigo
   * @return
   */
  public static EntityEnum getTabela(final String codigo) {
    return Arrays.stream(EntityEnum.values())
      .filter(tabela -> tabela.getCodigo().equals(codigo))
      .findFirst()
      .orElseThrow(() -> new InvalidParameterException("Tipo de Registro Inválido"));
  }
}