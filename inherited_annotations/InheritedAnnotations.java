/*
 * Copyright 2020-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class InheritedAnnotations {

	public static void main(String[] args) {
		findAnnotation(Superclass.class);
		findAnnotation(Subclass.class);
	}

	private static void findAnnotation(Class<?> clazz) {
		Annotation[] annotations = clazz.getAnnotations();
		if (annotations.length == 1 && annotations[0].annotationType().equals(InheritedAnnotation.class)) {
			System.out.format("SUCCESS: @InheritedAnnotation was found as an annotation on %s.%n",
				clazz.getSimpleName());
		}
		else {
			System.out.format("FAILURE: @InheritedAnnotation was NOT found as an annotation on %s.%n",
				clazz.getSimpleName());
		}
		Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
		if (declaredAnnotations.length == 1
				&& declaredAnnotations[0].annotationType().equals(InheritedAnnotation.class)) {
			System.out.format("SUCCESS: @InheritedAnnotation was found as a declared annotation on %s.%n",
				clazz.getSimpleName());
		}
		else {
			System.out.format("FAILURE: @InheritedAnnotation was NOT found as a declared annotation on %s.%n",
				clazz.getSimpleName());
		}
	}


	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	@interface InheritedAnnotation {
	}

	@InheritedAnnotation
	static class Superclass {
	}

	@InheritedAnnotation
	static class Subclass extends Superclass {
	}

}
