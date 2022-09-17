package bus;

import java.util.List;

import org.bson.Document;

import entity.Zip;

public interface ZipService {
	public Zip getZip(String id);
	
	//Ex2:
	//1.
	public List<Zip> getNDocumentFromK(int n, int k);
	
	//2.
	public boolean addZip(Zip doc) ;
	
	//3.
	public boolean updateZipById(String id, Zip zip);
	
	//4.
	public List<Zip> getZipByCity(String city);
	
	//5.
	public List<Zip> getZipByPopThanN(int n);
	
	//6.
	public int getPopByCity(String city);
	
	//7.
	public List<Zip> getZipByPop(int start, int end);
	
	//8.
	public List<Zip> getZipByStateAndPop(String state, int pop);
	
	//9.
	public List<String> getAllState();
	
	//11.
	public void getAllAvgState();
}
