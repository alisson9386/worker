package br.com.worker.repository;

import br.com.worker.domain.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
public class CarsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<Car> findAll(){
        try {
            String sql = "SELECT c.id, c.model, c.mark , c.color , c.yearModel , c.odometerKm , c.chassi , c.passengerNumbers FROM cars c";

            List<Car> result = jdbcTemplate.query(
                    sql,
                    (rs, row) -> new Car(
                            rs.getLong("id"),
                            rs.getString("model"),
                            rs.getString("mark"),
                            rs.getString("color"),
                            rs.getInt("yearModel"),
                            rs.getLong("odometerKm"),
                            rs.getString("chassi"),
                            rs.getInt("passengerNumbers")
                    )
            );
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Transactional(readOnly = true)
    public boolean findByChassi(Car cars) {
        try {
            String sql = "SELECT COUNT(*) FROM cars WHERE chassi = ?";

            int count = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{cars.getChassi()},
                    Integer.class
            );
            boolean exists = count > 0;
            return exists;

        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public Car findByChassiObject(String chassi) {
        try {
            String sql = "SELECT c.id, c.model, c.mark , c.color , c.yearModel , c.odometerKm , c.chassi , c.passengerNumbers FROM cars c WHERE chassi = ?";

            Car result = (Car) jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{chassi},
                    (rs, row) -> new Car(
                            rs.getLong("id"),
                            rs.getString("model"),
                            rs.getString("mark"),
                            rs.getString("color"),
                            rs.getInt("yearModel"),
                            rs.getLong("odometerKm"),
                            rs.getString("chassi"),
                            rs.getInt("passengerNumbers")
                    )
            );
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Car createOrUpdateCar(Car car, boolean update) {
        try {
            if (!update) {
                String sql = "INSERT INTO cars (model, mark, color, yearModel, odometerKm, chassi, passengerNumbers) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
                int rowsAffected = jdbcTemplate.update(
                        sql, car.getModel(), car.getMark(), car.getColor(), car.getYearModel(),
                        car.getOdometerKm(), car.getChassi(), car.getPassengerNumbers());

                if (rowsAffected > 0) {
                    Car carInserted = findByChassiObject(car.getChassi());
                    return carInserted;
                } else {
                    return null;
                }

            } else {
                String sql = "UPDATE cars SET model = ?, mark = ?, color = ?, yearModel = ?, odometerKm = ?, chassi = ?, " +
                        "passengerNumbers = ? WHERE id = ?";
                jdbcTemplate.update(sql, car.getModel(), car.getMark(), car.getColor(), car.getYearModel(),
                        car.getOdometerKm(), car.getChassi(), car.getPassengerNumbers(),
                        car.getId());

                Car carUpdated = findByChassiObject(car.getChassi());
                return carUpdated;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
