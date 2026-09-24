package com.campusfix.campusfix.service;

import com.campusfix.campusfix.entity.Location;
import com.campusfix.campusfix.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    public Location updateLocation(Long id, Location location) {

        Location existingLocation =
                locationRepository.findById(id).orElse(null);

        if (existingLocation == null) {
            return null;
        }

        existingLocation.setBuilding(location.getBuilding());
        existingLocation.setFloor(location.getFloor());
        existingLocation.setRoom(location.getRoom());

        return locationRepository.save(existingLocation);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}