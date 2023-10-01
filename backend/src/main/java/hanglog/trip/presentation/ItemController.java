package hanglog.trip.presentation;

import hanglog.auth.Auth;
import hanglog.auth.MemberOnly;
import hanglog.auth.domain.Accessor;
import hanglog.trip.dto.request.ExpenseRequest;
import hanglog.trip.dto.request.ItemRequest;
import hanglog.trip.dto.request.ItemUpdateRequest;
import hanglog.trip.dto.request.PlaceRequest;
import hanglog.trip.service.ItemService;
import hanglog.trip.service.TripService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
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
    @MemberOnly
    public ResponseEntity<Void> createItem(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId,
            @RequestBody @Valid final ItemRequest itemRequest
    ) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        final Long itemId = itemService.save(tripId, itemRequest);
        return ResponseEntity.created(URI.create("/trips/" + tripId + "/items/" + itemId)).build();
    }

    @PutMapping("/{itemId}")
    @MemberOnly
    public ResponseEntity<Void> updateItem(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId,
            @PathVariable final Long itemId,
            @RequestBody @Valid final ItemUpdateRequest itemUpdateRequest
    ) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        itemService.update(tripId, itemId, itemUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{itemId}")
    @MemberOnly
    public ResponseEntity<Void> deleteItem(
            @Auth final Accessor accessor,
            @PathVariable final Long tripId,
            @PathVariable final Long itemId
    ) {
        tripService.validateTripByMember(accessor.getMemberId(), tripId);
        itemService.delete(itemId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/test")
    @MemberOnly
    public ResponseEntity<Void> makeLegendTrip(@Auth final Accessor accessor, @PathVariable final Long tripId) {
//        for (long dayLogId = 435; dayLogId <= 495; dayLogId++) {
//            for (int i = 0; i < 20; i++) {
//                ItemRequest itemRequest = new ItemRequest(
//                        true,
//                        "title",
//                        5.0,
//                        "memo",
//                        dayLogId,
//                        List.of("https://hanglog.com/img/1" + dayLogId + "1" + i, "https://hanglog.com/img/2"+ dayLogId + "2" + i, "https://hanglog.com/img/3"+ dayLogId + "3"+ i, "https://hanglog.com/img/4"+ dayLogId + "4"+ i, "https://hanglog.com/img/5"+ dayLogId + "5"+ i),
//                        new PlaceRequest("place", BigDecimal.ONE, BigDecimal.ONE, List.of("food")),
//                        new ExpenseRequest("krw", BigDecimal.TEN, 100L)
//                );
//                itemService.save(tripId, itemRequest);
//            }
//        }

//        for (int i = 1; i < 20; i++) {
//            ItemRequest itemRequest = new ItemRequest(
//                    false,
//                    "title",
//                    5.0,
//                    "memo",
//                    496L,
//                    List.of("https://hanglog.com/img/1" + i, "https://hanglog.com/img/2" + i, "https://hanglog.com/img/3" + i,
//                            "https://hanglog.com/img/4" + i, "https://hanglog.com/img/5" + i),
//                    null,
//                    new ExpenseRequest("krw", BigDecimal.TEN, 100L)
//            );
//            itemService.save(tripId, itemRequest);
//        }

        ItemRequest itemRequest = new ItemRequest(
                true,
                "title",
                5.0,
                "memo",
                433L,
                List.of("https://hanglog.com/img/1" + 433 + "1",
                        "https://hanglog.com/img/2" + 433 + "2",
                        "https://hanglog.com/img/3" + 433 + "3",
                        "https://hanglog.com/img/4" + 433 + "4",
                        "https://hanglog.com/img/5" + 433 + "5"),
                new PlaceRequest("place", BigDecimal.ONE, BigDecimal.ONE, List.of("food")),
                new ExpenseRequest("krw", BigDecimal.TEN, 100L)
        );
        itemService.save(tripId, itemRequest);

        return ResponseEntity.created(URI.create("/trips/" + tripId + "/items/" + 1)).build();
    }
}
