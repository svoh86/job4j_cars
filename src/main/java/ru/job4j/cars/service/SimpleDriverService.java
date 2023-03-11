package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.DriverRepository;

import java.util.Optional;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class SimpleDriverService implements DriverService {
    private final DriverRepository driverRepository;

    @Override
    public Driver add(String driverName, User user) {
        Driver driver = new Driver();
        driver.setName(driverName);
        driver.setUser(user);
        return driverRepository.create(driver);
    }

    @Override
    public boolean update(Driver driver) {
        return driverRepository.update(driver);
    }

    @Override
    public void delete(int id) {
        driverRepository.delete(id);
    }

    @Override
    public Optional<Driver> findById(int id) {
        return driverRepository.findById(id);
    }
}
