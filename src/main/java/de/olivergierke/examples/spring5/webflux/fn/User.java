/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.olivergierke.examples.spring5.webflux.fn;

import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A {@link User} domain type.
 *
 * @author Oliver Gierke
 */
@Data
@Document
class User {

	private String id;
	private String name;

	public User(String name) {
		this.name = name;
	}
}
