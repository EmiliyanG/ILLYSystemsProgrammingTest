/*
 * @author: Emiliyan Grigorov
 * Date: 29 June 2016
 * Purpose: Compare two objects of the same type (which is unknown) based on their fields(properties) and 
 * return a list of all differences if such exist. The assumption is that even if objects have object type fields
 * the equals method and toString method of each object field has been overridden 
 *
 * */
package grigorov.emiliyan;



import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectComparator {
	public final static String EQUAL_OBJECTS = "equal";
	public final static String DIFFERENT_OBJECTS = "objects are from different type";
	
	private List<Field> diffObj1 = new ArrayList<>();
	private List<Field> diffObj2 = new ArrayList<>();
	
	

	/*return DIFFERENT_OBJECTS if objects cannot be compared (are not of the same type)
	 * return EQUAL_OBJECTS if objects are equal (all fields in both objects are the same )
	 * return String (list) with different fields if such exist
	 * */
	public static String compareObjects(Object obj1, Object obj2){
		
		/*check if the two objects are of the same type  */
		if(!obj1.getClass().equals(obj2.getClass())) return DIFFERENT_OBJECTS; //if not return null
		
		/*get all fields of both objects 
		 * at this point the objects are guaranteed to be of the same type
		 * however not all of their fields may have been initialized and therefore
		 * their field arrays may be of a different length */
		Field[] obj1fields = obj1.getClass().getDeclaredFields();
		Field[] obj2fields = obj2.getClass().getDeclaredFields();
		
		/*if both field arrays are empty then objects are equal*/
		if(obj1fields.length<1&&obj2fields.length<1)return EQUAL_OBJECTS;
		/*at this point both field arrays are guaranteed to belong to the same object type 
		 * even if one object may have non-initialized fields these fields are still added to the 
		 * array of declared fields, and therefore both arrays are guaranteed to be of the same length*/
		/*each difference in both field arrays will be formatted and stored in diff String*/
		String diff = "";
		boolean equal = true;
		for(int i = 0 ;i<obj1fields.length-1;i++){
			/*extract each field value*/
			Object value1 = extractFieldValue( obj1, obj1fields[i]);
			Object value2 = extractFieldValue( obj2, obj1fields[i]);
			/*if both field values are not null compare them*/
			if( value1!=null &&value2 != null){
				if(!value1.equals(value2)){
					equal = false;
					diff+= obj1fields[i].getName()+" >> obj1: "+value1+" ; obj2: "+ value2+"\n" ;
				}
			}
			/*if both field values are null they are equal*/
			else if (value1==null &&value2 == null){}
			/*one field value is null and the other one is not*/
			else{
				equal = false;
				diff+= obj1fields[i].getName()+" >> obj1: "+value1+" ; obj2: "+ value2+"\n" ;
			}
		}

		if(equal) return EQUAL_OBJECTS;
		else return diff;
	}
	
	/*
	 * This method compares two fields 
	 * the initial assumption is that both fields are from objects of the same type
	 * returns false if the fields are different 
	 * returns true if the fields are the same based on comparing their values
	 * */
	protected static boolean compareField(Object parent1, Object parent2, Field field1,Field field2){
		
		if(!field1.getName().equals(field2.getName())) return false; //if not return null
		
		/*extract values from fields*/
		Object value=extractFieldValue(parent1, field1);
		Object value2=extractFieldValue(parent2, field2);
		
		/*check if values are the same assuming that their equals method was modified 
		 * and that both values are not null */
		return value!=null && value.equals(value2);
		
	}
	
	/*provided that a sufficient field object parent is provided 
	 * the method will return value object for the given field */
	protected static Object extractFieldValue(Object parent, Field f){
		/*ensure that field is accessible by setting the modifier to public*/
		f.setAccessible(true);
		Object value = null;
		try {
			value = f.get(parent);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	
	
}
