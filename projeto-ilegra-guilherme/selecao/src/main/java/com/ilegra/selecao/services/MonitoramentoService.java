package com.ilegra.selecao.services;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilegra.selecao.services.constants.Constantes;

@Service
public class MonitoramentoService {
	
	@Autowired
	private GerenciadorArquivoService gerenciadorArquivoService;

	/**
	 * Método de monitoramento de diretório
	 * @throws IOException
	 */
	public void monitorarDiretorio() throws IOException {
		// Obtém o caminho do diretório de entrada a ser monitorado
		final Path pathMonitorado = Paths.get(Constantes.COMPLETE_PATH_IN);

		// Cria o monitoramento e define qual o registro deve ser monitorado
		final WatchService monitoramento = FileSystems.getDefault().newWatchService();
		pathMonitorado.register(monitoramento, ENTRY_CREATE);

		// Inicia o monitoramento constante da criação de arquivo no diretório
		// especificado
		while (true) {
			WatchKey key = null;
			try {
				key = monitoramento.take();
			} catch (InterruptedException e) {
				System.out.println("Erro de aplicação: " + e.getMessage());
			}

			for (WatchEvent<?> evento : key.pollEvents()) {
				WatchEvent.Kind<?> tipoEvento = evento.kind();
				final Path path = pathMonitorado.resolve(evento.context().toString());

				if (tipoEvento.equals(ENTRY_CREATE)) {
					processaArquivo(path);
				}
			}
			if (!key.reset()) {
				break;
			}
		}
	}
	
	/**
	 * Método de processamento de arquivos.
	 * @param pathArquivoEntrada
	 * @return Path do arquivo de saída.
	 */
	public Path processaArquivo(final Path pathArquivoEntrada) {

		final Path arquivoNovo = criaArquivoSaida(pathArquivoEntrada);
		try {
			gerenciadorArquivoService.carregaConteudosNovos(Files.readAllLines(pathArquivoEntrada));
			List<String> relatorio = gerenciadorArquivoService.geraArquivoFinal();
			try (final PrintWriter writer = new PrintWriter(Files.newBufferedWriter(arquivoNovo))) {
				for (String linha : relatorio) {
					writer.println(linha);
				}
			}
			System.out.println(String.format(
				"Processamento realizado com sucesso. Verifique o relatório em: %s ", Constantes.COMPLETE_PATH_OUT));

		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		return arquivoNovo;
	}

	/**
	 * Método que cria o diretório de saída se não existir
	 * @param pathArquivoEntrada
	 * @return
	 */
	private Path criaArquivoSaida(Path pathArquivoEntrada) {
		final File diretorioSaida = new File(Constantes.COMPLETE_PATH_OUT);
		if (!diretorioSaida.exists()) {
			diretorioSaida.mkdir();
		}
		return Paths.get(diretorioSaida.toString().concat(File.separator).concat("arquivo_final.txt"));
	}
	
}
