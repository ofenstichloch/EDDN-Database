/**
 * Represents one commodity in one station.
 *  Required fields are: systemName, stationName, itemName, stationStock, sellPrice, demand, timestamp.
 *  See http://schemas.elite-markets.net/eddn/commodity/1 for mor information.
*/
public class MarketData {
	public String systemName;
	public String stationName;
	public String itemName;
	public Integer buyPrice;
	public Integer supply;
	public String supplyLevel;
	public Integer sellPrice;
	public Integer demand;
	public String demandLevel;
	public String timestamp;
	
	public MarketData(){
		
	}
	
	public void print(){
		System.out.print(this.systemName + "/"+this.stationName+":"+this.itemName+"	" + this.sellPrice+ "	");
		if(this.buyPrice != -1){
			System.out.print(this.buyPrice);
		}else{
			System.out.print("Not sold");
		}
		System.out.println();
	}
	
	/**
	 * 
	 * 
	 * @return true, if this object contains valid EDDN content
	 */
	public boolean validate(){
		return true;
	}
}
