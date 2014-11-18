package br.com.box.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DataUtil {

	private static final String HORA_PT_BR = "HH:mm";
	private static final String DATA_PT_BR = "dd/MM/yyyy";
	
	private DataUtil() {}

	public static String formataDataPtBr(Date data) {
		return formatarData(DATA_PT_BR, data);
	}
	
	public static String formataHoraPtBr(Date data) {
		return formatarData(HORA_PT_BR, data);
	}
	
	private static String formatarData(String padrao, Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat(padrao);
		return sdf.format(data);
	}
}
