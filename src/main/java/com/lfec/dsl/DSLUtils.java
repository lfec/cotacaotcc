package com.lfec.dsl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.lfec.domain.Endereco;
import com.lfec.dsl.domain.AreaDSL;
import com.lfec.dsl.domain.NegociacaoDSL;
import com.lfec.dsl.domain.PracaDSL;
import com.lfec.dsl.domain.AreaDSL.TipoAreaGeograficaDSL;
import com.lfec.sql.domain.AreaGeografica;
import com.lfec.sql.domain.Municipio;
import com.lfec.sql.domain.Negociacao;
import com.lfec.sql.domain.Praca;
import com.lfec.sql.domain.AreaGeografica.TipoAreaGeografica;

public class DSLUtils {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String FIELD_SEPARATOR = ":";
	private static final String NEGOCIACAO_TOKEN = "N";
	private static final String SUB_ITEM_TOKEN = "-";

	private static Comparator<Praca> comp = new Comparator<Praca>() {

		@Override
		public int compare(Praca o1, Praca o2) {

			int o1i = o1.getOrder_index();
			int o2i = o2.getOrder_index();

			return o1i > o2i ? 1 : o1i < o2i ? -1 : 0;
		}
	};

	public static List<NegociacaoDSL> lerArquivo(String name) throws IOException {
		List<NegociacaoDSL> retList = new ArrayList<NegociacaoDSL>();

		InputStream stream = new FileInputStream("src\\main\\resources\\" + name);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		String lineRead = reader.readLine();

		NegociacaoDSL lastNegociacao = null;
		List<PracaDSL> lastPracaList = null;

		while (lineRead != null) {

			String[] split = lineRead.split(FIELD_SEPARATOR);

			if (split[0].equalsIgnoreCase(NEGOCIACAO_TOKEN)) {
				lastNegociacao = new NegociacaoDSL(split[1]);
				lastPracaList = new ArrayList<PracaDSL>();
				lastNegociacao.setPracaList(lastPracaList);
				retList.add(lastNegociacao);
			} else {

				String idPraca = split[0];
				String order = split[1];
				String origemTipo = split[2];
				String origemValor = split[3];
				String destinoTipo = split[4];
				String destinoValor = split[5];

				PracaDSL p = new PracaDSL(idPraca,lastNegociacao.getIdnegociacao(), order);
				lastPracaList.add(p);

				AreaDSL origem = new AreaDSL();
				AreaDSL destino = new AreaDSL();

				p.setOrigem(origem);
				p.setDestino(destino);

				TipoAreaGeograficaDSL tipoOrigemEnum = TipoAreaGeograficaDSL.fromValue(origemTipo);
				TipoAreaGeograficaDSL tipoDestinoEnum = TipoAreaGeograficaDSL.fromValue(destinoTipo);

				origem.setTipoArea(tipoOrigemEnum);
				destino.setTipoArea(tipoDestinoEnum);
				
				readToAreaDSL(origemValor, origem, tipoOrigemEnum);
				
				readToAreaDSL(destinoValor, destino, tipoDestinoEnum);

			}

			lineRead = reader.readLine();
		}
		reader.close();
		return retList;
	}

	private static void readToAreaDSL(String origemValor, AreaDSL origem, TipoAreaGeograficaDSL tipoOrigemEnum) {
		switch (tipoOrigemEnum) {
		case CEP:
			String[] ceps = origemValor.split(SUB_ITEM_TOKEN);
			origem.setCepInicio(Integer.parseInt(ceps[0]));
			origem.setCepTermino(Integer.parseInt(ceps[1]));
			break;
		case MUNICIPIO:
			origem.setCodMunicipio(Integer.parseInt(origemValor));
			break;
		case UF:
			origem.setCodUf(Integer.parseInt(origemValor));
			break;
		case PAIS:
			origem.setCodPais(Integer.parseInt(origemValor));
			break;
		case REGIAO:
			String[] municipios = origemValor.split(SUB_ITEM_TOKEN);

			List<Integer> integerList = new ArrayList<Integer>();

			for (String codMun : municipios) {
				integerList.add(Integer.parseInt(codMun));
			}
			origem.setRegiaoList(integerList);
			break;

		}
	}

	public static void escreverArquivo(String name, List<Negociacao> negociacaoList) throws IOException {

		FileWriter fw = new FileWriter(new File("src\\main\\resources\\" + name));

		for (Negociacao negociacao : negociacaoList) {

			List<Praca> pracalist = sort(negociacao.getPracaList());

			fw.write(NEGOCIACAO_TOKEN);
			fw.write(FIELD_SEPARATOR);
			fw.write(negociacao.getId());
			fw.write(LINE_SEPARATOR);
			for (Praca praca : pracalist) {

				AreaGeografica pracaOrigem = praca.getAreaOrigem();
				TipoAreaGeografica tipoOrigem = pracaOrigem.getTipoAreaGeografica();
				AreaGeografica pracaDestino = praca.getAreaDestino();
				TipoAreaGeografica tipoDestino = pracaDestino.getTipoAreaGeografica();

				fw.write(praca.getId());
				fw.write(FIELD_SEPARATOR);
				fw.write(praca.getOrder_index() + "");
				fw.write(FIELD_SEPARATOR);

				// Origem
				switch (tipoOrigem) {
				case CEP:
					fw.write("C");
					fw.write(FIELD_SEPARATOR);
					fw.write(pracaOrigem.getCepInicio() + "");
					fw.write(SUB_ITEM_TOKEN);
					fw.write(pracaOrigem.getCepTermino() + "");
					fw.write(FIELD_SEPARATOR);
					break;
				case MUNICIPIO:
					fw.write("M");
					fw.write(FIELD_SEPARATOR);
					fw.write(pracaOrigem.getCodigoMun() + "");
					fw.write(FIELD_SEPARATOR);
					break;
				case UF:
					fw.write("U");
					fw.write(FIELD_SEPARATOR);
					fw.write(pracaOrigem.getCodigoUf() + "");
					fw.write(FIELD_SEPARATOR);
					break;
				case PAIS:
					fw.write("P");
					fw.write(FIELD_SEPARATOR);
					fw.write(pracaOrigem.getCodigoPais() + "");
					fw.write(FIELD_SEPARATOR);
					break;
				case REGIAO:
					fw.write("R");
					fw.write(FIELD_SEPARATOR);

					List<Municipio> munList = pracaOrigem.getRegiao().getMunicipioRegiaoList();

					for (int j = 0; j < munList.size(); j++) {
						Municipio m = munList.get(j);
						fw.write(m.getCodigo() + "");

						if (j < munList.size() - 1) {
							fw.write(SUB_ITEM_TOKEN);
						}
					}

					fw.write(FIELD_SEPARATOR);

					break;

				}

				// Destino
				switch (tipoDestino) {
				case CEP:
					fw.write("C");
					fw.write(FIELD_SEPARATOR);
					fw.write(pracaDestino.getCepInicio() + "");
					fw.write(SUB_ITEM_TOKEN);
					fw.write(pracaDestino.getCepTermino() + "");
					fw.write(FIELD_SEPARATOR);
					break;
				case MUNICIPIO:
					fw.write("M");
					fw.write(FIELD_SEPARATOR);
					fw.write(pracaDestino.getCodigoMun() + "");
					fw.write(FIELD_SEPARATOR);
					break;
				case UF:
					fw.write("U");
					fw.write(FIELD_SEPARATOR);
					fw.write(pracaDestino.getCodigoUf() + "");
					fw.write(FIELD_SEPARATOR);
					break;
				case PAIS:
					fw.write("P");
					fw.write(FIELD_SEPARATOR);
					fw.write(pracaDestino.getCodigoPais() + "");
					fw.write(FIELD_SEPARATOR);
					break;
				case REGIAO:
					fw.write("R");
					fw.write(FIELD_SEPARATOR);

					List<Municipio> munList = pracaDestino.getRegiao().getMunicipioRegiaoList();

					for (int j = 0; j < munList.size(); j++) {
						Municipio m = munList.get(j);
						fw.write(m.getCodigo() + "");

						if (j < munList.size() - 1) {
							fw.write(SUB_ITEM_TOKEN);
						}
					}

					fw.write(FIELD_SEPARATOR);

					break;

				}

				fw.write(LINE_SEPARATOR);
			}

			fw.flush();

		}

		fw.close();

	}

	private static List<Praca> sort(List<Praca> pracaList) {
		Collections.sort(pracaList, comp);
		return pracaList;
	}
	
	public static  List<PracaDSL> buscarPracas (Endereco origem, Endereco destino, List<NegociacaoDSL> negociacaoList){
		List<PracaDSL> ret = new ArrayList<PracaDSL>();
		
		for (NegociacaoDSL nDSL : negociacaoList) {
			
			List<PracaDSL> pDSLList = nDSL.getPracaList();
			
			for (PracaDSL pracaDSL : pDSLList) {
				
				if (pracaDSL.atende(origem,destino)) {
					ret.add(pracaDSL);
					break;
				}
				
			}
			
		}
		
		
		return ret;
	}

}
