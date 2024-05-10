package ve.com.avss.ventas.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PruebaCalendario {
 private static Map<Integer, String> nombresMes = null;
 private static int anchoCelda = 5;
 private static float anchoTablaAnioCalendario = 50f;
 private static float anchoCalendario = 20f;
 
 
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();

		int date = 0; // almacena la semana número 1 calculada después del año y mes ingresados
		int day_max = 0; // El número de días en el mes donde se almacenan el año, mes y año ingresados
		
		Document document = null;
		
		try {
			
			String direccion = "C:\\avss\\calendario.pdf";
			document = new Document(PageSize.A4_LANDSCAPE.rotate(),0,0,0,0);
		
			PdfWriter.getInstance(document, new FileOutputStream(direccion));
			 
			document.open();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		int year = 0; // se usa para guardar el año ingresado
		int month = 0; // usado para guardar el mes de entrada
		cargarNombreMeses(); // cargando nombres de los meses

		Scanner in = new Scanner(System.in);
		System.out.print("Ingrese el año:");
		year = in.nextInt();
		System.out.print("Ingrese el mes:");
		month = in.nextInt();
		System.out.println("La fecha ingresada es:" + year + " - " + month);
		in.close();
		
		
		 	try {
		 		PdfPTable tablaAnioCalendario = new PdfPTable(1); 
		 		tablaAnioCalendario.setWidthPercentage(anchoTablaAnioCalendario);
				
				PdfPCell cell = null;
			 	cell = new PdfPCell(new Phrase("CALENDARIO "+year));
			 	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
			 	cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			 	cell.setPaddingBottom(5);
			 	tablaAnioCalendario.addCell(cell);
			 	document.add(tablaAnioCalendario);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	
		
		
		switch (month) {
		case 0:
			for (int i = 1; i < 13; i++) {
				// Establezca el mes en el 1 del mes siguiente, y el día anterior, obtenga
				// primero la cantidad de días en el mes actual
				month = i;
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DATE, 1);

				calendar.add(Calendar.DATE, -1);
				day_max = calendar.get(Calendar.DATE);

				// Establezca la hora en 1 nuevamente, que es el día de la semana, el domingo es
				// 0, el lunes es 1, y así sucesivamente
				calendar.add(Calendar.DATE, -(day_max - 1));
				date = calendar.get(Calendar.DAY_OF_WEEK) - 1;

				System.out.println(calendar.getTime());
				printfCalendar(date, day_max, month, year, document);
				
			}
			break;

		default:
			// Establezca el mes en el 1 del mes siguiente, y el día anterior, obtenga
			// primero la cantidad de días en el mes actual
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.DATE, 1);

			calendar.add(Calendar.DATE, -1);
			day_max = calendar.get(Calendar.DATE);

			// Establezca la hora en 1 nuevamente, que es el día de la semana, el domingo es
			// 0, el lunes es 1, y así sucesivamente
			calendar.add(Calendar.DATE, -(day_max - 1));
			date = calendar.get(Calendar.DAY_OF_WEEK) - 1;

			System.out.println(calendar.getTime());
			printfCalendar(date, day_max, month, year, document);
			break;
		}
		
		 document.close();

	}

	/**
	 * Función: imprimir un calendario
	 * 
	 * @param start   es el día de la semana el primero del mes
	 * @param day_max cuantos días en el mes
	 */
	public static void printfCalendar(int start, int day_max, int month, int year, Document document) {
		 String[] dias = new String[7];
		 dias[0] = "D";
		 dias[1] = "L";
		 dias[2] = "M";
		 dias[3] = "M";
		 dias[4] = "J";
		 dias[5] = "V";
		 dias[6] = "S";
		 String lineaSeparadora = "==================================================";
		
        try {
        	 
    		 
    		 PdfPTable filaMesCalendario = new PdfPTable(1); 
    		 filaMesCalendario.setWidthPercentage(anchoCalendario);
    		 
    		 PdfPCell cell = null;
             cell = new PdfPCell();
             cell.setHorizontalAlignment (Element.ALIGN_CENTER);
             cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
             cell.setPaddingBottom(5);
             
            
             
             
        	System.out.println(lineaSeparadora);
			
			 
			 
			 document.add(new Phrase(Chunk.NEWLINE));
			 document.add(new Phrase(Chunk.NEWLINE));
			 
			
        	System.out.println(lineaSeparadora);
        	
        	
        	cell = new PdfPCell(new Phrase("\t\t"+nombresMes.get(month)));
            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPaddingBottom(5);
            filaMesCalendario.addCell(cell);
        	document.add(filaMesCalendario);
    		System.out.println("\t\t"+nombresMes.get(month));
//*************************** nombre de calendario y mes*****************************   
    		
    		
//*************************** inicio dias del mes *****************************   	
    		System.out.println(lineaSeparadora);
    		// Encabezado de salida
    		 PdfPTable filaDiasCalendario = new PdfPTable(7); 
    		 filaDiasCalendario.setWidthPercentage(anchoCalendario);
    		 
    		 for (int i = 0; i < dias.length; i++) {
    			 cell = new PdfPCell(new Phrase(dias[i]));
                 cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                 cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                
                 filaDiasCalendario.addCell(cell);
				
			}
    		 document.add(filaDiasCalendario);
 //*************************** fin dias del mes ********************************************   		 
    		 
    		//document.add(new Phrase("D\tL\tM\tM\tJ\tV\tS\n"));
    		//document.add(new Phrase(Chunk.NEWLINE));
    		System.out.print("D\tL\tM\tM\tJ\tV\tS\n");
//*************************** inicio fecha del mes ********************************************  
    		int day = 1; // número de días para imprimir
    		month: for (int i = 0; i < 6; i++) {
    			PdfPTable filaSemana = new PdfPTable(7); 
				filaSemana.setWidthPercentage(anchoCalendario);
    			for (int j = 0; j < 7; j++) {
    				 
    				 
    				cell = new PdfPCell();
    	            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
    	            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    	            cell.setPaddingBottom(5);
    				
    				
    				
    				// Al imprimir la primera línea, vacíe el espacio antes del inicio
    				if (i == 0 && j < start) {
    					
    					//document.add(new Phrase("\t"));
    					System.out.print("\t");
    					
    					filaSemana.addCell(cell);
    					
    					// fuera del ciclo actual
    					continue;
    				}

    				if (day > day_max) {
    					
    					for (int a = 0; a < (7-j); a++) {
    						cell = new PdfPCell();
    	    	            cell.setHorizontalAlignment (Element.ALIGN_CENTER);
    	    	            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    	    	            cell.setPaddingBottom(5);
    	    	            filaSemana.addCell(cell);
    	    	            document.add(filaSemana);
							
						}
    					//document.add(new Phrase(Chunk.NEWLINE));
    					System.out.println();
    					break month;
    				}
    				//document.add(new Phrase(day +"\t"));
    				System.out.print(day + "\t");
    				cell.addElement(new Phrase(day+""));
    				filaSemana.addCell(cell);
    				
    				day++;
    				
    			}
    			// Ajustar después de imprimir una línea
    			
    			System.out.println();
    			document.add(filaSemana);
    		} // for month
    		
//*************************** fin fecha del mes ********************************************      		
    		
    		
    		
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	
		

	}

	private static void cargarNombreMeses() {
		nombresMes = new HashMap<Integer, String>();
		nombresMes.put(1, "enero");
		nombresMes.put(2, "febrero");
		nombresMes.put(3, "marzo");
		nombresMes.put(4, "abril");
		nombresMes.put(5, "mayo");
		nombresMes.put(6, "junio");
		nombresMes.put(7, "julio");
		nombresMes.put(8, "agosto");
		nombresMes.put(9, "septiembre");
		nombresMes.put(10, "octubre");
		nombresMes.put(11, "noviembre");
		nombresMes.put(12, "diciembre");
	}

}
