package hanglog.trip.presentation;

import hanglog.trip.presentation.dto.request.ItemRequest;
import hanglog.trip.service.ItemService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/trips/{tripId}/items")
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Void> createItem(@PathVariable final Long tripId, @RequestBody @Valid final ItemRequest itemRequest) {
        Long id = itemService.save(tripId, itemRequest);
        return ResponseEntity.created(URI.create("/trips/" + tripId + "/items/" + id)).build();
    }
}
