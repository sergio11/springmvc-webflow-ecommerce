/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import persistence.models.Authority;

/**
 *
 * @author sergio
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);
}
