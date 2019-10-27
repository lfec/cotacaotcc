package com.lfec.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.lfec.compiler.EscreverClasse;
import com.lfec.domain.Endereco;
import com.lfec.domain.PracaWrapper;
import com.lfec.dsl.DSLUtils;
import com.lfec.dsl.domain.NegociacaoDSL;
import com.lfec.dsl.domain.PracaDSL;
import com.lfec.sql.controller.Dao;
import com.lfec.sql.domain.AreaGeografica;
import com.lfec.sql.domain.Municipio;
import com.lfec.sql.domain.Negociacao;
import com.lfec.sql.domain.Praca;
import com.lfec.sql.domain.Regiao;
import com.lfec.sql.domain.AreaGeografica.TipoAreaGeografica;
import com.lfec.sql.query.PracaListQuery;

public class CotacaoTest {

	static Dao dao = Dao.getInstance();

	static TipoAreaGeografica[] tipos = { TipoAreaGeografica.CEP, TipoAreaGeografica.MUNICIPIO, TipoAreaGeografica.UF,
			TipoAreaGeografica.REGIAO };

	static EnderecoTeste[] enderecos = { new EnderecoTeste(01021100, 3550308, 35, 1058), // SP
			new EnderecoTeste(07010000, 3518800, 35, 1058), // SP
			new EnderecoTeste(11010000, 3548500, 35, 1058), // SP
			new EnderecoTeste(12010000, 3554102, 35, 1058), // SP
			new EnderecoTeste(14010000, 3543402, 35, 1058), // SP
			new EnderecoTeste(33010000, 3157807, 31, 1058), new EnderecoTeste(25010000, 3301702, 33, 1058),
			new EnderecoTeste(24020000, 3303302, 33, 1058), new EnderecoTeste(27110000, 3300308, 33, 1058),
			new EnderecoTeste(28010000, 3301009, 33, 1058), new EnderecoTeste(29010001, 3205309, 32, 1058),
			new EnderecoTeste(30110000, 3106200, 31, 1058), new EnderecoTeste(31010030, 3106200, 31, 1058),
			new EnderecoTeste(32010000, 3118601, 31, 1058), new EnderecoTeste(40010000, 2927408, 29, 1058),
			new EnderecoTeste(41098010, 2927408, 29, 1058), new EnderecoTeste(42600000, 2919926, 29, 1058),
			new EnderecoTeste(43700000, 2930709, 29, 1058), new EnderecoTeste(44001001, 2910800, 29, 1058),
			new EnderecoTeste(50010000, 2611606, 26, 1058), new EnderecoTeste(51010000, 2611606, 26, 1058),
			new EnderecoTeste(53010005, 2609600, 26, 1058), new EnderecoTeste(54070000, 2607901, 26, 1058),
			new EnderecoTeste(58010000, 2507507, 25, 1058), new EnderecoTeste(59010000, 2408102, 24, 1058),
			new EnderecoTeste(59925000, 2414753, 24, 1058), new EnderecoTeste(59910000, 2403202, 24, 1058),
			new EnderecoTeste(59700000, 2401008, 24, 1058), new EnderecoTeste(50761100, 2611606, 26, 1058), // PE
			new EnderecoTeste(27110100, 3300308, 33, 1058), // RJ
			new EnderecoTeste(33010100, 3157807, 31, 1058), // MG
			new EnderecoTeste(40010100, 2927408, 29, 1058), // BA
			new EnderecoTeste(29010100, 3205309, 32, 1058), // ES
			new EnderecoTeste(49000100, 2800308, 28, 1058), // SE
			new EnderecoTeste(57010100, 2704302, 27, 1058), // AL
			new EnderecoTeste(58010100, 2507507, 25, 1058), // PB
			new EnderecoTeste(59010100, 2408102, 24, 1058) // RN
	};

	@Test
	public void testCotacaoMapa() {

		int totalNegociacoes = 1000;
		int totalPracas = 15;

		List<Negociacao> negociacaoList = TestUtils.createBase(totalNegociacoes, totalPracas,enderecos,tipos);

		Random random = new Random(123l);

		Date d2 = new Date();

		int loop = 1;

		for (int i = 0; i < loop; i++) {
			List<PracaWrapper> pracaRet = new ArrayList<PracaWrapper>();

			EnderecoTeste endOr = enderecos[random.nextInt(enderecos.length)];
			EnderecoTeste endDest = enderecos[random.nextInt(enderecos.length)];

			Endereco endIni = new Endereco();
			TestUtils.toEndereco(endOr, endIni);

			Endereco endFim = new Endereco();
			TestUtils.toEndereco(endDest, endFim);

			for (Negociacao negociacao : negociacaoList) {
				List<Praca> pracaList = negociacao.getPracaList();

				for (Praca praca : pracaList) {
					if (praca.getAreaOrigem().atende(endIni) && praca.getAreaDestino().atende(endFim)) {

						pracaRet.add(new PracaWrapper(negociacao.getId(), praca.getId(), praca.getOrder_index()));

						break;
					}
				}

			}
			System.out.println(pracaRet.size() + " registros encontrados");

			for (PracaWrapper praca : pracaRet) {
				System.out.println(praca.getNegociacaoId() + " - "+praca.getPracaId() + " - " + praca.getOrderIndex());
			}

		}

		Date d3 = new Date();
		long time2 = d3.getTime() - d2.getTime();
		System.out.println(time2 / 1000 + "segundos para execução");

	}
	
	@Test
	public void testQuery() {
		Date d1 = new Date();

		int loop = 10;
		PracaListQuery pq = new PracaListQuery();

		Random random = new Random(123l);

		for (int i = 0; i < loop; i++) {

			EnderecoTeste endOr = enderecos[random.nextInt(enderecos.length)];
			EnderecoTeste endDest = enderecos[random.nextInt(enderecos.length)];

			pq.setCepIni(endOr.getCep());
			pq.setCodMunicipioIni(endOr.getCodigoMun());
			pq.setCodUfIni(endOr.getCodigoUf());
			pq.setCofPaisIni(endOr.getCodigoPais());

			pq.setCepFim(endDest.getCep());
			pq.setCodMunicipioFim(endDest.getCodigoMun());
			pq.setCodUfFim(endDest.getCodigoUf());
			pq.setCofPaisFim(endDest.getCodigoPais());

			List<Object[]> pqueryRet = dao.list(pq);

			System.out.println(pqueryRet.size() + " registros encontrados");
//			for (Object[] praca : pqueryRet) {
//				System.out.println(praca[0] + " - " +praca[1]+" - "+ praca[2]);
//			}
		}

		Date d2 = new Date();
		long time = d2.getTime() - d1.getTime();
		System.out.println(time / 1000 + "segundos para execução");

	}

	@Test
	public void createBase() {

		int totalNegociacoes = 5000;
		int totalPracas = 25;

		Date d1 = new Date();

		List<Negociacao> negociacaoList = TestUtils.createBase(totalNegociacoes, totalPracas,enderecos,tipos);

		dao.persist(negociacaoList);

		Date d2 = new Date();
		long time = d2.getTime() - d1.getTime();
		System.out.println(time / 1000 + "segundos para execução");
//		System.out.println(pracaCount + "praças criadas");
//		System.out.println(emp + "negociações criadas");

	}

	@Test
	public void testWriteDSL() {
		
		int totalNegociacoes = 1000;
		int totalPracas = 15;
		List<Negociacao> negociacaoList = TestUtils.createBase(totalNegociacoes, totalPracas,enderecos,tipos);
		
		try {
			DSLUtils.escreverArquivo("teste1", negociacaoList);
			List<NegociacaoDSL> arquivoDSL = DSLUtils.lerArquivo("teste1");
			
			
			Random random = new Random(123l);

			Date d2 = new Date();

			int loop = 1;

			for (int i = 0; i < loop; i++) {

				EnderecoTeste endOr = enderecos[random.nextInt(enderecos.length)];
				EnderecoTeste endDest = enderecos[random.nextInt(enderecos.length)];

				Endereco endIni = new Endereco();
				TestUtils.toEndereco(endOr, endIni);

				Endereco endFim = new Endereco();
				TestUtils.toEndereco(endDest, endFim);

				
				List<PracaDSL> retBusca = DSLUtils.buscarPracas(endIni, endFim, arquivoDSL);
				
				System.out.println(retBusca.size() + " registros encontrados");

				for (PracaDSL praca : retBusca) {
					System.out.println(praca.getIdNegociacao() + " - "+praca.getIdPraca() + " - " + praca.getOrderIndex());
				}

			}

			Date d3 = new Date();
			long time2 = d3.getTime() - d2.getTime();
			System.out.println(time2 / 1000 + "segundos para execução");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void testCompile() {
//		List<Negociacao> ret = TestUtils.createBase(5000, 25,enderecos,tipos);
//		try {
//			EscreverClasse.escreverClasse("teste1", ret);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}



}
