import java.util.*;
import java.util.stream.*;

public class MergingTwoMaps{
	
	public static void main(String[] args){
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("name","abhijeet");
		map1.put("age","34");
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("sex","male");
		map2.put("age","33");
		
		Map<String, String> map3 = new HashMap<String, String>(map1);
		
		map2.forEach((key, value) -> map3.merge(key, value, (value1, value2) -> value1+"-"+value2));
		
		System.out.println(map3);
		
		Stream<Map.Entry<String,String>> stream = Stream.concat(map1.entrySet().stream(), map2.entrySet().stream());
		Map<String, String> map4 = stream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (value1, value2) -> value2 + "-" + value1));
		System.out.println(map4);
		
		Stream<Map<String, String>> stream2 = Stream.of(map1, map2);
		Stream<Map.Entry<String, String>> stream3 = stream2.flatMap(map -> map.entrySet().stream());
		System.out.println(stream3.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (value1, value2) -> value1 + "-" + value2)));
	}
}