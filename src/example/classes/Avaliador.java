package example.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.jtcgen.annotations.TestEquals;
import br.com.jtcgen.annotations.JTCGen;
import br.com.jtcgen.annotations.Test;
import br.com.jtcgen.annotations.TestVoidEquals;
import example.classes.Lance;
import example.classes.Leilao;

@JTCGen
public class Avaliador {

	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private List<Lance> maiores;
	
	@Test("")
	public void avalia(Leilao leilao) {
		
		for(Lance lance: leilao.getLances()) { 
			if(lance.getValor() > maiorDeTodos) maiorDeTodos = lance.getValor();
			if(lance.getValor() < menorDeTodos) menorDeTodos = lance.getValor();
		}
		
		pegaOsMaioresNo(leilao);
	}
	
	@Test("mock('example.classes.Leilao@getLances()').returns(mockList('example.classes.Lance@getValor()', [200.0, 300.0, 400.0, 500.0], 'double'))")
	@TestVoidEquals({"getTresMaiores", "200.0"})
	private void pegaOsMaioresNo(Leilao leilao) {
        maiores = new ArrayList<Lance>(leilao.getLances());
        Collections.sort(maiores, new Comparator<Lance>() {
            public int compare(Lance o1, Lance o2) {
                if(o1.getValor() < o2.getValor()) return 1;
                if(o1.getValor() > o2.getValor()) return -1;
                return 0;
            }
        });
        maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
    }
	
	
    public List<Lance> getTresMaiores() {
        return this.maiores;
    }
	
    @Test("mock('example.classes.Leilao@getLances()').returns(mockList('example.classes.Lance@getValor()', [200.0, 300.0, 400.0], 'double'))")
    @TestEquals("300.0")
	public double obtemValorMedioDosLances(Leilao leilao) {
		double total = (double) leilao.getLances().size();
		double valorTotal = 0.0;
		for(Lance lance: leilao.getLances())  
			valorTotal += lance.getValor();
		return valorTotal / total;
	}
	
	public double getMaiorLance() {
		return maiorDeTodos;
	}
	
	public double getMenorLance() {
		return menorDeTodos;
	}

}
