package org.cnd.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
	@Autowired
	private Validator validator;

	/**
	 * This method validates if the model is correct
	 * 
	 * @param object     - model to be validated
	 * @param properties - list of properties to be validated if null then validate
	 *                   all properties
	 * @return
	 */
	protected List<String> validateConstraints(Object object, String... properties) {
		Set<ConstraintViolation<Object>> validationErrors = this.validator.validate(object);
		if (validationErrors != null && !validationErrors.isEmpty()) {
			List<String> listError = new ArrayList<>();
			for (ConstraintViolation<Object> constraintViolation : validationErrors) {
				if (properties != null && properties.length > 0) {
					for (String property : properties) {
						if (property != null && property.equals(constraintViolation.getPropertyPath().toString())) {
							listError.add(constraintViolation.getMessage());
							break;
						}

					}
				} else
					listError.add(constraintViolation.getMessage());

			}
			return listError.isEmpty() ? null : listError;
		}
		return null;

	}

}
