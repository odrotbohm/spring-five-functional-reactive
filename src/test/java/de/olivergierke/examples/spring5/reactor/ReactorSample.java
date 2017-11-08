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
package de.olivergierke.examples.spring5.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.junit.Test;

/**
 * @author Oliver Gierke
 */
public class ReactorSample {

	@Test
	public void reactorFundamentals() {

		Mono.just("Hello") //
				.map(word -> word.concat(" World!")) //
				.subscribe(System.out::println);

		Flux.just("Hello", "World") //
				.flatMap(word -> Flux.fromArray(word.split(""))) //
				.subscribe(System.out::println);
	}
}
