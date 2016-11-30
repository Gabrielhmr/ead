package br.com.ead.service;

import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class GeradorRelatorio {

	private String nomeArquivo;
	private Map<String, Object> parametros;
	private Connection connection;
	private JasperPrint jasperPrint;

	public GeradorRelatorio(String nomeArquivo, Map<String, Object> parametros, Connection connection) {
		this.nomeArquivo = nomeArquivo;
		this.parametros = parametros;
		this.connection = connection;
	}

	public void geraPDFParaOutputStream(OutputStream outputStream) {

		try {
			//jasperPrint = JasperFillManager.fillReport(this.nomeArquivo, this.parametros, this.connection);
			
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
			exporter.exportReport();
			

		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isEmpty() throws JRException {
		jasperPrint = JasperFillManager.fillReport(this.nomeArquivo, this.parametros, this.connection);
		boolean empty = jasperPrint.getPages().get(0).getElements().size() <= 0;
		return empty;
	}

}
