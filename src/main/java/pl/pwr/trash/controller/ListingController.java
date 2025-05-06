package pl.pwr.trash.controller;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwr.trash.model.Listing;
import pl.pwr.trash.request.ListingRequest;
import pl.pwr.trash.service.ListingService;


@RestController
@RequestMapping("/api/v1/listing")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListing(
            HttpServletRequest httpRequest,
            @PathVariable("id") Long id
    ) {
        Listing listing = listingService.getListing(httpRequest, id);
        return ResponseEntity.ok(listing);
    }
    @GetMapping
    public ResponseEntity<Page<Listing>> getListings(
            HttpServletRequest httpRequest,
            @PageableDefault Pageable pageable
    ) {
        Page<Listing> listings = listingService.getListings(httpRequest, pageable);
        System.out.println(listings.getContent());
        return ResponseEntity.ok(listings);
    }
    @PostMapping("/user/{userId}")
    public ResponseEntity createListing(
            HttpServletRequest httpServletRequest,
            @RequestBody @Valid ListingRequest listingRequest,
            @PathVariable("userId") Long userId
    ) {
       listingService.createListing(httpServletRequest, listingRequest, userId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteListing(
            HttpServletRequest httpRequest,
            @PathVariable("id") Long id
    ) {
        listingService.removeListing(httpRequest, id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity updateListing(
            HttpServletRequest httpRequest,
            @PathVariable("id") Long id,
            @RequestBody @Valid ListingRequest listingRequest
    ) {
        listingService.updateListing(httpRequest, id, listingRequest);
        return ResponseEntity.noContent().build();
    }

}
