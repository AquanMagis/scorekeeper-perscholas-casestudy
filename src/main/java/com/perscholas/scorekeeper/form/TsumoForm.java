package com.perscholas.scorekeeper.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
public class TsumoForm extends BasicHandForm{
	@NotNull
	long winnerId;

	@Min(value = 20, message = "Minimum fu is {1}.")
	int fu;
	@Min(value = 1, message = "Minimum han is {1}.")
	int han;
}
