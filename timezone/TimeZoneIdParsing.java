import java.util.TimeZone;

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

public class TimeZoneIdParsing {

	public static void main(String[] args) {
		String input = "GMT+2";
		String expected = "GMT+02:00";
		TimeZone timeZone = TimeZone.getTimeZone(input);
		String parsed = timeZone.getID();
		if (!parsed.equals(expected)) {
			System.err.format("FAILURE: expected '%s' to be parsed into '%s' but got '%s'.%n", input, expected, parsed);
		}
		else {
			System.out.format("SUCCESS: '%s' was correctly parsed into '%s'.%n", input, parsed);
		}
	}

}
