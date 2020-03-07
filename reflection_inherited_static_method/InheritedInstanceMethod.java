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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.ZoneId;

public class InheritedInstanceMethod {

	public static void main(String[] args) throws Exception {
		new Tester().performTest();
	}
}

class Tester {

	void performTest() throws Exception {
		// Note: ZoneId.of() returns an instance of java.time.ZoneRegion.
		ZoneId zoneId = ZoneId.of("GMT+1");
		Class<?> clazz = zoneId.getClass();
		instanceMethod(clazz);
	}

	private void instanceMethod(Class<?> clazz) throws NoSuchMethodException {
		Method method = clazz.getMethod("getId");
		if (method != null && !Modifier.isStatic(method.getModifiers())) {
			System.out.format("SUCCESS: found instance method getId() in %s.%n", clazz.getName());
		}
		else {
			System.err.format("FAILURE: did not find instance method getId() in %s.%n", clazz.getName());
		}
	}

}
