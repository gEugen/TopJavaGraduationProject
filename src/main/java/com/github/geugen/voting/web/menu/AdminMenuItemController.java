package com.github.geugen.voting.web.menu;

import com.github.geugen.voting.model.MenuItem;
import com.github.geugen.voting.repository.MenuItemRepository;
import com.github.geugen.voting.repository.RestaurantRepository;
import com.github.geugen.voting.util.validation.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;


@Tag(
        name = "Admin Menu Item Controller",
        description = "allows administrator to get menu item list or " +
                "specific item of specific restaurant, create, update and delete them")
@RestController
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Validated
public class AdminMenuItemController {
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menu-items";

    private final MenuItemRepository menuItemRepository;

    private final RestaurantRepository restaurantRepository;

    @Operation(
            summary = "Get actual menu item list for restaurant by its id",
            description = "Returns actual menu item list")
    @GetMapping()
    public List<MenuItem> getActualByRestaurant(@Parameter(description = "restaurant id") @PathVariable @Min(1) int restaurantId) {
        log.info("get {}", restaurantId);
        return menuItemRepository.getAllExisted(LocalDate.now(), restaurantId);
    }

    @Operation(
            summary = "Get restaurant menu item list by restaurant id and date",
            description = "Returns menu item list")
    @GetMapping("/by-date")
    public List<MenuItem> getAllByRestaurantForGivenDay(
            @Parameter(description = "restaurant id") @PathVariable @Min(1) int restaurantId,
            @Parameter(description = "requested date") @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate requestDate) {
        log.info("get {}", restaurantId);
        return menuItemRepository.getAllExisted(requestDate, restaurantId);
    }

    @Operation(
            summary = "Get menu item for restaurant by its ides",
            description = "Returns response with menu item")
    @GetMapping("/{itemId}")
    public MenuItem get(
            @Parameter(description = "restaurant id") @PathVariable @Min(1) int restaurantId,
            @Parameter(description = "menu item id") @PathVariable @Min(1) int itemId) {
        log.info("get menu item {} for restaurant {}", itemId, restaurantId);
        return menuItemRepository.checkBelongAndGet(restaurantId, itemId);
    }

    @Operation(
            summary = "Delete menu item for restaurant by its ides",
            description = "Deletes menu item")
    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "restaurant id") @PathVariable @Min(1) int restaurantId,
            @Parameter(description = "menu item id") @PathVariable @Min(1) int itemId) {
        log.info("delete menu item {} for restaurant {}", restaurantId, itemId);
        MenuItem menuItem = menuItemRepository.checkBelongAndGet(restaurantId, itemId);
        menuItemRepository.delete(menuItem);
    }

    @Operation(
            summary = "Update menu item details for restaurant by its ides",
            description = "Updates and returns response with updated menu item")
    @PutMapping(value = "/{itemId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(
            @Parameter(description = "menu item") @Valid @RequestBody MenuItem menuItem,
            @Parameter(description = "restaurant id") @PathVariable @Min(1) int restaurantId,
            @Parameter(description = "menu item id") @PathVariable @Min(1) int itemId) {
        log.info("update menu item {} for restaurant {}", menuItem, restaurantId);
        ValidationUtil.assureIdConsistent(menuItem, itemId);
        MenuItem updatableItem = menuItemRepository.checkBelongAndGet(restaurantId, itemId);
        updatableItem.setName(menuItem.getName());
        updatableItem.setPrice(menuItem.getPrice());
        menuItemRepository.save(updatableItem);
    }

    @Operation(
            summary = "Create new menu item for restaurant by its id",
            description = "Creates new menu item and returns response with new menu item")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> createWithLocation(
            @Parameter(description = "menu item") @Valid @RequestBody MenuItem menuItem,
            @Parameter(description = "restaurant id") @PathVariable @Min(1) int restaurantId) {
        log.info("create menu item {} for restaurant {}", menuItem, restaurantId);
        ValidationUtil.checkNew(menuItem);
        menuItem.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        MenuItem created = menuItemRepository.save(menuItem);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{itemId}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}