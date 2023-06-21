package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoCheque.AverageSumResponse;
import peaksoft.dto.dtoCheque.ChequeRequest;
import peaksoft.dto.dtoCheque.ChequeResponse;
import peaksoft.dto.dtoCheque.PaginationChequeResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository repository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    private User getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepository.getUserByEmail(name).orElseThrow(() -> new NotFoundException("User with email: " + name + " us bit found!"));
        return user;
    }

    @Override
    public PaginationChequeResponse getAllCheques() {
        return null;
    }

    @Override
    public SimpleResponse saveCheque(Long userId, Long menuItemId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User with id:%s is not found...", userId)));
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> new NotFoundException(String.format("Item with id:%s is not found...", menuItemId)));
        List<MenuItem> items = new ArrayList<>();
        int totalPrice = 0;
        items.add(menuItem);
        for (MenuItem m:items) {
            totalPrice+=m.getPrice();
        }
        Cheque cheque= new Cheque();
        cheque.setUser(user);
        cheque.setCreatedAt(LocalDate.now());
        cheque.setMenuItems(items);
        cheque.setPriceAverage(totalPrice);
        repository.save(cheque);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved")
                .build();
    }

    @Override
    public SimpleResponse updateCheque(Long id, ChequeRequest chequeRequest) {
        Cheque cheque = repository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Cheque with id:%s is not present", id)));
        cheque.setPriceAverage(chequeRequest.priceAverage());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved")
                .build();
    }

    @Override
    public ChequeResponse getChequeById(Long id) {
        Cheque cheque = repository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Cheque with id:%s is not present", id)));
        Restaurant restaurant = restaurantRepository.findById(1L).orElseThrow(() -> new NotFoundException(String.format("Restaurant with id:%s is not present", 1L)));
        return ChequeResponse.builder()
                .id(cheque.getId())
                .waiterFullName(cheque.getUser().getFirstName()+" "+cheque.getUser().getLastName())
                .items(cheque.getMenuItems())
                .service(restaurant.getService())
                .priceAverage(cheque.getPriceAverage())
                .grandTotal((int) (cheque.getPriceAverage()*0.15))
                .build();
    }

    @Override
    public SimpleResponse deleteCheque(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
         return    SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Successfully deleted")
                    .build();
        }else throw new NotFoundException(String.format("Cheque with id:%s is not present", id));
    }

    @Override
    public AverageSumResponse getAverageSum(LocalDate date) {
        int i = 0;
        List<Cheque> cheques = new ArrayList<>();
        List<User> all = userRepository.findAll();
        for (int j = 0; j < all.size(); j++) {
           cheques.addAll(all.get(j).getCheques());
        }
        for (int k = 0; k < cheques.size(); k++) {
            if (cheques.get(k).getCreatedAt().equals(date)){
                i+=cheques.get(k).getPriceAverage();
            }
        }
        return AverageSumResponse.builder()
                .sum(i)
                .build();
    }

    @Override
    public AverageSumResponse getAverageSumOfWaiter(Long waiterId, LocalDate dateTime) {
        User user = userRepository.findById(waiterId).orElseThrow(() -> new NotFoundException(String.format("Waiter with id:%s is not present", waiterId)));
        int i = repository.averageSumOfWaiter(user.getId(), dateTime);
        return AverageSumResponse.builder()
                .sum(i)
                .build();
    }
}
