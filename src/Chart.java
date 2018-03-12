import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class Chart {

	private String _apiUrl = "";
	private String _name = "Unknown";
	private float _btcValue = 0.0f;
	private float _eurValue = 0.0f;
	private float _usdValue = 0.0f;
	private float _marketPlaceValue = 0.0f;
	private float _volume24h = 0.0f;
	private float _change24h = 0.0f;
	private int _rank = 0;
	
	public Chart(String name, String apiUrl) {
		_name = name;
		_apiUrl = apiUrl;
	}
	
	public String getName() {
		return _name;
	}
	
	public float getCurrentBtcValue() {
		return _btcValue;
	}
	
	public float getCurrentUsdValue() {
		return _usdValue;
	}
	
	public float getCurrentEurValue() {
		return _eurValue;
	}
	
	public float getCurrentMarketPlace() {
		return _marketPlaceValue;
	}
	
	public float getVolume24h() {
		return _volume24h;
	}
	
	public float getChange24h() {
		return _change24h;
	}
	
	public int getRank() {
		return _rank;
	}
	
	public void printInfo() {
		
		System.out.println("---------- " + _name + " ------------ ");	
		System.out.printf("Market Place: %.0f $\n", _marketPlaceValue);
		System.out.println("Rank : " + _rank);
		System.out.printf("BTC Value: %.5f B\n", _btcValue);
		System.out.printf("USD Value: %.5f $\n", _usdValue);
		System.out.printf("24h volume: %.2f $\n", _volume24h);
		System.out.printf("24h change: %.2f\n", _change24h);
	}
	
	public void updateChart()
	{
		String result = null;
		if (_apiUrl.startsWith("https://"))
		{
			// Send a GET request to the servlet
			try
			{
		
				// Send data
				String urlStr = _apiUrl;
				/*if (requestParameters != null && requestParameters.length () > 0)
				{
					urlStr += "?" + requestParameters;
				}*/
				
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection ();
			
				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				
				while ((line = rd.readLine()) != null)
				{
					sb.append(line);
				}
				
				rd.close();
				result = sb.toString();
				JSONParser parser = new JSONParser();
				JSONArray jsonArray = (JSONArray) parser.parse(result);
				JSONObject json = (JSONObject) jsonArray.get(0);
				
				_rank = Integer.valueOf(json.get("rank").toString());
				_btcValue = Float.valueOf(json.get("price_btc").toString());
				_usdValue = Float.valueOf(json.get("price_usd").toString());
				_eurValue = Float.valueOf(json.get("price_eur").toString());
				_marketPlaceValue = Float.valueOf(json.get("market_cap_usd").toString());
				_volume24h = Float.valueOf(json.get("24h_volume_usd").toString());
				_change24h = Float.valueOf(json.get("percent_change_24h").toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
