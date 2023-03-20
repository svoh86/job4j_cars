package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.Optional;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class SimpleCarService implements CarService {
    private final CarRepository carRepository;

    @Override
    public Car add(Car car) {
        return carRepository.create(car);
    }

    @Override
    public boolean update(Car car) {
        return carRepository.update(car);
    }

    @Override
    public void delete(int id) {
        carRepository.delete(id);
    }

    @Override
    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }
}
