package com.gabriel.music.redesocial.repository.material;

import com.gabriel.music.redesocial.domain.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Optional<Object> findByReferenceFileName(String newFileName);
}
