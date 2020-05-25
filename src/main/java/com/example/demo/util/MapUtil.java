package com.example.demo.util;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtil {
	  /**
	   * select年の表示に使用するアイテム
	   */
	  public final static Map<String, String> SELECT_ITEMS_YEAR =
	    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
	    {
	    	Calendar calendar = Calendar.getInstance();
	    	int thisYear = calendar.get(Calendar.YEAR);

	    	for (Integer i = 0; i < 100; i++) {
	    		Integer year = thisYear - ( i +1) ;
				put(i.toString(),year.toString());
			};
	    }
	  });

	  /**
	   * select月の表示に使用するアイテム
	   */
	  public final static Map<String, String> SELECT_ITEMS_MONTH =
	    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
	    {

	    	for (Integer i = 0; i < 12; i++) {
	    		Integer month = i + 1;
				put(i.toString(),month.toString());
			};

	    }
	  });

	  /**
	   * select日付けの表示に使用するアイテム
	   */
	  public final static Map<String, String> SELECT_ITEMS_DAY =
	    Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
	    {

	    	for (Integer i = 0; i < 31; i++) {
	    		Integer day = i + 1;
				put(i.toString(), day.toString());
			};

	    }
	  });

}
