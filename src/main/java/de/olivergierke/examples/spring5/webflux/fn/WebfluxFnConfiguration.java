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

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
class WebfluxFnConfiguration {

	/**
	 * Defines the routes mapped to {@link FunctionalUserController}.
	 * 
	 * @param controller
	 * @return
	 */
	@Bean
	RouterFunction<?> routes(FunctionalUserController controller) {

		return RouterFunctions//
				.route(GET("/users"), controller::getUsers)//
				.andRoute(GET("/users/{id}"), controller::getUser);
	}

	/**
	 * Initializes the repository with a sample user.
	 * 
	 * @param repository the repository to create {@link User}s in.
	 * @return
	 */
	@Bean
	ApplicationRunner onStartup(UserRepository repository) {

		// We need to call ….block() here to actually execute the call.
		return (args) -> repository.save(new User("Dave Matthews")).block();
	}

	/**
	 * Functional controller in Spring WebFlux.
	 *
	 * @author Oliver Gierke
	 */
	@Component
	@RequiredArgsConstructor
	static class FunctionalUserController {

		private final UserRepository repository;

		/**
		 * Returns a single {@link User}.
		 * 
		 * @param request
		 * @return
		 */
		Mono<ServerResponse> getUser(ServerRequest request) {

			Mono<User> user = Mono.just(request.pathVariable("id")) //
					.flatMap(repository::findById);

			return ServerResponse.ok().body(user, User.class);
		}

		/**
		 * Returns all {@link User}s available.
		 * 
		 * @param request
		 * @return
		 */
		Mono<ServerResponse> getUsers(ServerRequest request) {

			Flux<User> users = repository.findAll();
			return ServerResponse.ok().body(users, User.class);
		}
	}
}
