package grigorov.emiliyan;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import junit.framework.Assert;

import org.junit.Test;

public class TestCases {

	/*Test objects of different types
	 * The test should not succeed if the objects are of the same type*/
	@Test
	public void testCompareDifferentTypeObjects() {
		TestObject obj1= new TestObject(3,3.3,"Cool",15);
		TestObject obj2= new TestObject(3,3.3,"Cool",15);
		assertTrue(ObjectComparator.compareObjects(new Object(), obj1).equals(ObjectComparator.DIFFERENT_OBJECTS));
		assertTrue(ObjectComparator.compareObjects(obj2, obj1)!=null);
	}
	
	@Test
	public void testEqualObjects(){
		TestObject obj1= new TestObject(3,3.3,"Cool",15);
		TestObject obj2= new TestObject(3,3.3,"Cool",15);
		assertTrue(ObjectComparator.compareObjects(obj1, obj2).equals(ObjectComparator.EQUAL_OBJECTS));
	}
	@Test
	public void testNonEqualObjectsOfTheSameType(){
		TestObject obj1= new TestObject(3,3.3,"Cool",13);
		TestObject obj2= new TestObject();
		obj2.setS(3);
		obj2.setJ(3.5);
		obj2.setP(13);
		String result = ObjectComparator.compareObjects(obj1, obj2);
		assertTrue(!result.equals(ObjectComparator.EQUAL_OBJECTS));
		assertTrue(!result.equals(ObjectComparator.DIFFERENT_OBJECTS));
		System.out.println(result);
		
	}
	@Test
	public void testExtractFieldValue(){
		TestObject obj1= new TestObject(3,3.3,"Cool",15);
		Field[] obj1fields = obj1.getClass().getDeclaredFields();
		assertTrue(ObjectComparator.extractFieldValue(obj1, obj1fields[0]).equals(3));
	}
	
	@Test
	public void testSingleFieldComparison(){
		TestObject obj1= new TestObject(3,3.3,"Cool",15);
		TestObject obj2= new TestObject(3,3.3,"Cool",15);
		Field[] obj1fields = obj1.getClass().getDeclaredFields();
		Field[] obj2fields = obj1.getClass().getDeclaredFields();
		
		for(int i=0; i<obj1fields.length-1;i++){
			assertTrue(ObjectComparator.compareField(obj1, obj2, obj1fields[i], obj2fields[i]));
		}
		
	}
	@Test
	public void testNonInitializedFields(){
		TestObject obj1= new TestObject();
		obj1.setJ(3.3);
		Field[] obj1fields = obj1.getClass().getDeclaredFields();
		
		
		String diff = "";
		
		for(int i = 0;i< obj1fields.length-1;i++){
			Object value = ObjectComparator.extractFieldValue(obj1, obj1fields[i]);
			if(value!= null) diff += obj1fields[i].getName()+" = " +value+"\n";
		}
		String testString = "s = 0\n"+"j = 3.3\n"+"p = 0\n";
		assertTrue(testString.equals(diff));
	}
	
//	@Test
//	public void test(){
//		TestObject obj1= new TestObject(3,3.3,null,15);
//		Field[] obj1fields = obj1.getClass().getDeclaredFields();
//		for(int i=0;i<obj1fields.length-1;i++){//the last field of the array is the object itself 
//			obj1fields[i].setAccessible(true);
//			Object value= null;
//			try {
//				value = obj1fields[i].get(obj1);
//			} catch (IllegalArgumentException | IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if(value !=null) {
//				System.out.println(obj1fields[i].getName()+" = " + value.toString());
//				
//			}
//			
//		}
//	}
	
	
	private class TestObject{
		
		private int s;
		private double j;
		private String v;
		private int p;
		public TestObject(){
			
		}
		public TestObject(int s, double j, String v, int p){
			this.setS(s);
			this.setJ(j);
			this.setV(v);
			this.setP(p);
		}
		public int getP() {
			return p;
		}
		public void setP(int p) {
			this.p = p;
		}
		public String getV() {
			return v;
		}
		public void setV(String v) {
			this.v = v;
		}
		public double getJ() {
			return j;
		}
		public void setJ(double j) {
			this.j = j;
		}
		public int getS() {
			return s;
		}
		public void setS(int s) {
			this.s = s;
		}
		
		
	}
}
