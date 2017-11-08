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
package de.olivergierke.examples.spring5.webflux;

import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;

/**
 * @author Mark Paluch
 * @author Oliver Gierke
 */
@SpringBootApplication
public class WebfluxDemo {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(WebfluxDemo.class, args);
	}

	/**
	 * Application runner to initialize a capped collection for {@link Event}s and insert a new {@link Event} every two
	 * seconds.
	 * 
	 * @param operations
	 * @param reactiveOperations
	 * @return
	 */
	@Bean
	ApplicationRunner onStart(MongoOperations operations, ReactiveMongoOperations reactiveOperations) {

		return args -> {

			CollectionOptions options = CollectionOptions.empty() //
					.capped() //
					.size(2048) //
					.maxDocuments(1000);

			operations.dropCollection(Event.class);
			operations.createCollection(Event.class, options);

			Flux.interval(Duration.ofSeconds(2)) //
					.map(counter -> new Event(LocalDateTime.now())) //
					.flatMap(reactiveOperations::save) //
					.log() //
					.subscribe();
		};
	}
}
