package com.perscholas.scorekeeper.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class BasicHandForm {
	long gameId;
	List<Long> inRiichi;
}
