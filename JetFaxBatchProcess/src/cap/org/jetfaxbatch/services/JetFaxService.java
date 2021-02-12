package cap.org.jetfaxbatch.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cap.org.jetfaxbatch.dao.JetFaxDao;
import cap.org.jetfaxbatch.util.CAPProperties;

public class JetFaxService {

	JetFaxDao dao = new JetFaxDao();
	CAPProperties _appProp = new CAPProperties();

	public Map<Integer, Set<String>> checkProductInFolderPath() {

		List<Integer> yearDropdown = getYearsDropdown();
		List<String> productsDropdown = dao.getProductsDropdown(yearDropdown);
		Map<Integer, Set<String>> multiMap = new HashMap<Integer, Set<String>>();
		String filepath = _appProp.getProperty("jetfax.filepath");

		for (Integer year : yearDropdown) {
			Set<String> hashSet = new HashSet<>();
			for (String product : productsDropdown) {
				File file = new File(filepath + (year.toString())); // Change this to the directory you want to search
																	// in.

				if (file.exists() && file.isDirectory()) {
					String[] files = file.list(); // get the files in String format.
					for (String fileName : files) {
						if (fileName.contains(product))
							hashSet.add(fileName);
					}

				}

			}
			multiMap.put(year, hashSet);
		}

		return multiMap;

	}

	public Set<String> getMailingNames() {
		Set<String> mailingname = new HashSet<>();
		List<String> fileNamesList = new ArrayList<String>();
		Map<Integer, Set<String>> mailingMap = checkProductInFolderPath();
		for (Map.Entry<Integer, Set<String>> entry : mailingMap.entrySet()) {
			System.out.println("Year:" + entry.getKey());
			System.out.println("MatchedProducts:" + entry.getValue() + "     size:" + entry.getValue().size());
			fileNamesList.addAll(entry.getValue());
		}

		for (String filename : fileNamesList) {
			String name = filename.split("\\.")[0];
			if (name.indexOf("-") != -1) {
				name = name.replaceAll("[^A-Za-z0-9]+", "");
				if (Character.isDigit(name.charAt(0))) {
					name = name.replaceAll("\\d+", "") + name.replaceAll("\\D+", "");
				}
				mailingname.add(name);

			} else {
				String modifiedName = name.split(" ")[0];
				mailingname.add(modifiedName);
			}
		}
		return mailingname;
	}

	public List<Integer> getYearsDropdown() {
		List<Integer> yearsList = new ArrayList<Integer>();
		Calendar cal = Calendar.getInstance();
		for (int i = 0; i <= 2; i++) {
			yearsList.add(cal.get(Calendar.YEAR) - i);
		}
		System.out.println("Current Year List::::::::: " + yearsList);
		return yearsList;
	}

	public void updatejetFaxFlag()  {
		Set<String> mailingNames = getMailingNames();
		int rows = dao.getFinalMailingsList(mailingNames);
		System.out.println(rows + " Rows Updated Successfully");
	}

}
