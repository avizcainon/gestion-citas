package ve.com.avss.ventas.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.gt.jpfbatch.Main;
import com.toedter.calendar.JCalendar;



public class Prueba extends JFrame{
	private static JCalendar calendar;
	private static  JFormattedTextField campo1 = new JFormattedTextField ();
	public Prueba() {
		
	}
	
	private static SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] claveEncriptacion = clave.getBytes("UTF-8");
        
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        
        claveEncriptacion = sha.digest(claveEncriptacion);
        claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);
        
        SecretKeySpec secretKey = new SecretKeySpec(claveEncriptacion, "AES");

        return secretKey;
    }
	
	public static String encriptar(String datos, String claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = crearClave(claveSecreta);
        
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");        
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] datosEncriptar = datos.getBytes("UTF-8");
        byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
        String encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);

        return encriptado;
    }
	
	public static String desencriptar(String datosEncriptados, String claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = crearClave(claveSecreta);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        
        byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
        String datos = new String(datosDesencriptados);
        
        return datos;
    }
	
	
	protected static void armarMascaraMontoProvisional() {

		String txtMascara = "#,###.00";
		Double maximoPermitido = Util.formatMontoDouble("999999999999999");

		Locale loc = new Locale("de", "FB");
		NumberFormat formato = NumberFormat.getNumberInstance( loc);
		DecimalFormat formatoCI = (DecimalFormat)  formato;
		formatoCI.applyPattern(txtMascara);
		
		NumberFormatter enFormatCI = new NumberFormatter(formatoCI);
		enFormatCI.setAllowsInvalid(true);
		enFormatCI.setMinimum(new Double(0));
		enFormatCI.setMaximum(maximoPermitido);
		enFormatCI.setOverwriteMode(true);
		enFormatCI.setCommitsOnValidEdit(true);

		NumberFormatter dnFormatCI = new NumberFormatter(formatoCI);		
		dnFormatCI.setMinimum(new Double(0));	
		dnFormatCI.setMaximum(maximoPermitido);
		dnFormatCI.setAllowsInvalid(true);

		DefaultFormatterFactory currFactoryCI = new DefaultFormatterFactory(dnFormatCI, enFormatCI);
		campo1.setFormatterFactory(currFactoryCI);
	}
	public static void main(String[] args) throws ParseException {
		Prueba prueba = new Prueba();
		String arg[] = {"help"};
		Main printFiscal = new Main();
		try {
			printFiscal.main(arg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	      try {
	    	  String datosEncriptado = encriptar("ventas-AVSS-04/08/2021", "19908451");
			System.out.println(datosEncriptado);
			System.out.println(desencriptar(datosEncriptado, "19908451"));
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
        double number = 2000000015;
        System.out.println("El número utiliza las siguientes constantes de la clase Locale como parámetros de construcción del objeto de formato y obtendrá diferentes formatos de moneda:");
        // Crear objeto de formato
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        // Formato de moneda con formato de salida
        System.out.println("Locale.CHINA：" + format.format(number));
        format = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.println("Locale.US：" + format.format(number));
        format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        System.out.println("Locale.ENGLISH：" + format.format(number));
        format = NumberFormat.getCurrencyInstance(Locale.TAIWAN);
        System.out.println("Locale.TAIWAN：" + format.format(number));
        
        DecimalFormat twoPlaces = new DecimalFormat("0.00");
        System.out.println(twoPlaces.format(number));
        
        format = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.println("Locale.US：" + format.format(number));
        
        System.out.println(String.format("%,.2f", number));     
		DecimalFormat df = new DecimalFormat("0.00");
		System.out.println(df.format(new Double("125400.0")));
		System.out.println(df.format(new Double("125400.00")));


		prueba.pack();
		prueba.setAlwaysOnTop(false);
		prueba.setSize(300, 250);
		prueba.setTitle("Pruebas");
		prueba.setResizable(true);
		prueba.setVisible(true);

	}

}
