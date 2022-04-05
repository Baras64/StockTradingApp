package Data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetData implements Data {

    //private static String[] ApiKeys = {"T5R26ILWHINLNI43", "9C699CA0OCGI6EAE", "5KK8M963SPSZP8KH", "TD34VBSS8XO5M9W8"};
    //private static String  API = "apikey=" + ApiKeys[0];
    //private String basicURL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&";
    private String symbol;
    //private String interval;
    private StringBuilder res;

    private static int i = 1;

    public GetData(String ticker) throws Exception {
        symbol = "symbol=" + ticker + "&";

        URL url = new URL(basicURL + symbol + interval + API);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        res = new StringBuilder();

        System.out.println(url.toString());
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        int c;

        while ((c = in.read()) != -1)
            res.append((char) c);

    }

    private double[] parseJson(String input) {
        int temp;
        double[] values = null;

        try {
            JsonObject object = new JsonParser().parse(input).getAsJsonObject();
            String data = object.getAsJsonObject("Time Series (1min)").toString();
            String[] vals = data.substring((temp = data.indexOf('{', 1)) + 1, data.indexOf('}', temp)).split(",");
            values = new double[vals.length];
            System.out.println(data);

            for (temp = 0; temp < vals.length; temp++)
                values[temp] = Double.parseDouble(vals[temp].substring(vals[temp].indexOf(":") + 1).replaceAll("\"", ""));
        } catch (NullPointerException ex) {
            System.out.println("API limit exceeded. Please wait for 1 minute.");
        }
        return values;
    }

    public static void loadData(String ticker, Object[] vals) throws Exception {
        GetData data = new GetData(ticker);
        double[] values = data.parseJson(data.res.toString());

        for (int i = 1, j = 0; j < values.length; i++, j++)
            vals[i] = values[j];
    }

    public static double loadData(String ticker) throws Exception {
        GetData data = new GetData(ticker);
        return data.parseJson(data.res.toString())[0];
    }

}
