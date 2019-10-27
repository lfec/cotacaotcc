package com.lfec.compiler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.lfec.sql.domain.AreaGeografica;
import com.lfec.sql.domain.Municipio;
import com.lfec.sql.domain.Negociacao;
import com.lfec.sql.domain.Praca;
import com.lfec.sql.domain.AreaGeografica.TipoAreaGeografica;

public class EscreverClasse {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static Comparator<Praca> comp = new Comparator<Praca>() {

		@Override
		public int compare(Praca o1, Praca o2) {

			int o1i = o1.getOrder_index();
			int o2i = o2.getOrder_index();

			return o1i > o2i ? 1 : o1i < o2i ? -1 : 0;
		}
	};

//	cont =true;
//	if (cont && origem.getCodigoMun().equals(123456) && destino.getCodigoUf().equals(45)) {
//	ret.add(new PracaWrapper(negociacaoId, pracaId, orderIndex));
//		cont=false;
//	}

	
	public static void escreverClasse2(String name, List<Negociacao> negociacaoList) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

		String javaName = name + ".txt";

		StringBuilder mainBuilder = new StringBuilder();
		StringBuilder methodBuilder = new StringBuilder();
		for (Negociacao negociacao : negociacaoList) {

			buildNegociacao(mainBuilder,methodBuilder, negociacao);

		}

		String semiclass = CompiladorJava.readCode("src\\main\\resources\\SemiClass.txt");

		String novaSemi = semiclass.replace("${CLASS_NAME}", name).replace("${MAIN_CODE}", mainBuilder.toString()).replace("${METHOD_CODE}", methodBuilder.toString());

		Path javaPath = CompiladorJava.saveSource(novaSemi, javaName,
				"src\\main\\java\\com\\lfec\\compiler");

//		Path classPath = CompiladorJava.compileSource(javaPath, className);
		
	}
	
	public static void escreverClasse(String name, List<Negociacao> negociacaoList) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

		String javaName = name + ".java";
		String className = name + ".class";

		StringBuilder mainBuilder = new StringBuilder();
		StringBuilder methodBuilder = new StringBuilder();
		for (Negociacao negociacao : negociacaoList) {

			buildNegociacao(mainBuilder,methodBuilder, negociacao);

		}

		String semiclass = CompiladorJava.readCode("src\\main\\resources\\SemiClass.txt");

		String novaSemi = semiclass.replace("${CLASS_NAME}", name).replace("${MAIN_CODE}", mainBuilder.toString()).replace("${METHOD_CODE}", methodBuilder.toString());

		Path javaPath = CompiladorJava.saveSource(novaSemi, javaName,
				"src\\main\\java\\com\\lfec\\compiler");

//		Path classPath = CompiladorJava.compileSource(javaPath, className);
		
	}
	

	private static void buildNegociacao(StringBuilder mainBuilder, StringBuilder methodBuilder, Negociacao negociacao) {
		String negociacaoId = negociacao.getId();
		mainBuilder.append("method"+negociacao.getId()+"(origem, destino, ret);");
		mainBuilder.append(LINE_SEPARATOR);
		
		methodBuilder.append("private static void method"+negociacao.getId()+"(Endereco origem, Endereco destino, List<PracaWrapper> ret){");

		List<Praca> sortedPracaList = sort(negociacao.getPracaList());

		for (int i = 0; i < sortedPracaList.size(); i++) {

			Praca praca = sortedPracaList.get(i);

			String pracaId = praca.getId();
			int orderIndex = praca.getOrder_index();
			methodBuilder.append(" if ( ");

			AreaGeografica pracaOrigem = praca.getAreaOrigem();
			TipoAreaGeografica tipoOrigem = pracaOrigem.getTipoAreaGeografica();
			AreaGeografica pracaDestino = praca.getAreaDestino();
			TipoAreaGeografica tipoDestino = pracaDestino.getTipoAreaGeografica();

			switch (tipoOrigem) {
			case CEP:
				methodBuilder.append("origem.getCep()>=" + pracaOrigem.getCepInicio());
				methodBuilder.append(" && ");
				methodBuilder.append("origem.getCep()<=" + pracaOrigem.getCepTermino());

				break;
			case MUNICIPIO:
				methodBuilder.append("origem.getCodigoMun().equals(" + pracaOrigem.getCodigoMun() + ")");
				break;
			case UF:
				methodBuilder.append("origem.getCodigoUf().equals(" + pracaOrigem.getCodigoUf() + ")");
				break;
			case REGIAO:

				List<Municipio> munList = pracaOrigem.getRegiao().getMunicipioRegiaoList();

				methodBuilder.append(" contains( origem.getCodigoMun()");
				methodBuilder.append(", new int[]{ ");

				for (int j = 0; j < munList.size(); j++) {
					Municipio m = munList.get(j);
					methodBuilder.append(m.getCodigo());

					if (j < munList.size() - 1) {
						methodBuilder.append(",");
					}
				}

				methodBuilder.append("})");

				break;
			case PAIS:
				methodBuilder.append("origem.getCodigoPais().equals(" + pracaOrigem.getCodigoPais() + ")");
				break;

			}

			methodBuilder.append(" && ");

			switch (tipoDestino) {
			case CEP:
				methodBuilder.append("destino.getCep()>=" + pracaDestino.getCepInicio());
				methodBuilder.append(" && ");
				methodBuilder.append("destino.getCep()<=" + pracaDestino.getCepTermino());

				break;
			case MUNICIPIO:
				methodBuilder.append("destino.getCodigoMun().equals(" + pracaDestino.getCodigoMun() + ")");
				break;
			case UF:
				methodBuilder.append("destino.getCodigoUf().equals(" + pracaDestino.getCodigoUf() + ")");
				break;
			case REGIAO:

				List<Municipio> munList = pracaDestino.getRegiao().getMunicipioRegiaoList();

				methodBuilder.append(" contains( destino.getCodigoMun()");
				methodBuilder.append(", new int[]{ ");

				for (int j = 0; j < munList.size(); j++) {
					Municipio m = munList.get(j);
					methodBuilder.append(m.getCodigo());

					if (j < munList.size() - 1) {
						methodBuilder.append(",");
					}
				}

				methodBuilder.append("})");

				break;
			case PAIS:
				methodBuilder.append("destino.getCodigoPais().equals(" + pracaDestino.getCodigoPais() + ")");
				break;

			}

			methodBuilder.append(" ) { ");

			methodBuilder.append(" ret.add(new PracaWrapper(\"" + negociacaoId + "\",\"" + pracaId + "\"," + orderIndex + ")); ");
			methodBuilder.append(" }");

			if (i < sortedPracaList.size() - 1) {
				methodBuilder.append("else ");
			}
			
			methodBuilder.append(LINE_SEPARATOR);
		}
		methodBuilder.append("}");
	}

	private static boolean contains(final int v, final int[] array) {

		boolean result = false;

		for (int i : array) {
			if (i == v) {
				result = true;
				break;
			}
		}

		return result;
	}

	private static List<Praca> sort(List<Praca> pracaList) {
		Collections.sort(pracaList, comp);
		return pracaList;
	}

}
