import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.MergedAnnotation;

public class SynthesizedAnnotations {

	@RequestMapping("/test")
	public static void main(String[] args) throws Exception {
		Method method = Controller.class.getDeclaredMethod("handlerMethod");

		RequestMapping jdkRequestMapping = method.getAnnotation(RequestMapping.class);
		System.out.println("JDK: " + jdkRequestMapping);
		System.out.println("JDK: " + jdkRequestMapping.getClass());
		System.out.println("----------------------------------------------------------------");

		RequestMapping synthesizedRequestMapping = MergedAnnotation.from(jdkRequestMapping).synthesize();
		System.out.println("SPR: " + synthesizedRequestMapping);
		System.out.println("SPR: " + synthesizedRequestMapping.getClass());
		System.out.println("----------------------------------------------------------------");

		System.out.println("Proxy classes are the same: " +
				jdkRequestMapping.getClass().equals(synthesizedRequestMapping.getClass()));
	}

}

class Controller {

	@RequestMapping("/test")
	String handlerMethod() {
		return "enigma";
	}

}

@Retention(RetentionPolicy.RUNTIME)
@interface RequestMapping {

	@AliasFor("path")
	String value() default "";

	@AliasFor("value")
	String path() default "";
}
