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

import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mark Paluch
 * @author Oliver Gierke
 */
@RestController
@RequiredArgsConstructor
class EventController {

	private final EventRepository eventRepository;

	@GetMapping(path = "/events", produces = { //
			MediaType.APPLICATION_STREAM_JSON_VALUE, //
			MediaType.TEXT_EVENT_STREAM_VALUE //
	})
	Flux<Event> streamEvents() {
		return eventRepository.findPeopleBy();
	}
}

@Data
@Document
class Event {

	String id;
	final LocalDateTime eventDate;
}

interface EventRepository extends ReactiveCrudRepository<Event, String> {

	@Tailable
	Flux<Event> findPeopleBy();
}
