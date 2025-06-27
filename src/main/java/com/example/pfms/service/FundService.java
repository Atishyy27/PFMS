package com.example.pfms.service;

import com.example.pfms.model.Fund;
import com.example.pfms.model.User;
import com.example.pfms.repository.FundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FundService {
    @Autowired private FundRepository repo;

    public Fund createFund(Fund fund) {
        return repo.save(fund);
    }

    public List<Fund> listFundsByUser(User user) {
        return repo.findByUser(user);
    }

    public Fund getFundById(Long id) {
        return repo.findById(id)
                   .orElseThrow(() -> new IllegalArgumentException("Fund not found: " + id));
    }

    @Transactional
    public Fund updateFund(Long id, Fund updated) {
        Fund existing = getFundById(id);
        existing.setMonth(updated.getMonth());
        existing.setContributionAmount(updated.getContributionAmount());
        existing.setWithdrawalAmount(updated.getWithdrawalAmount());
        return existing;
    }

    public void deleteFund(Long id) {
        repo.deleteById(id);
    }

    public List<Fund> searchByMonth(User user, String pattern) {
        return repo.findByUserAndMonthContaining(user, pattern);
    }
}
