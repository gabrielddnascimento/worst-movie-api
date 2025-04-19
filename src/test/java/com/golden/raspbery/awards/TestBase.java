package com.golden.raspbery.awards;

public class TestBase {
	private static final String SERVER_URL = "http://localhost:";
	private static final int SERVER_PORT = 8080;

	public StringBuilder getServerURL() {
		return new StringBuilder(SERVER_URL).append(SERVER_PORT);
	}
}
