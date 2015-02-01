/**
 * Represents one commodity in one station.
 *  Required fields are: systemName, stationName, itemName, stationStock, sellPrice, demand, timestamp.
 *  See http://schemas.elite-markets.net/eddn/commodity/1 for mor information.
*/
public class MarketData {
	public String systemName;
	public String stationName;
	public String itemName;
	public int buyPrice;
	public int supply;
	public String supplyLevel;
	public int sellPrice;
	public int demand;
	public String demandLevel;
	public String timestamp;
	
	public MarketData(){
		
	}
	
	public boolean validate(){
		return false;
	}
}
