package com.ilegra.selecao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ilegra.selecao.services.MonitoramentoService;

@SpringBootApplication
public class SelecaoApplication implements CommandLineRunner {

	@Autowired
	private MonitoramentoService monitoramentoService;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SelecaoApplication.class);
        app.run(args);
	}
	
	/**
	 * Inicia o monitoramento do diretório
	 * @param args
	 * @throws Exception
	 */
	@Override
	public void run(String... args) throws Exception {
		monitoramentoService.monitorarDiretorio();
    }

}
