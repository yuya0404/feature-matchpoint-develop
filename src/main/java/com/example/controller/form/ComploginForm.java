package com.example.controller.form;

import javax.validation.constraints.NotBlank;

public class ComploginForm {

	@NotBlank
	private String compLoginId;

	public String getCompLoginId() { return compLoginId; }
	public void setCompLoginId(String compLoginId) { this.compLoginId = compLoginId; }
}
