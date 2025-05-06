package pl.pwr.trash.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.pwr.trash.model.Listing;
import pl.pwr.trash.request.ListingRequest;

public interface ListingService {
    Listing getListing(HttpServletRequest httpRequest, Long id);

    Page<Listing> getListings(HttpServletRequest httpRequest,
                              Pageable pageable);

    void createListing(HttpServletRequest httpServletRequest, ListingRequest listingRequest, Long userId);

    void removeListing(HttpServletRequest httpRequest, Long id);

    void updateListing(HttpServletRequest httpRequest, Long id, ListingRequest listingRequest);
}
