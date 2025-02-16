package com.example.esme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RepasandoProgramacionApplicationTests {

	@Test
	void contextLoads() {
		int numA = 2;
		int numB = 2;
		int expected = 4;
		assertEquals(expected, (numA + numB));
	}

}
