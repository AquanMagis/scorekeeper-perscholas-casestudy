package com.perscholas.scorekeeper.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerUpdateForm {
	private String firstName;
	private boolean showLastName;
	private String lastName;
	private boolean showFirstName;
	private String displayName;
	private String bio;
}
