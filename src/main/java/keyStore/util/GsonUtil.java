package keyStore.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class GsonUtil {

	private static final Gson gson = new Gson();

	public static <T> T fromJson(String content, Class<T> toClass) {
		return gson.fromJson(content, toClass);
	}
	
    public static <T> T fromJson(String content, Type typeOfT) {
        return gson.fromJson(content, typeOfT);
    }

	public static String toJson(Object content) {
		return gson.toJson(content);
	}
}
