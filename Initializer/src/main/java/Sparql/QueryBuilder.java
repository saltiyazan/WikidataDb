package Sparql;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryBuilder {

	public HashMap<String, String> selectAs;
	public HashMap<String, String> isA;
	public HashMap<String, String> hasA;
	public HashMap<String, String> hasNo;
	public HashMap<String, ArrayList<String>> filter;
	// public ArrayList<HashMap<String, String>> where;

	public QueryBuilder() {

		selectAs = new HashMap<>();
		isA = new HashMap<>();
		hasA = new HashMap<>();
		hasNo = new HashMap<>();
		filter = new HashMap<>();
		// where = new ArrayList<>();

	}

	public String BuildQueryStringEn() {
		String prefix = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" + "\n";
		String selectDist = "SELECT DISTINCT ";
		StringBuilder selectParams = new StringBuilder("");

		for (String pa : selectAs.values()) {
			selectParams.append(" ?" + pa + " ");
		}
		String selection = selectParams.toString();
		StringBuilder condition = new StringBuilder(" ");
		int i = 0;
		for (String s : isA.keySet()) {
			condition.append(" ?" + s + " wdt:P31 " + "wd:" + isA.get(s) + ".\n");
		}
		if (hasA.size() > 0) {
			for (String s : hasA.keySet()) {
				i++;
				if (selectAs.containsKey(hasA.get(s))) {
					condition.append("? " + s + " wdt:" + hasA.get(s) + " ?" + selectAs.get(hasA.get(s)) + ".\n");
				} else {
					String label = s + hasA.get(s) + i;
					selectAs.put(hasA.get(s), label);
					condition.append(" ?" + s + " wdt:" + hasA.get(s) + " ?" + label + ".\n");
				}
			}
		}
		condition.append("  SERVICE wikibase:label { bd:serviceParam wikibase:language \"[AUTO_LANGUAGE],en\". }\n");

		if (filter.size() > 0) {
			for (String s : filter.keySet()) {
				StringBuilder filterfor = new StringBuilder(" ");
				for (String inList : filter.get(s)) {
					if (filter.get(s).indexOf(inList) == (filter.get(s).size() - 1)) {
						filterfor.append("(?" + selectAs.get(s) + " " + inList + ")");
					} else {
						filterfor.append("(?" + selectAs.get(s) + " " + inList + ") && ");
					}

				}
				condition.append(" FILTER( " + filterfor.toString() + " )");
			}
		}
		if (hasNo.size() > 0) {
			StringBuilder hasnoCond = new StringBuilder(" ");
			for (String s : hasNo.keySet()) {
				hasnoCond.append(
						"?" + selectAs.get(s) + " wdt:" + hasNo.get(s) + " ?" + selectAs.get(hasNo.get(s)) + ".");
			}

			condition.append("\n OPTIONAL {FILTER NOT EXISTS {" + hasnoCond.toString() + "}}");
		}
		String where = "Where {\n" + condition.toString() + "\n}";
		return prefix + selectDist + selection + where;
	}

}
