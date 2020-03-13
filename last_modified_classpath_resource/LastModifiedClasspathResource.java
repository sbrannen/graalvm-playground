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

import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.time.Instant.ofEpochMilli;

public class LastModifiedClasspathResource {

	public static void main(String[] args) throws Exception {
		new LastModifiedClasspathResource().test();
	}

	void test() throws Exception {
		String name = LastModifiedClasspathResource.class.getName() + ".class";
		URL resource = getClass().getResource(name);
		if (resource == null) {
			System.err.println("FAILURE: could not find classpath resource " + name);
			return;
		}
		System.err.println("SUCCESS: found classpath resource " + name);

		URLConnection connection = resource.openConnection();

		int contentLength = connection.getContentLength();
		if (contentLength <= 0) {
			System.err.println("FAILURE: non-positive content-length " + contentLength);
		}
		else {
			System.err.println("SUCCESS: content-length " + contentLength);
		}

		long lastModified = connection.getLastModified();
		if (lastModified <= 0) {
			System.err.println("FAILURE: non-positive last-modified " + lastModified);
		}
		else {
			LocalDateTime date = LocalDateTime.ofInstant(ofEpochMilli(lastModified), ZoneId.systemDefault());
			System.err.format("SUCCESS: last-modified timestamp %d / date %s%n", lastModified, date);
		}
	}

}
