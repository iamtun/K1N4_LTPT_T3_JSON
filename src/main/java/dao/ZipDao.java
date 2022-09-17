package dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.print.Doc;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

import entity.Zip;
import util.AtlasMongoClient;

public class ZipDao {
	private MongoCollection<Zip> zipCol;

	public ZipDao() {
		// config
		PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(pojoCodecProvider));

		zipCol = AtlasMongoClient.getInstane().getMongoClient().getDatabase("sample_training")
				.getCollection("zips", Zip.class).withCodecRegistry(codecRegistry);
	}

	public Zip getZip(String id) {
		return zipCol.find(Filters.eq("_id", new ObjectId(id))).first();
	}

	// Tìm các document có dân số >100000
	// db.zips.find({pop:{$gt:100000}})
	public List<Zip> getZipByPop(int pop) {

		Document filter = Document.parse("{pop:{$gt:" + pop + "}}");
		return zipCol.find(filter).into(new ArrayList<>());

	}

	// 1.
	public List<Zip> getAllZip() {
		Document filter = Document.parse("{}");
		return zipCol.find(filter).into(new ArrayList<>());
	}

	// 2.
	public boolean addZip(Zip doc) {
		InsertOneResult result = zipCol.insertOne(doc);
		return result.getInsertedId() != null;
	}

	// 3. update colunm pop
	public boolean updateZipById(String id, Zip zip) {
		Document query = new Document("_id", new ObjectId(id));

		Document dataUpadte = new Document("pop", zip.getPop());

		Document update = new Document("$set", dataUpadte);

		UpdateResult result = zipCol.updateOne(query, update);

		return result.getModifiedCount() == 1;
	}

	// 4. Tìm các document có city là PALMER
	public List<Zip> getZipByCity(String city) {
		return zipCol.find(eq("city", city)).into(new ArrayList<>());
	}

	// 5. Tìm các document có dân số >100000
	public List<Zip> getZipByPopThanN(int n) {
		Document filter = Document.parse("{pop: {$gt:" + String.valueOf(n) + "}}");
		return zipCol.find(filter).into(new ArrayList<>());
	}

	// 6. Tìm dân số của thành phố FISHERS ISLAND
	// db.zips.find({city: "FISHERS ISLAND"}, {pop: 1, _id: 0})
	public int getPopByCity(String city) {
		Zip pop = zipCol.find(Filters.eq("city", city)).first();
		return pop.getPop();
	}

	// 7. Tìm các thành phố có dân số từ 10 – 50
	// db.zips.find({"pop" : {'$gt' : 10, '$lt': 50}})
	public List<Zip> getZipByPop(int start, int end) {
		Document filter = Document.parse("{\"pop\" : {'$gt' : " + start + ", '$lt': " + end + "}}");
		return zipCol.find(filter).into(new ArrayList<Zip>());
	}

	// 8. Tìm tất cả các thành phố của bang MA có dân số trên 500
	// db.zips.find({"$and" : [ {"state": "MA"}, {"pop": {"$gt": 500} } ] })
	public List<Zip> getZipByStateAndPop(String state, int pop) {
		Bson filter = Filters.and(Filters.eq("state", state), Filters.gt("pop", pop));
		return zipCol.find(filter).into(new ArrayList<Zip>());
	}
	
	//9. Tìm tất cả các bang (không trùng)
	//db.zips.distinct("state")
	public List<String> getAllState(){
		return zipCol.distinct("state", String.class).into(new ArrayList<String>());
	}

	//11.Tính dân số trung bình của mỗi bang
	//db.zips.aggregate({$group: {_id: "$city",avgPOP: {$avg: "$pop"}}})
	//chua duoc
	public void getAllAvgState(){
		Bson group = Aggregates.group(Filters.eq("_id", "$city"), Accumulators.avg("avgPOP", "$pop"));
		Bson project = Aggregates.project(Projections.include("avgPOP"));
		zipCol.aggregate(Arrays.asList(group, project)).forEach(zip -> System.out.println(zip));
//		while(result.hasNext()) {
//			System.out.println(result.next());
//		}
		
		//System.out.println(result);
	}
}
