package hanglog.trip.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import hanglog.category.Category;
import hanglog.category.repository.CategoryRepository;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.ItemRepository;
import hanglog.trip.fixture.ItemFixture;
import hanglog.trip.fixture.TripFixture;
import hanglog.trip.presentation.dto.request.ExpenseRequest;
import hanglog.trip.presentation.dto.request.ItemRequest;
import hanglog.trip.presentation.dto.request.PlaceRequest;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private DayLogRepository dayLogRepository;

    @DisplayName("새롭게 생성한 여행 아이템의 id를 반환한다.")
    @Test
    void save() {
        // given
        final PlaceRequest placeRequest = new PlaceRequest(
                "apiId",
                "에펠탑",
                "에펠탑주소",
                new BigDecimal("38.123456"),
                new BigDecimal("39.123456"),
                "categoryApiId"
        );
        final ExpenseRequest expenseRequest = new ExpenseRequest("EURO", 10000, 1L);
        final ItemRequest itemRequest = new ItemRequest(
                true,
                "에펠탑",
                4.5,
                "에펠탑을 방문",
                1L,
                placeRequest,
                expenseRequest
        );

        given(itemRepository.save(any()))
                .willReturn(ItemFixture.LONDON_EYE_ITEM);
        given(categoryRepository.findById(any()))
                .willReturn(Optional.of(new Category("문화", "apiId")));
        given(categoryRepository.findByGoogleApiId(any()))
                .willReturn(Optional.of(new Category("문화", "apiId")));
        given(dayLogRepository.findById(any()))
                .willReturn(Optional.of(new DayLog("첫날", 1, TripFixture.LONDON_TRIP)));

        // when
        Long actualId = itemService.save(1L, itemRequest);

        // then
        assertThat(actualId).isEqualTo(1L);
    }

    @DisplayName("여행 아이템의 정보를 수정한다.")
    @Test
    void update() {
        // given
        final PlaceRequest placeRequest = new PlaceRequest(
                "apiId",
                "에펠탑",
                "에펠탑주소",
                new BigDecimal("38.123456"),
                new BigDecimal("39.123456"),
                "categoryApiId"
        );
        final ExpenseRequest expenseRequest = new ExpenseRequest("EURO", 10000, 1L);
        final ItemRequest itemRequest = new ItemRequest(
                true,
                "에펠탑",
                4.5,
                "에펠탑을 방문",
                1L,
                placeRequest,
                expenseRequest
        );

        given(itemRepository.save(any()))
                .willReturn(ItemFixture.LONDON_EYE_ITEM);
        given(itemRepository.findById(any()))
                .willReturn(Optional.ofNullable(ItemFixture.LONDON_EYE_ITEM));
        given(categoryRepository.findById(any()))
                .willReturn(Optional.of(new Category("문화", "apiId")));
        given(categoryRepository.findByGoogleApiId(any()))
                .willReturn(Optional.of(new Category("문화", "apiId")));
        given(dayLogRepository.findById(any()))
                .willReturn(Optional.of(new DayLog("첫날", 1, TripFixture.LONDON_TRIP)));

        // when
        itemService.update(1L, 1L, itemRequest);

        // then
        verify(itemRepository).save(any());
    }
}
