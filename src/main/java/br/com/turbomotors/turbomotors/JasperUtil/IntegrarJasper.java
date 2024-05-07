package br.com.turbomotors.turbomotors.JasperUtil;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Component
public class IntegrarJasper implements Serializable{
    // OBter Binario do jasper
    
    @Autowired
    private DataSource dataSource;
    
    public byte[] gerarRelatorio(String parametro1, String relatorio, ServletContext ServletContext) throws Exception{
        String caminhoJasper = ServletContext.getRealPath("relatorios") + File.separator + relatorio + ".jasper";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("StatusParam", parametro1);
        
        Connection minhaConexao = dataSource.getConnection();
        
        JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, parametros,  minhaConexao);
        return JasperExportManager.exportReportToPdf(impressoraJasper);
    }
}