package br.sca.utils;

import java.text.*;   // Em fun��o da classe SimpleDateFormat
import java.sql.Date;
import java.util.GregorianCalendar;


public class TrataData
{	
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
    public static boolean dataInvalida(String data)
    {
      try
      {        
        String [] dt = data.split("/");
        int ano = Integer.parseInt(dt[2]);
        int mes = Integer.parseInt(dt[1]);
        int dia = Integer.parseInt(dt[0]);
        if ((dia < 1)||(dia > 31)){
            return true;
        }
        if (((dia == 30)||(dia ==31)) && (mes == 2)){
            return true;
        }
        if ((dia==31)&&((mes==2)||(mes==4)||(mes==6)||(mes==9)||(mes==11))){
            return true;
        }
        if ((mes<1)||(mes>12)){
            return true;
        }
        if ((dia==29)&&(mes==2)&&(new GregorianCalendar().isLeapYear(ano)==false)){
            return true;
        }                 
      }
      catch (Exception e)
      {
        return true;   
      }         
        return false;
}

    public static boolean AnoSemestreInvalido(String anoSemestre)
    {
      try
      {
        String anoString = anoSemestre.trim().substring(0,4);
        String semestreString = anoSemestre.trim().substring(4,5);
        
 
        int ano = Integer.parseInt(anoString);
        int semestre = Integer.parseInt(semestreString);
        
        if ((semestre < 1)||(semestre > 2)){
            return true;
        }
 
      }
      catch (Exception e)
      {
        return true;   
      }        
        return false;
}
   
    
    
 public static Date strToDate(String umaData)
	{
         
                 
          String [] dt = umaData.split("/");
          /*int ano = Integer.parseInt(dt[2]);
          int mes = Integer.parseInt(dt[1]);
          int dia = Integer.parseInt(dt[0]);*/

          String ano = dt[2];
          String mes = dt[1];
          String dia = dt[0];

          
          //Date data = new Date(ano,mes,dia);

          return Date.valueOf(ano + "-" + mes + "-" + dia);
          //return data;
          
         /*   int dia = Integer.parseInt(umaData.substring(0,2));
		int mes = Integer.parseInt(umaData.substring(3,5));
		int ano = Integer.parseInt(umaData.substring(6,10));

		return Date.valueOf(ano + "-" + mes + "-" + dia);*/
	}

	public static String dateToStr(Date umaData)
	{	if (umaData == null)
			return "";
		else
		{	return sdf.format(umaData);	
		}
	}

        public static String dataSistema()
        {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            GregorianCalendar date = new GregorianCalendar();
            return dateFormat.format(date.getTime());
        }
}