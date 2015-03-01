import java.sql.*;
public class DBWorker {
	private Connection conn = null;
	
	public DBWorker(){
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/admin_EDDN?user=EDDN&password=");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
 
	public boolean insertMarketData(MarketData data){
		//Check if system and station is listed
		
		//Fill in data
		try{
			PreparedStatement statement = conn.prepareStatement("INSERT INTO Commodities (System, Station, Commodity, Buy, Sell, Demand,DemandLevel, Supply, SupplyLevel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE Buy=?, Sell=?, Demand=?, DemandLevel=?, Supply=?, SupplyLevel=?");
			statement.setString(0, data.systemName);
			statement.setString(1, data.stationName);
			statement.setString(2, data.itemName);
			statement.setInt(3, data.buyPrice);
			statement.setInt(4, data.sellPrice);
			statement.setInt(5, data.demand);
			statement.setString(6, data.demandLevel);
			statement.setInt(7, data.supply);
			statement.setString(8, data.supplyLevel);
			
			statement.setInt(9, data.buyPrice);
			statement.setInt(10, data.sellPrice);
			statement.setInt(11, data.demand);
			statement.setString(12, data.demandLevel);
			statement.setInt(13, data.supply);
			statement.setString(14, data.supplyLevel);
			
			statement.executeUpdate();
			
		}catch (SQLException e){
		}
		return false;
	}
	
}
