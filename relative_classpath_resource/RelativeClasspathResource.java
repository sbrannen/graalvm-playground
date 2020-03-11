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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class RelativeClasspathResource {

	public static void main(String[] args) throws Exception {
		new Tester().test();
	}


	static class Tester {

		void test() throws Exception {
			classpathResource("subdir/example1.txt", "1");
			classpathResource("example2.txt", "2");
			classpathResource("subdir/../example2.txt", "2");
		}

		private void classpathResource(String name, String expectedContents) throws Exception {
			URL resource = getClass().getResource(name);
			if (resource == null) {
				System.err.println("FAILURE: could not find classpath resource: " + name);
				return;
			}
			String contents = getContentsAsString(getClass().getResourceAsStream(name));
			if (contents == null) {
				System.err.println("FAILURE: could not read contents of classpath resource: " + name);
				return;
			}
			if (!contents.equals(expectedContents)) {
				System.err.format("FAILURE: classpath resource '%s' contains '%s' instead of '%s'%n", name, contents,
					expectedContents);
			}
			System.err.println("SUCCESS: found and read classpath resource: " + name);
		}
	}


	private static String getContentsAsString(InputStream in) throws Exception {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			return reader.readLine();
		}
	}

}
