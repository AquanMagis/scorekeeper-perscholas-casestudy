package com.perscholas.scorekeeper.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RonForm extends BasicHandForm{
	public static short ARRAY_LENGTH = 3;
	Long loserId;
	Long[] winnerIds = new Long[ARRAY_LENGTH];
	Integer[] fu = new Integer[ARRAY_LENGTH];
	Integer[] han = new Integer[ARRAY_LENGTH];
}
