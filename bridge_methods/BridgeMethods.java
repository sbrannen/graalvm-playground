/*
 * Copyright 2002-2020 the original author or authors.
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

public class BridgeMethods {

	public static void main(String[] args) {
		Method bridgedMethod = null;
		Method bridgeMethod = null;
		for (Method method : StringGenericParameter.class.getDeclaredMethods()) {
			if ("getFor".equals(method.getName())) {
				if (method.getReturnType().equals(Object.class)) {
					bridgeMethod = method;
				}
				else {
					bridgedMethod = method;
				}
			}
		}
		System.out.println("Bridged method is a bridge method: " + bridgedMethod.isBridge());
		System.out.println("Bridge method is a bridge method:  " + bridgeMethod.isBridge());
	}


	interface GenericParameter<T> {

		T getFor(Class<T> cls);
	}

	static class StringGenericParameter implements GenericParameter<String> {

		@Override
		public String getFor(Class<String> cls) {
			return null;
		}

		public String getFor(Integer integer) {
			return null;
		}
	}

}
