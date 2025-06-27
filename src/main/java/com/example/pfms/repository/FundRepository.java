package com.example.pfms.repository;

import com.example.pfms.model.Fund;
import com.example.pfms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FundRepository extends JpaRepository<Fund, Long> {
    List<Fund> findByUser(User user);
    List<Fund> findByUserAndMonthContaining(User user, String monthPattern);
}
