package bus.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import bus.ZipService;
import dao.ZipDao;
import entity.Zip;

public class ZipServiceImpl implements ZipService {

	private ZipDao zipDao;

	public ZipServiceImpl() {
		zipDao = new ZipDao();
	}

	@Override
	public List<Zip> getZipByCity(String city) {
		if (city != null && !city.trim().equals("")) {
			List<Zip> docs = zipDao.getZipByCity(city);

			return docs;
		}

		return null;
	}

	@Override
	public Zip getZip(String id) {
		Zip zip = zipDao.getZip(id);
		return zip;
	}

	// Cau 1:
	@Override
	public List<Zip> getNDocumentFromK(int n, int k) {
		// TODO Auto-generated method stub
		ArrayList<Zip> result = new ArrayList();
		for (int i = k; i <= n; ++i) {
			result.add(zipDao.getAllZip().get(i));
		}
		return result;
	}

	// Cau 2:
	@Override
	public boolean addZip(Zip zip) {
		return zipDao.addZip(zip);
	}

	@Override
	public boolean updateZipById(String id, Zip zip) {
		return zipDao.updateZipById(id, zip);
	}

	@Override
	public List<Zip> getZipByPopThanN(int n) {
		// TODO Auto-generated method stub
		return zipDao.getZipByPopThanN(n);
	}

	@Override
	public int getPopByCity(String city) {
		// TODO Auto-generated method stub
		return zipDao.getPopByCity(city);
	}

	@Override
	public List<Zip> getZipByPop(int start, int end) {
		return zipDao.getZipByPop(start, end);
	}

	@Override
	public List<Zip> getZipByStateAndPop(String state, int pop) {
		// TODO Auto-generated method stub
		return zipDao.getZipByStateAndPop(state, pop);
	}

	@Override
	public List<String> getAllState() {
		// TODO Auto-generated method stub
		return zipDao.getAllState();
	}

	@Override
	public void getAllAvgState() {
		zipDao.getAllAvgState();
	}
}
