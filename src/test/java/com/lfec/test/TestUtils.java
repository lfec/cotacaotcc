package com.lfec.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.lfec.domain.Endereco;
import com.lfec.sql.domain.AreaGeografica;
import com.lfec.sql.domain.Municipio;
import com.lfec.sql.domain.Negociacao;
import com.lfec.sql.domain.Praca;
import com.lfec.sql.domain.Regiao;
import com.lfec.sql.domain.AreaGeografica.TipoAreaGeografica;

public class TestUtils {

	
	public static Regiao getCreateRegiao(EnderecoTeste end) {

		if (end.getRegiao() == null) {

			Regiao r = new Regiao();

			Integer codigoMun = end.getCodigoMun();
			r.setDescricao("região para " + codigoMun);
			r.setNome("região para " + codigoMun);

			List<Municipio> municipiolist = new ArrayList<Municipio>();
			r.setMunicipioRegiaoList(municipiolist);

			for (int i = codigoMun - 10; i < codigoMun + 10; i++) {
				Municipio m = new Municipio(new Integer(i), "municipio " + i);
				municipiolist.add(m);
			}

//			r = dao.persist(r);

			end.setRegiao(r);
		}

		return end.getRegiao();

	}
	
	public static Praca createPraca(EnderecoTeste endOr, TipoAreaGeografica tipoOr, EnderecoTeste endFim,
			TipoAreaGeografica tipoFim) {

		AreaGeografica inicio = createAreaGeografica(endOr, tipoOr);
		AreaGeografica Fim = createAreaGeografica(endFim, tipoFim);

		Praca p = new Praca();
		p.setAreaOrigem(inicio);
		p.setAreaDestino(Fim);

		return p;
	}
	
	public static AreaGeografica createAreaGeografica(EnderecoTeste endOr, TipoAreaGeografica tipoOr) {

		AreaGeografica area = new AreaGeografica();
		area.setTipoAreaGeografica(tipoOr);

		switch (tipoOr) {
		case CEP:
			area.setCepInicio(endOr.getCep() - 100);
			area.setCepTermino(endOr.getCep() + 100);
			break;
		case MUNICIPIO:
			area.setCodigoMun(endOr.getCodigoMun());
			break;
		case UF:
			area.setCodigoUf(endOr.getCodigoUf());

			break;
		case REGIAO:
			area.setRegiao(getCreateRegiao(endOr));

			break;

		case PAIS:
//inexistente			
			break;

		}

		return area;
	}
	
	public static  void toEndereco(EnderecoTeste endTest, Endereco end) {
		end.setCep(endTest.getCep());
		end.setCodigoMun(endTest.getCodigoMun());
		end.setCodigoUf(endTest.getCodigoUf());
		end.setCodigoPais(endTest.getCodigoPais());
	}
	


	public static  List<Negociacao> createBase(int totalNegociacoes, int totalPracas, EnderecoTeste[] enderecos, TipoAreaGeografica[] tipos) {

		int negociacaoIdCount = 1;
		int pracaIdCount = 1;
		int regiaoIdCount = 1;

		List<Negociacao> retNegociacaoList = new ArrayList<Negociacao>();

		Random random = new Random(123l);

		for (int i = 0; i < totalNegociacoes; i++) {

			Negociacao n = new Negociacao();
			n.setEmpresaNome("Empresa " + i);
			n.setNome("negociacao " + i);
			n.setId(negociacaoIdCount + "");

			List<Praca> pracaList = new ArrayList<Praca>();
			n.setPracaList(pracaList);

			int indexPraca = 0;
			for (int j = 0; j < totalPracas; j++) {

				EnderecoTeste endOr = enderecos[random.nextInt(enderecos.length)];
				EnderecoTeste endDest = enderecos[random.nextInt(enderecos.length)];
				TipoAreaGeografica tipoOr = tipos[random.nextInt(tipos.length)];
				TipoAreaGeografica tipoDest = tipos[random.nextInt(tipos.length)];

				Praca p = createPraca(endOr, tipoOr, endDest, tipoDest);
				p.setId(pracaIdCount + "");
				p.setNegociacao(n);
				p.setOrder_index(indexPraca);
				pracaList.add(p);

				indexPraca++;
				pracaIdCount++;

			}
			retNegociacaoList.add(n);
			negociacaoIdCount++;
		}

		return retNegociacaoList;

	}
}
