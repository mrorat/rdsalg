                        									package com.quasar;

import static org.junit.Assert.*;

import org.junit.Test;

import com.quasar.rdsalg.TextHelper;

public class TextHelperTest
{
	@Test
	public void oneLetterLowerTest()
	{
		assertEquals("A", TextHelper.capitalizeFirstLetter("a"));
	}

	@Test
	public void word1Test()
	{
		assertEquals("Bar", TextHelper.capitalizeFirstLetter("bar"));
	}

	@Test
	public void oneLetterUpperTest() { assertEquals("A", TextHelper.capitalizeFirstLetter("A")); }
	
}
