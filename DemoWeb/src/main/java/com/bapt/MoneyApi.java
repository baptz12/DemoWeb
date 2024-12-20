package com.bapt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;

public class MoneyApi
{
  public static double main(String[] args) throws Exception
  {
    try
    {
      if (args.length != 3)
      {
        System.out
            .println("Mauvais format, veuillez respecter le format : MONTANT,MONNAIE DU MONTANT,MONNAIE DU RESULTAT");
        System.exit(1);
      }
      String count1 = args[0];
      String count2 = args[1];
      String currency3 = args[2];
      String API_KEY = "YOUR API KEY";
      double amount = Double.parseDouble(count1);

      URL url = new URL(
          "https://api.currencyapi.com/v3/latest?apikey=" + API_KEY);
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("GET");
      int responseCode = connection.getResponseCode();

      if (responseCode == HttpURLConnection.HTTP_OK)
      {
        BufferedReader in = new BufferedReader(new InputStreamReader(
            connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null)
        {
          response.append(inputLine);
        }
        in.close();
        double result = 0;
        result = parse(response.toString(), count2, currency3, amount);
        return result;
      }
      else
      {
        System.out.println("Erreur avec l'api, code d'erreur: " + responseCode);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 0;
  }

  public static double parse(String responseBody, String fromCurrency,
      String toCurrency, double amount)
  {
    JSONObject obj = new JSONObject(responseBody);
    JSONObject rate = obj.getJSONObject("data");

    double fromRate = rate.getJSONObject(fromCurrency).getDouble("value");
    double toRate = rate.getJSONObject(toCurrency).getDouble("value");

    double convertedAmount = amount * (toRate / fromRate);
    return convertedAmount;
  }
}
