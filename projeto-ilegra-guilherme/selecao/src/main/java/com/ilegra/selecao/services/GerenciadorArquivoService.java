package com.ilegra.selecao.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ilegra.selecao.dto.ClienteDTO;
import com.ilegra.selecao.dto.VendaDTO;
import com.ilegra.selecao.dto.VendedorDTO;
import com.ilegra.selecao.enums.EntityEnum;
import com.ilegra.selecao.interfaces.Entity;
import com.ilegra.selecao.services.constants.Constantes;

@Component
public class GerenciadorArquivoService {

	private final List<Entity> conteudoArquivo;

	  public GerenciadorArquivoService() {
		  conteudoArquivo = new ArrayList<Entity>();
	  }

	  /**
	   * Método que obtem os conteúdos através do arquivo de entrada
	   * @param conteúdo do arquivo
	   */
	  public void carregaConteudosNovos(final List<String> linhas) {
	    linhas.stream().forEach(linha -> {
	      carregaConteudo(linha);
	    });
	  }

	  /**
	   * Carrega o conteúdo do arquivo linha a linha
	   * @param linha
	   */
	  public void carregaConteudo(final String linha) {
	    final EntityEnum tabelaFactory = EntityEnum.getTabela(linha.substring(Constantes.NUMBER_ZERO, Constantes.NUMBER_THREE));
	    conteudoArquivo.add(tabelaFactory.criaRegistro(linha));
	  }

	  /**
	   * Gera as linhas do arquivo conforme os dados selecionados.
	   * @return linhas do arquivo
	   */
	  public List<String> geraArquivoFinal() {
	    final List<String> arquivoFinal = new ArrayList<>();
	    arquivoFinal.add(String.format("Quantidade de clientes no arquivo de entrada - %d", this.getQtdeClientes()));
	    arquivoFinal.add(String.format("Quantidade de vendedores no arquivo de entrada - %d", this.getQtdeVendedores()));
	    arquivoFinal.add(String.format("ID da venda mais cara - %d", this.getMaiorVenda()));
	    arquivoFinal.add(String.format("O pior vendedor - %s", this.getPiorVendedor()));
	    return arquivoFinal;
	  }

	  public Long getQtdeClientes() {
	    return conteudoArquivo.stream().filter(registro -> registro instanceof ClienteDTO).count();
	  }

	  public Long getQtdeVendedores() {
	    return conteudoArquivo.stream().filter(registro -> registro instanceof VendedorDTO).count();
	  }

	  /**
	   * Obtem o valor do ID da maior venda entre os vendedores
	   * @return
	   */
	  public Long getMaiorVenda() {
	    final List<VendaDTO> vendas = conteudoArquivo.stream()
	        .filter(registro -> registro instanceof VendaDTO)
	        .map(venda -> (VendaDTO) venda).collect(Collectors.toList());

	    return vendas.stream()
	        .max(Comparator.comparingDouble(venda -> venda.getValorTotalCalculado().doubleValue()))
	        .map(venda -> venda.getId()).orElse(0L);
	  }

	  /**
	   * Seleciona o vendedor que teve o pior faturamento dentre todos os outros
	   * @return
	   */
	  public String getPiorVendedor() {
	    final List<VendaDTO> vendas = conteudoArquivo.stream()
	        .filter(registro -> registro instanceof VendaDTO)
	        .map(venda -> (VendaDTO) venda).collect(Collectors.toList());

	    final Map<String, Double> vendasAgrupadas = vendas.stream()
	        .collect(Collectors.groupingBy(VendaDTO::getNomeVendedor,
	        Collectors.summingDouble(venda -> venda.getValorTotalCalculado().doubleValue())));

	    return vendasAgrupadas.entrySet().stream()
	        .min(Map.Entry.comparingByValue())
	        .map(chave -> chave.getKey()).orElse("");
	  }  
}
