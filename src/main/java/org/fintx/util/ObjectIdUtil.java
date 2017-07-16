package org.fintx.util;

import java.util.Base64;
//import java.util.HashSet;
//import java.util.Set;

/*
 * 压缩24位的ObjectId到16位
 * 
 */
public  class ObjectIdUtil {
    
    private ObjectIdUtil(){
        throw new AssertionError("No ObjectIdUtil instances for you!");
    }

	public static String objectId() {
		ObjectId objectid = ObjectId.get();
		return objectid.toHexString();
	}

	public static String base64ObjectId() {
		ObjectId objectid = ObjectId.get();
		return base64ObjectId(objectid);
	}

	protected static String base64ObjectId(ObjectId objectid) {

		// return Base64.encodeBase64URLSafeString(bb.array());
		return Base64.getUrlEncoder().encodeToString(objectid.toByteArray());
	}

	public static String encodeBase64ObjectId(String objectIdString) {
		ObjectId objectId = new ObjectId(objectIdString);
		return base64ObjectId(objectId);
	}

	public static String decodeBase64ObjectId(String base64ObjectId) {

		// byte[] byObjectId = Base64.decodeBase64(compressedObjectId);
		byte[] byObjectId = Base64.getUrlDecoder().decode(base64ObjectId);

		ObjectId objectId = new ObjectId(byObjectId);
		return objectId.toHexString();
	}

	

//	public static void main(String args[]) {
//	    String doubleEncodeId=base64ObjectId();
//	    System.out.println(doubleEncodeId.length());
//		int count = 1000000000;
//		// check performance single thread
//		long begin = System.currentTimeMillis();
//		for (int i = 0; i < count; i++) {
//			String originalId = ObjectIdUtil.objectId();
//		}
//		long end = System.currentTimeMillis();
//		System.out.println("Original ID generation total milliseconds:" + (end - begin) + " total seconds:"
//				+ (end - begin) / 1000);
//		System.out.println("Original ID generation QPMS:" + count / ((end - begin) ));
//		begin = System.currentTimeMillis();
//		for (int i = 0; i < count; i++) {
//			String base64Id = ObjectIdUtil.base64ObjectId();
//		}
//		end = System.currentTimeMillis();
//		System.out.println(
//				"Base64 ID generation total milliseconds:" + (end - begin) + " total seconds:" + (end - begin) / 1000);
//		System.out.println("Base64 ID generation QPMS:" + count / ((end - begin) ));
//
//
//		// check encode decode safety
//		for (int i = 0; i < count; i++) {
//			String originalId = ObjectIdUtil.objectId();
//			String base64Id = ObjectIdUtil.encodeBase64ObjectId(originalId);
//			if (!originalId.equals(ObjectIdUtil.decodeBase64ObjectId(base64Id))) {
//				System.out.println("Unsafe Base64 encode and decode, original id:" + originalId);
//			}
//		}
//
//		// check unique safety
//		count=10000000;
//		Set set = new HashSet(count+2);
//		for (int i = 0; i < count; i++) {
//
//			String originalId = ObjectIdUtil.objectId();
//			set.add(originalId);
//		}
//		if (set.size() != count) {
//			System.out.println("Duplicated key found in originalId set.");
//		}else{
//		    System.out.println("OriginalId passed");
//		}
//		set.clear();
//		
//		for (int i = 0; i < count; i++) {
//
//			String originalId = ObjectIdUtil.objectId();
//
//			String base64Id = ObjectIdUtil.encodeBase64ObjectId(originalId);
//			set.add(base64Id);
//		}
//		if (set.size() != count) {
//			System.out.println("Duplicated key found in base64Id set.");
//		}else{
//		    System.out.println("Base64Id passed");
//		}
//		set.clear();
//	}

}