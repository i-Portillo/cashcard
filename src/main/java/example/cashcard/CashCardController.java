package example.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
class CashCardController {

	private final CashCardRepository cashCardRepository;

	private CashCardController(CashCardRepository cashCardRepository) {
		this.cashCardRepository = cashCardRepository;
	}

	@GetMapping("/{requestId}")
	private ResponseEntity<CashCard> findById(@PathVariable Long requestId) {
		Optional<CashCard> cashCardOptional = cashCardRepository.findById(requestId);
		if (cashCardOptional.isPresent()) {
			return ResponseEntity.ok(cashCardOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
		CashCard savedCashCard = cashCardRepository.save(newCashCardRequest);
		URI locationOfNewCashCard = ucb
			.path("cashcards/{id}")
			.buildAndExpand(savedCashCard.id())
			.toUri();
		return ResponseEntity.created(locationOfNewCashCard).build();
	}
}
