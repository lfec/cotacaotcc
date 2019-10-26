package com.lfec.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import com.lfec.controller.Dao;
import com.lfec.controller.QueryReturn;
import com.lfec.domain.AreaGeografica;
import com.lfec.domain.Municipio;
import com.lfec.domain.Negociacao;
import com.lfec.domain.Praca;
import com.lfec.domain.Regiao;
import com.lfec.domain.AreaGeografica.TipoAreaGeografica;
import com.lfec.domain.End;
import com.lfec.query.AllPracaQuery;
import com.lfec.query.PracaQuery;

public class CotacaoTest {
	static Dao dao = Dao.getInstance();

	static TipoAreaGeografica[] tipos = { TipoAreaGeografica.CEP, TipoAreaGeografica.MUNICIPIO, TipoAreaGeografica.UF,
			TipoAreaGeografica.REGIAO };

	static Endereco[] enderecos = { new Endereco(01021100, 3550308, 35, 1058), // SP
			new Endereco(07010000, 3518800, 35, 1058), // SP
			new Endereco(11010000, 3548500, 35, 1058), // SP
			new Endereco(12010000, 3554102, 35, 1058), // SP
			new Endereco(14010000, 3543402, 35, 1058), // SP
			new Endereco(33010000, 3157807, 31, 1058), new Endereco(25010000, 3301702, 33, 1058),
			new Endereco(24020000, 3303302, 33, 1058), new Endereco(27110000, 3300308, 33, 1058),
			new Endereco(28010000, 3301009, 33, 1058), new Endereco(29010001, 3205309, 32, 1058),
			new Endereco(30110000, 3106200, 31, 1058), new Endereco(31010030, 3106200, 31, 1058),
			new Endereco(32010000, 3118601, 31, 1058), new Endereco(40010000, 2927408, 29, 1058),
			new Endereco(41098010, 2927408, 29, 1058), new Endereco(42600000, 2919926, 29, 1058),
			new Endereco(43700000, 2930709, 29, 1058), new Endereco(44001001, 2910800, 29, 1058),
			new Endereco(50010000, 2611606, 26, 1058), new Endereco(51010000, 2611606, 26, 1058),
			new Endereco(53010005, 2609600, 26, 1058), new Endereco(54070000, 2607901, 26, 1058),
			new Endereco(58010000, 2507507, 25, 1058), new Endereco(59010000, 2408102, 24, 1058),
			new Endereco(59925000, 2414753, 24, 1058), new Endereco(59910000, 2403202, 24, 1058),
			new Endereco(59700000, 2401008, 24, 1058), new Endereco(50761100, 2611606, 26, 1058), // PE
			new Endereco(27110100, 3300308, 33, 1058), // RJ
			new Endereco(33010100, 3157807, 31, 1058), // MG
			new Endereco(40010100, 2927408, 29, 1058), // BA
			new Endereco(29010100, 3205309, 32, 1058), // ES
			new Endereco(49000100, 2800308, 28, 1058), // SE
			new Endereco(57010100, 2704302, 27, 1058), // AL
			new Endereco(58010100, 2507507, 25, 1058), // PB
			new Endereco(59010100, 2408102, 24, 1058) // RN
	};

	private static Praca createPraca(Endereco endOr, TipoAreaGeografica tipoOr, Endereco endFim,
			TipoAreaGeografica tipoFim) {

		AreaGeografica inicio = createAreaGeografica(endOr, tipoOr);
		AreaGeografica Fim = createAreaGeografica(endFim, tipoFim);

		Praca p = new Praca();
		p.setAreaOrigem(inicio);
		p.setAreaDestino(Fim);

		return p;
	}

	private static AreaGeografica createAreaGeografica(Endereco endOr, TipoAreaGeografica tipoOr) {

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

	public static Regiao getCreateRegiao(Endereco end) {

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

			r = dao.persist(r);

			end.setRegiao(r);
		}

		return end.getRegiao();

	}
	
	@Test
	public void testCotacaoMapa() {
		
		Date d1 = new Date();
//		AllPracaQuery pq = new AllPracaQuery();
//		QueryReturn<Praca> pqueryRet = dao.query(pq);
		
		List<Praca> retList = new ArrayList<Praca>();
		
		int emp = 1;
		int pracaCount = 0;
		int totalEmpresas = 50000;
		int totalPracas = 50;

		Random random = new Random(123l);

		for (int i = 0; i < totalEmpresas; i++) {

			Negociacao n = new Negociacao();
			n.setEmpresaNome("Empresa " + emp);
			n.setNome("negociacao " + emp);

			List<Praca> pracaList = new ArrayList<Praca>();
			n.setPracaList(pracaList);

			for (int j = 0; j < totalPracas; j++) {

				Endereco endOr = enderecos[random.nextInt(enderecos.length)];
				Endereco endDest = enderecos[random.nextInt(enderecos.length)];
				TipoAreaGeografica tipoOr = tipos[random.nextInt(tipos.length)];
				TipoAreaGeografica tipoDest = tipos[random.nextInt(tipos.length)];

				Praca p = createPraca(endOr, tipoOr, endDest, tipoDest);
				p.setNegociacao(n);
				pracaList.add(p);
				retList.add(p);
				pracaCount++;

			}
//			dao.persist(n);
			emp++;

		}
		
		List<Praca> pracaList = retList;
		
		Date d2 = new Date();
		long time = d2.getTime() - d1.getTime();
		System.out.println(time / 1000 + "segundos para retornar todos os dados do banco");
		System.out.println(pracaList.size() + " registros no total");
		
		
		
		int loop = 10; 
		
		random = new Random(123l);
		
		
		for (int i = 0; i <loop; i++) {
			
			Map<String, Praca> pracaMapa = new HashMap<String, Praca>();
			
			Endereco endOr = enderecos[random.nextInt(enderecos.length)];
			Endereco endDest = enderecos[random.nextInt(enderecos.length)];
			
			
			End endIni = new End(); 
			endIni.setCep(endOr.getCep());
			endIni.setCodigoMun(endOr.getCodigoMun());
			endIni.setCodigoUf(endOr.getCodigoUf());
			endIni.setCodigoPais(endOr.getCodigoPais());
			
			End endFim = new End();
			endFim.setCep(endDest.getCep());
			endFim.setCodigoMun(endDest.getCodigoMun());
			endFim.setCodigoUf(endDest.getCodigoUf());
			endFim.setCodigoPais(endDest.getCodigoPais());
			
			for (Praca praca : pracaList) {
				if(praca.getAreaOrigem().atende(endIni) && praca.getAreaDestino().atende(endFim)) {
					String idnego = praca.getNegociacao().getId();
					if (pracaMapa.containsKey(idnego)) {
						if (pracaMapa.get(idnego).getOrder_index()>praca.getOrder_index()) {
							pracaMapa.put(idnego, praca);
						};
					}else {
						pracaMapa.put(idnego, praca);
					}
				}
			}
			
			Collection<Praca> values = pracaMapa.values();
//			System.out.println(values.size() + " registros encontrados");
			
//			for (Praca praca : values) {
//				System.out.println(praca.getNegociacao().getId() + " - " + praca.getOrder_index());
//			}

		}

		Date d3 = new Date();
		long time2 = d3.getTime() - d2.getTime();
		System.out.println(time2 / 1000 + "segundos para execução");
		
	}

	@Test
	public void testQuery() {
		Date d1 = new Date();

		int loop = 10; 
		PracaQuery pq = new PracaQuery();
		
		Random random = new Random(123l);
		
		
		for (int i = 0; i <loop; i++) {
			
			Endereco endOr = enderecos[random.nextInt(enderecos.length)];
			Endereco endDest = enderecos[random.nextInt(enderecos.length)];
			
			pq.setCepIni(endOr.getCep());
			pq.setCodMunicipioIni(endOr.getCodigoMun());
			pq.setCodUfIni(endOr.getCodigoUf());
			pq.setCofPaisIni(endOr.getCodigoPais());
			
			pq.setCepFim(endDest.getCep());
			pq.setCodMunicipioFim(endDest.getCodigoMun());
			pq.setCodUfFim(endDest.getCodigoUf());
			pq.setCofPaisFim(endDest.getCodigoPais());

			QueryReturn<Praca> pqueryRet = dao.query(pq);
//			System.out.println(pqueryRet.getListaRetorno().size() + " registros encontrados");
			
//			for (Praca praca : pqueryRet.getListaRetorno()) {
//				System.out.println(praca.getNegociacao().getId() + " - " + praca.getOrder_index());
//			}
		}

		Date d2 = new Date();
		long time = d2.getTime() - d1.getTime();
		System.out.println(time / 1000 + "segundos para execução");

	}

	@Test
	public void createBase() {

		int emp = 1;
		int pracaCount = 0;

		Date d1 = new Date();

		int totalEmpresas = 50000;
		int totalPracas = 50;

		Random random = new Random(123l);

		for (int i = 0; i < totalEmpresas; i++) {

			Negociacao n = new Negociacao();
			n.setEmpresaNome("Empresa " + emp);
			n.setNome("negociacao " + emp);

			List<Praca> pracaList = new ArrayList<Praca>();
			n.setPracaList(pracaList);

			for (int j = 0; j < totalPracas; j++) {

				Endereco endOr = enderecos[random.nextInt(enderecos.length)];
				Endereco endDest = enderecos[random.nextInt(enderecos.length)];
				TipoAreaGeografica tipoOr = tipos[random.nextInt(tipos.length)];
				TipoAreaGeografica tipoDest = tipos[random.nextInt(tipos.length)];

				Praca p = createPraca(endOr, tipoOr, endDest, tipoDest);
				p.setNegociacao(n);
				pracaList.add(p);
				pracaCount++;

			}
			dao.persist(n);
			emp++;

		}

		Date d2 = new Date();
		long time = d2.getTime() - d1.getTime();
		System.out.println(time / 1000 + "segundos para execução");
		System.out.println(pracaCount + "praças criadas");
		System.out.println(emp + "negociações criadas");

	}

}
