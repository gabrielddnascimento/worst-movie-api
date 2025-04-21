package com.golden.raspbery.awards.infrastructure;

import com.opencsv.bean.AbstractBeanField;

public class YesNoToBooleanConverter extends AbstractBeanField<Boolean, String> {
	@Override
	protected Boolean convert(String value) {
		return value != null && value.trim().equalsIgnoreCase("yes");
	}
}
