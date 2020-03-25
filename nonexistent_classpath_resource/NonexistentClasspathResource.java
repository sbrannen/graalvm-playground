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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class NonexistentClasspathResource {

	public static void main(String[] args) throws Exception {
		new Tester().test();
	}


	static class Tester {

		void test() throws Exception {
			String name = "NonexistentClasspathResource.class";
			URL resource1 = getClass().getResource(name);
			if (resource1 == null) {
				System.err.println("FAILURE: could not find classpath resource: " + name);
				return;
			}
			System.err.println("SUCCESS: found classpath resource: " + name);

			if (!exists(resource1)) {
				System.err.println("FAILURE: classpath resource does not exist: " + resource1);
				return;
			}
			System.err.println("SUCCESS: classpath resource exists: " + resource1);

			URL resource2 = new URL(resource1, "Bogus.class");
			if (exists(resource2)) {
				System.err.println("FAILURE: bogus classpath resource appears to exist: " + resource2);
				return;
			}
			System.err.println("SUCCESS: bogus classpath resource does not exist: " + resource2);
		}

		private boolean exists(URL url) {
			try {
				// Try a URL connection content-length header
				URLConnection con = url.openConnection();
				if (con.getContentLengthLong() > 0) {
					System.err.format("getContentLengthLong() for %s: %d%n", url, con.getContentLengthLong());
					return true;
				}

				// Fall back to stream existence: can we open the stream?
				getInputStream(url).close();
				System.err.println("opened InputStream...");
				return true;
			}
			catch (IOException ex) {
				return false;
			}
		}

		private InputStream getInputStream(URL url) throws IOException {
			return url.openConnection().getInputStream();
		}

	}

}
