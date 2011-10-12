package ncsu.csc.homework;

public class City {

	private String _Name;
	
	private Double _Latitude;
	
	private Double _Longitude;
	
	public City(String name, Double latitude, Double longitude) {
		
		_Name = name;
		
		_Latitude = latitude;
		
		_Longitude = longitude;
		
	}
	
	public String getName() {
		
		return _Name;
		
	}
	
	public Double getLatitude() {
		
		return _Latitude;
		
	}
	
	public Double getLongitude() {
		
		return _Longitude;
		
	}
	
	public String toString() {
		
		return _Name;
		
	}
	
}
