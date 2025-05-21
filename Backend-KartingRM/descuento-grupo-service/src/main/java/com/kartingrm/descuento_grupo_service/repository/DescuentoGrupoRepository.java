package com.kartingrm.descuento_grupo_service.repository;

import com.kartingrm.descuento_grupo_service.entity.DescuentoGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescuentoGrupoRepository extends JpaRepository<DescuentoGrupo, Long> {
}
