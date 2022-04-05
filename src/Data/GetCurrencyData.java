package Data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetCurrencyData implements CurrencyData {
    private StringBuilder res;
    public static int arrayLength = 7;

    public GetCurrencyData() throws Exception {
        URL url = new URL(basicCurrencyURL + currencyAPI + "&" + base + "&limit=INR,EUR,JPY,BTC,GBP,CAD,AUD");
        System.out.println(basicCurrencyURL + currencyAPI + "&" + base);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        res = new StringBuilder();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        int c;
        while ((c = in.read()) != -1)
            res.append((char) c);
    }

    private String[] parseJson(String input) {
        String[] vals = null;

        try {
            JsonObject object = new JsonParser().parse(input).getAsJsonObject();
            String data = object.getAsJsonObject("rates").toString();
            vals = data.substring(1, data.length() - 1).split(",");
            for (String s : vals) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vals;
    }

    public static void loadData(Object[] vals) throws Exception {
        GetCurrencyData data = new GetCurrencyData();
        String[] values = data.parseJson(data.res.toString());
        arrayLength = values.length;
        for (int i = 0; i < values.length; i++)
            vals[i] = values[i];
    }
}
