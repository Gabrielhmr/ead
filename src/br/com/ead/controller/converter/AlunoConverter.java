package br.com.ead.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.ead.dao.AlunoDao;
import br.com.ead.model.Aluno;


@FacesConverter(value="alunoConverter",forClass = Aluno.class)
public class AlunoConverter implements Converter{
	
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {
    	System.out.println("------------get as obj------------------meu value id: " + value);
//		if (value == null)
//			return null;
//		System.out.println("------------------------------meu value id: " + value);
		Long id;
		//= new Long(value)

        try {
            id = Long.parseLong(value);
        } catch (NumberFormatException exception) {
            throw new ConverterException();
        }
		
		

		AlunoDao dao = ctx.getApplication().evaluateExpressionGet(ctx,"#{alunoDao}", AlunoDao.class);

		Aluno aluno = dao.load(id);
		return aluno;
		

    }
 
    @Override
    public String getAsString(FacesContext ctx, UIComponent component,
			Object value) {
    	System.out.println("---------get as string---------------meu value id: " + value);
    	
    	if (value == null) {
			return "";
		}
        
        Aluno aluno = (Aluno) value;
        
        if(aluno.getNome() == null)
        	return "";
        
        return aluno.getId().toString();
    }

}
