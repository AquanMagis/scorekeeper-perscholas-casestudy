package com.perscholas.scorekeeper.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class BasicHandForm {
	int gameId;
	List<Long> inRiichi;
}
