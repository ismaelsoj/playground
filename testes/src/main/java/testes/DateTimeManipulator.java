package testes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

public class DateTimeManipulator {
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		/*
		 * Essa é a data que vem do banco
		 */
		Date now = sdf.parse("09/02/2019 00:00:00");
		/*
		 * Recupera a diferença em milissegundos entre a data com nosso TimeZone e UTC
		 */
		int offsetToUTC = DateTimeZone.getDefault().getOffset(now.getTime());
		System.out.println("Diferença para o UTC: " + offsetToUTC);
		/*
		 * Cria a nova data em UTC
		 */
		Date utcDate = new Date(now.getTime() - offsetToUTC);
		System.out.println("Horário em UTC: " + sdf.format(utcDate));
		
		String[] availableIDs = TimeZone.getAvailableIDs();
		for (String string : availableIDs) {
			DateTimeZone timeZone;
			try {
				/*
				 * Recupera o TimeZone do front, por exemplo
				 */
				timeZone = DateTimeZone.forID(string);
			} catch (Exception e) {
				continue;
			}
			/*
			 * Calcula a diferença em milissegundos entre esse TimeZone e o UTC
			 */
			int offset = timeZone.getOffset(utcDate.getTime());
			/*
			 * A data a ser levada em consideração nos cálculos de dripping é 
			 * a soma entre o horário UTC e a diferença do TimeZone atual.
			 * 
			 * Ou seja: dateByTimeZone > liberationDate? Se sim, libera o conteúdo, senão, espera
			 */
			Date dateByTimeZone = new Date(utcDate.getTime() + offset);
			System.out.println("Horário em " + string + ": " + sdf.format(dateByTimeZone));
		}
		
	}

}
