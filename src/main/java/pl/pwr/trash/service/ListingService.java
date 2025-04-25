package pl.pwr.trash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pwr.trash.dao.ListingDao;
import pl.pwr.trash.model.Listing;
import pl.pwr.trash.model.ListingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingDao listingDao;

    public List<Listing> getAllListings() {
        return listingDao.findAll();
    }

    public Optional<Listing> getListingById(Long id) {
        return listingDao.findById(id);
    }

    public Optional<Listing> createListing(Listing listing) {
        listing.setStatusLis(ListingStatus.ACTIVE);
        listing.setCreatedAt(LocalDateTime.now());
        listing.setUpdatedAt(LocalDateTime.now());

        int rows = listingDao.save(listing);
        if (rows > 0) {
            return listingDao.findAll().stream()
                    .filter(l -> l.getTitle().equals(listing.getTitle())
                            && l.getUserId().equals(listing.getUserId()))
                    .findFirst();
        }
        return Optional.empty();
    }

    public Optional<Listing> updateListing(Long id, Listing updatedListing) {
        return listingDao.findById(id).map(existingListing -> {
            existingListing.setTitle(updatedListing.getTitle());
            existingListing.setDescription(updatedListing.getDescription());
            existingListing.setPrice(updatedListing.getPrice());
            existingListing.setPhoto(updatedListing.getPhoto());
            existingListing.setStatusLis(updatedListing.getStatusLis());
            existingListing.setUpdatedAt(LocalDateTime.now());

            int rows = listingDao.update(existingListing);
            return rows > 0 ? existingListing : null;
        });
    }

    public boolean deleteListing(Long id) {
        return listingDao.deleteById(id) > 0;
    }

    public List<Listing> getListingsByUserId(Integer userId) {
        return listingDao.findByUserId(userId);
    }

    public boolean updateListingStatus(Long listingId, ListingStatus status) {
        return listingDao.updateStatus(listingId.intValue(), status.name()) > 0;
    }
}
