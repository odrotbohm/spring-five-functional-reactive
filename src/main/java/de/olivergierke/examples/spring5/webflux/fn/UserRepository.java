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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.repository.Repository;

/**
 * A reactive repository to handle {@link User}s.
 *
 * @author Oliver Gierke
 */
interface UserRepository extends Repository<User, String> {

	/**
	 * Looks up the user with the given identifier.
	 * 
	 * @param id
	 * @return
	 */
	Mono<User> findById(String id);

	/**
	 * Returns all {@link User}s currently available.
	 * 
	 * @return
	 */
	Flux<User> findAll();

	/**
	 * Saves the given {@link User}.
	 * 
	 * @param user
	 * @return
	 */
	Mono<User> save(User user);
}
