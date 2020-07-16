package com.lotus.authen.api.repository;

import com.lotus.authen.api.repository.models.PINModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PINRepo extends JpaRepository<PINModel, Integer> {
}
