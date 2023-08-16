package hanglog.trip.presentation;

import hanglog.auth.Auth;
import hanglog.trip.dto.request.ItemRequest;
import hanglog.trip.dto.request.ItemUpdateRequest;
import hanglog.trip.service.ItemService;
import hanglog.trip.service.TripService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/trips/{tripId}/items")
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final TripService tripService;

    @PostMapping
    public ResponseEntity<Void> createItem(
            @Auth final Long memberId,
            @PathVariable final Long tripId,
            @RequestBody @Valid final ItemRequest itemRequest
    ) {
        tripService.validateTripByMember(memberId, tripId);
        final Long itemId = itemService.save(tripId, itemRequest);
        return ResponseEntity.created(URI.create("/trips/" + tripId + "/items/" + itemId)).build();
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Void> updateItem(
            @Auth final Long memberId,
            @PathVariable final Long tripId,
            @PathVariable final Long itemId,
            @RequestBody @Valid final ItemUpdateRequest itemUpdateRequest
    ) {
        tripService.validateTripByMember(memberId, tripId);
        itemService.update(tripId, itemId, itemUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @Auth final Long memberId,
            @PathVariable final Long tripId,
            @PathVariable final Long itemId
    ) {
        tripService.validateTripByMember(memberId, tripId);
        itemService.delete(itemId);
        return ResponseEntity.noContent().build();
    }
}
