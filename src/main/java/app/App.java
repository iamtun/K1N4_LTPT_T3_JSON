package app;

import com.mongodb.client.MongoClient;

import bus.ZipService;
import bus.impl.ZipServiceImpl;
import dao.ZipDao;
import entity.Zip;
import util.AtlasMongoClient;

public class App {
	public static void main(String[] args) {
		
		MongoClient mongoClient = AtlasMongoClient.getInstane().getMongoClient();
		
//		mongoClient.listDatabaseNames()
//		.forEach(dbName -> System.out.println(dbName));
		
		ZipService zipService = new ZipServiceImpl();
		
		//Cau 1: Hiển thị n documents từ document thứ k.
		//System.out.println(zipService.getNDocumentFromK(3, 1));
	
		//Cau 3: Cập nhật thông tin của một document khi biết id
//		Zip zip = new Zip();
//		zip.setPop(3000);
//		System.out.println(zipService.updateZipById("5c8eccc1caa187d17ca6ed1b", zip));
		
		//Cau 4: Tìm các document có city là PALMER
//		zipService.getZipByCity("PALMER").forEach(zip -> System.out.println(zip));
		
		//Cau 5: Tìm các document có dân số >100000
		//zipService.getZipByPopThanN(100000).forEach(zip -> System.out.println(zip));
		
		//Cau 6: Tìm dân số của thành phố FISHERS ISLAND
		//System.out.println(zipService.getPopByCity("FISHERS ISLAND"));
		
		//Cau7: Tìm các thành phố có dân số từ 10 – 50
		//zipService.getZipByPop(10, 50).forEach(zip -> System.out.println(zip));
		
		//Cau 8: Tìm tất cả các thành phố của bang MA có dân số trên 500
		//System.out.println(zipService.getZipByStateAndPop("MA", 500).size());
		
		//Cau 9: Tìm tất cả các bang (không trùng)
		//zipService.getAllState().forEach(state -> System.out.println(state));
		
		//Cau 11: Tính dân số trung bình của mỗi bang
		zipService.getAllAvgState();
	}
}
