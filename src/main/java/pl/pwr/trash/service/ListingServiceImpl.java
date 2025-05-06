package pl.pwr.trash.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pwr.trash.dao.ListingDao;
import pl.pwr.trash.model.Listing;
import pl.pwr.trash.model.ListingStatus;
import pl.pwr.trash.request.ListingRequest;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService{
    private final ListingDao listingDao;
    @Override
    public Listing getListing(HttpServletRequest httpRequest, Long id) {
        Long userId =  Long.valueOf( httpRequest.getAttribute("user_id").toString() );
        if (userId == null) {
            throw new IllegalArgumentException("User ID not found in request");
        }
        Listing listing = listingDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        if (!listing.getUserId().equals(userId)) {
            throw new IllegalArgumentException("User does not have permission to access this listing");
        }
        return listing;
    }

    @Override
    public Page<Listing> getListings(HttpServletRequest httpRequest, Pageable pageable) {
        Long userId =  Long.valueOf( httpRequest.getAttribute("user_id").toString() );
        if (userId == null) {
            throw new IllegalArgumentException("User ID not found in request");
        }
        return listingDao.findByUserId(userId.intValue(), pageable);
    }

    @Override
    public void createListing(HttpServletRequest httpServletRequest,
                              ListingRequest listingRequest,
                              Long userId) {
        Long userIdFromRequest =  Long.valueOf( httpServletRequest.getAttribute("user_id").toString() );
        System.out.println(userIdFromRequest);
        if (userIdFromRequest == null) {
            throw new IllegalArgumentException("User ID not found in request");
        }
        if (!userIdFromRequest.equals(userId)) {
            throw new IllegalArgumentException("User does not have permission to create this listing");
        }
        Listing listing = new Listing();
        listing.setTitle(listingRequest.getTitle());
        listing.setDescription(listingRequest.getDescription());
        listing.setPrice(listingRequest.getPrice());
        listing.setPhoto(listingRequest.getPhoto());
        listing.setUserId(userId.intValue());
        listing.setStatusLis(ListingStatus.ACTIVE);
        listing.setCreatedAt(OffsetDateTime.now());
        listing.setUpdatedAt(OffsetDateTime.now());
        System.out.println(listing);
        int x = listingDao.save(listing);
        System.out.println(x);
    }

    @Override
    public void removeListing(HttpServletRequest httpRequest,
                              Long id) {
        Long userId =  Long.valueOf( httpRequest.getAttribute("user_id").toString() );
        if (userId == null) {
            throw new IllegalArgumentException("User ID not found in request");
        }
        Listing listing = listingDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        if (!listing.getUserId().equals(userId)) {
            throw new IllegalArgumentException("User does not have permission to delete this listing");
        }
        listingDao.deleteById(id);
    }

    @Override
    public void updateListing(HttpServletRequest httpRequest,
                              Long id,
                              ListingRequest listingRequest) {
        Long userId =  Long.valueOf( httpRequest.getAttribute("user_id").toString() );
        if (userId == null) {
            throw new IllegalArgumentException("User ID not found in request");
        }
        Listing listing = listingDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        if (!listing.getUserId().equals(userId)) {
            throw new IllegalArgumentException("User does not have permission to update this listing");
        }
        listing.setTitle(listingRequest.getTitle());
        listing.setDescription(listingRequest.getDescription());
        listing.setPrice(listingRequest.getPrice());
        listing.setPhoto(listingRequest.getPhoto());
        listing.setUpdatedAt(OffsetDateTime.now());
        listingDao.update(listing);
    }
}
